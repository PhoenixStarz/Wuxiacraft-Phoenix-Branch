package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.SkillActivatorAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.init.*;
import com.lazydragonstudios.wuxiacraft.networking.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CultivationEventHandler {

	//TODO add drop back realm when not have stabilized realm
	@SubscribeEvent
	public static void onCultivatorUpdate(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		var player = event.player;
		if (player == null) return;
		player.level().getProfiler().push("playerCultivationUpdate");
		//defining variables I'm sure I'm gonna use a lot inside here
		ICultivation cultivation = Cultivation.get(player);
		var bodyData = cultivation.getSystemData(System.BODY);
		var divineData = cultivation.getSystemData(System.DIVINE);
		var essenceData = cultivation.getSystemData(System.ESSENCE);
		var itemStack = player.getInventory().getItem(0);

		handleClientSync(player, cultivation);
		if (cultivation.isSemiDead()) {
			handleSemiDead(player, cultivation);
		} else {
			handleSkillCasting(player, cultivation);
			handleBodyEnergyRegen(player, cultivation, bodyData);
			handleEnergyRegen(player, cultivation);
			handleEnergyOverflow(cultivation);
			handleNaturalHealing(cultivation, bodyData);
			handleBarrierRegen(cultivation, essenceData);
			handleHungerRegen(player, cultivation, essenceData);
			handleExerciseEnergies(cultivation, bodyData, divineData, essenceData);
			handleExerciseAddingCultBase(player, cultivation, bodyData);
			handleLowEnergyPunishments(player, cultivation, bodyData, divineData);
			handleCustomPotionEffects(player, cultivation, divineData, essenceData);
			handleCombatMovementCosts(player, cultivation);
			handleParticlesWhenFlying(player, cultivation);
			handleShouldAddStepAssist(player, cultivation);
			handlePlayerExtraHealth(player, cultivation);
			handleCanFly(player, cultivation);
		}
		player.level().getProfiler().pop();
	}

	private static void handleCanFly(Player player, ICultivation cultivation) {
		player.getAbilities().mayfly = player.getAbilities().mayfly || cultivation.canFly();
	}

	private static void handleBarrierRegen(ICultivation cultivation, SystemContainer essenceData) {
		if (!(essenceData.getStage() instanceof EssenceCultivationStage essenceStage)) return;
		if (cultivation.getStat(PlayerStat.BARRIER).compareTo(cultivation.getStat(PlayerStat.MAX_BARRIER)) < 0
				&& cultivation.getStat(PlayerStat.BARRIER_REGEN_COOLDOWN).compareTo(BigDecimal.ZERO) <= 0 &&
				cultivation.isCombat() && !cultivation.isSemiDead() && essenceStage.isCanHaveBarrier()) {
			BigDecimal cost = cultivation.getStat(PlayerStat.BARRIER_REGEN_COST);
			BigDecimal maxEnergy = cultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY);
			if (essenceData.hasEnergy(cost.add(maxEnergy.multiply(new BigDecimal("0.5")))) && essenceData.consumeEnergy(cost)) {
				cultivation.setStat(PlayerStat.BARRIER,
						cultivation.getStat(PlayerStat.BARRIER).add(cultivation.getStat(PlayerStat.BARRIER_REGEN)).min(cultivation.getStat(PlayerStat.MAX_BARRIER)));
			}
		}
		if (cultivation.getStat(PlayerStat.BARRIER_REGEN_COOLDOWN).compareTo(BigDecimal.ZERO) > 0) {
			cultivation.setStat(PlayerStat.BARRIER_REGEN_COOLDOWN, cultivation.getStat(PlayerStat.BARRIER_REGEN_COOLDOWN).subtract(BigDecimal.ONE).max(BigDecimal.ZERO));
		}
	}

	private static void handleHungerRegen(Player player, ICultivation cultivation, SystemContainer essenceData) {
		if (!(essenceData.getStage() instanceof EssenceCultivationStage essenceStage)) return;
		if (!cultivation.isSemiDead() && essenceStage.isCanConvertToFood()) {
			BigDecimal cost = cultivation.getStat(PlayerStat.HUNGER_REGEN_COST);
			var regenAmount = cultivation.getStat(PlayerStat.HUNGER_REGEN).floatValue();
			FoodData foodData = player.getFoodData();
			if ((foodData.getFoodLevel() < 20 ||
					foodData.getSaturationLevel() < foodData.getFoodLevel()) && essenceData.consumeEnergy(cost)) {
				var exhaustionLevel = foodData.getExhaustionLevel();
				var saturationLevel = foodData.getSaturationLevel();
				var foodLevel = foodData.getFoodLevel();
				exhaustionLevel = exhaustionLevel - regenAmount;
				if (exhaustionLevel <= 0) {
					exhaustionLevel = 4f;
					saturationLevel = Math.min(saturationLevel + 0.5f, foodLevel);
					if (saturationLevel >= foodLevel) {
						foodLevel = Math.min(foodLevel + 1, 20);
					}
				}
				foodData.setExhaustion(exhaustionLevel);
				foodData.setSaturation(saturationLevel);
				foodData.setFoodLevel(foodLevel);
			}
		}
	}

	private static void handleShouldAddStepAssist(Player player, ICultivation cultivation) {
		var agi = cultivation.getStat(PlayerStat.AGILITY);
		var attributes = player.getAttributes();
		var step_instance = attributes.getInstance(ForgeMod.STEP_HEIGHT_ADDITION.get());
		if (step_instance == null) return;
		var modifier = new AttributeModifier(UUID.fromString("8a657afe-6307-11ee-8c99-0242ac120002"), "wuxiacraft.step_assist", 1, AttributeModifier.Operation.ADDITION);
		step_instance.removeModifier(modifier);
		if (agi.compareTo(new BigDecimal("0.15")) >= 0 && cultivation.isCombat()) {
			step_instance.addTransientModifier(modifier);
		}
	}

	/**
	 * Makes the vanilla max hp always maintain at it's base value so that regen and other stuff doesn't mess with wuxia health system
	 *
	 * @param player      target player
	 * @param cultivation target player's cultivation
	 */
	private static void handlePlayerExtraHealth(Player player, ICultivation cultivation) {
		var attributes = player.getAttributes();
		var maxHealthInstance = attributes.getInstance(Attributes.MAX_HEALTH);
		if (maxHealthInstance == null) return;
		var extraHealth = maxHealthInstance.getValue() - maxHealthInstance.getBaseValue();
		var modifier = new AttributeModifier(UUID.fromString("7a9c1749-7c95-4e61-893b-72f3efff5629"), "wuxiacraft.health_modifier", -extraHealth, AttributeModifier.Operation.ADDITION);
		maxHealthInstance.removeModifier(modifier);
		if (extraHealth > 0) {
			maxHealthInstance.addTransientModifier(modifier);
		}
		cultivation.setExtraHealthFromAttributes(extraHealth);
	}

	private static void handleClientSync(Player player, ICultivation cultivation) {
		//Sync the cultivation with the client every so often
		cultivation.advanceTimer();
		if (cultivation.getTimer() >= 100) {
			cultivation.resetTimer();
			if (!player.level().isClientSide()) {
				syncClientCultivation((ServerPlayer) player);
				for (var system : System.values()) {
					var systemData = cultivation.getSystemData(system);
					systemData.techniqueData.grid.fixProficiencies(cultivation.getAspects());
				}
			}
		}
	}

	private static void handleSemiDead(Player player, ICultivation cultivation) {
		cultivation.advanceSemiDead(20 * WuxiaConfigs.SEMI_DEAD_TIMER.get());
		if (cultivation.getStat(PlayerStat.HEALTH).compareTo(BigDecimal.TEN) > 0) {
			cultivation.setSemiDeadState(false);
		}
		if (player.level().isClientSide()) return;
		if (!cultivation.isSemiDead()) {
			WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
					new TurnSemiDeadStateMessage(false, Component.empty(), false));
		}
	}

	// TODO change CAST SPEED and COOLDOWN SPEED to their respective systems later on
	private static void handleSkillCasting(Player player, ICultivation cultivation) {
		var skillData = cultivation.getSkills();
		var selectedSkill = skillData.getSkillAt(skillData.selectedSkill);
		if (cultivation.isCombat()) {
			if (skillData.casting && selectedSkill.getStatValue(SkillStat.CURRENT_COOLDOWN).compareTo(BigDecimal.ZERO) <= 0) {
				if (!selectedSkill.getSkillChain().isEmpty() && selectedSkill.getSkillChain().getFirst() instanceof SkillActivatorAspect activator) {
					selectedSkill.addStat(SkillStat.CURRENT_CASTING, cultivation.getSystemData(System.ESSENCE).getStat(PlayerSystemStat.CAST_SPEED));
					selectedSkill.addStat(SkillStat.NON_STOP_CASTING_TIME, BigDecimal.ONE);
					//casting >= cast_time
					if (selectedSkill.getStatValue(SkillStat.CURRENT_CASTING)
							.compareTo(selectedSkill.getAppliedStats(cultivation, SkillStat.CAST_TIME)) >= 0 &&
							activator.activate.test(player, selectedSkill)) {
						selectedSkill.setStat(SkillStat.CURRENT_CASTING, BigDecimal.ZERO);
						selectedSkill.setStat(SkillStat.CURRENT_COOLDOWN, selectedSkill.getAppliedStats(cultivation, SkillStat.COOLDOWN));
						selectedSkill.setStat(SkillStat.CURRENT_MAX_COOLDOWN, selectedSkill.getStatValue(SkillStat.CURRENT_COOLDOWN));
					}
				}
			} else {
				if (selectedSkill.getStatValue(SkillStat.CURRENT_CASTING).compareTo(BigDecimal.ZERO) > 0) {
					selectedSkill.addStat(SkillStat.CURRENT_CASTING, new BigDecimal("-1"));
				}
				if (selectedSkill.getStatValue(SkillStat.NON_STOP_CASTING_TIME).compareTo(BigDecimal.ZERO) > 0) {
					selectedSkill.setStat(SkillStat.NON_STOP_CASTING_TIME, BigDecimal.ZERO);
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			var skill = skillData.getSkillAt(i);
			if (skill.getStatValue(SkillStat.CURRENT_COOLDOWN).compareTo(BigDecimal.ZERO) > 0) {
				skill.addStat(SkillStat.CURRENT_COOLDOWN, new BigDecimal("-1").multiply(cultivation.getSystemData(System.ESSENCE).getStat(PlayerSystemStat.COOLDOWN_SPEED)));
			} else if (skill.getStatValue(SkillStat.CURRENT_MAX_COOLDOWN).compareTo(BigDecimal.ZERO) > 0) {
				skill.setStat(SkillStat.CURRENT_MAX_COOLDOWN, BigDecimal.ZERO);
			}
		}
		if (player.level().isClientSide && player == Minecraft.getInstance().player) {
			var animationState = ClientAnimationState.get(player);
			if (!skillData.casting && animationState.isSwordFlight()) {
				animationState.setSwordFlight(false);
				WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
			}
		}
	}

	private static void handleBodyEnergyRegen(Player player, ICultivation cultivation, SystemContainer bodyData) {
		//Body energy regen depends on food
		if (player.getFoodData().getFoodLevel() > 15) {
			BigDecimal hunger_modifier = new BigDecimal("1");
			if (player.getFoodData().getFoodLevel() >= 18) hunger_modifier = hunger_modifier.add(new BigDecimal("0.3"));
			if (player.getFoodData().getFoodLevel() >= 20) hunger_modifier = hunger_modifier.add(new BigDecimal("0.3"));
			BigDecimal finalEnergyRegen = cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY_REGEN).multiply(hunger_modifier);
			//bodyEnergy < bodyMaxEnergy * 0.7 (70%)
			BigDecimal maxEnergyMultiplicand = new BigDecimal(cultivation.isWithinFormationRange() ? "1.1" : "0.7");
			boolean canRegenBodyEnergy = cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY).compareTo(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(maxEnergyMultiplicand)) < 0;
			if (canRegenBodyEnergy) {
				bodyData.addEnergy(finalEnergyRegen);
				cultivation.setStat(System.BODY, PlayerSystemStat.ENERGY, cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY).min(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(maxEnergyMultiplicand)));
				player.causeFoodExhaustion(finalEnergyRegen.floatValue() * 0.2f);
			}
		}
	}

	private static void handleEnergyRegen(Player player, ICultivation cultivation) {
		//others don't
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			if (system != System.BODY) { //body already regenerated at that point
				if (systemData.getStage().canRegenEnergy())
					systemData.addEnergy(cultivation.getStat(system, PlayerSystemStat.ENERGY_REGEN));
			}
			//kill if above 150%
			if (cultivation.getStat(system, PlayerSystemStat.ENERGY).compareTo(cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("1.5"))) > 0) {
				killPlayerWithExplosion(player, systemData, player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.ENERGY_EXCESS_ESSENCE),
						//energy * 3 * max_health -> just to guarantee death
						cultivation.getStat(system, PlayerSystemStat.ENERGY).multiply(new BigDecimal("3")).multiply(cultivation.getStat(PlayerStat.MAX_HEALTH)));
				//or regulate it slowly to 100%
			} else if (cultivation.getStat(system, PlayerSystemStat.ENERGY).compareTo(cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal(cultivation.isWithinFormationRange() ? "1.1" : "1"))) > 0) {
				systemData.consumeEnergy(cultivation.getStat(system, PlayerSystemStat.ENERGY_REGEN));
			}
		}
	}

	private static void handleEnergyOverflow(ICultivation cultivation) {
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			if (!systemData.getStage().canRegenEnergy()) continue;
			var energy = cultivation.getStat(system, PlayerSystemStat.ENERGY);
			var max_energy = cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal(cultivation.isWithinFormationRange() ? "1.1" : "1.0"));
			if (energy.compareTo(max_energy) > 0) {
				var energy_regen = cultivation.getStat(system, PlayerSystemStat.ENERGY_REGEN);
				energy = energy.subtract(energy_regen.multiply(new BigDecimal("2"))).max(max_energy);
				systemData.setStat(PlayerSystemStat.ENERGY, energy);
			}
		}
	}

	private static void handleNaturalHealing(ICultivation cultivation, SystemContainer bodyData) {
		//Healing part yaay
		if (cultivation.getStat(PlayerStat.HEALTH).compareTo(cultivation.getStat(PlayerStat.MAX_HEALTH)) < 0) {
			BigDecimal energy_used = cultivation.getStat(PlayerStat.HEALTH_REGEN_COST);
			//Won't heal when energy is below 10%
			if (cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY).subtract(energy_used).compareTo(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("0.1"))) >= 0) {
				BigDecimal amount_healed = cultivation.getStat(PlayerStat.HEALTH_REGEN);
				if (bodyData.consumeEnergy(energy_used)) { // this is the correct way to use consume energy ever
					cultivation.setStat(PlayerStat.HEALTH, cultivation.getStat(PlayerStat.MAX_HEALTH).min(cultivation.getStat(PlayerStat.HEALTH).add(amount_healed)));
				}
			}
		}
	}

	private static void handleExerciseEnergies(ICultivation cultivation, SystemContainer bodyData, SystemContainer divineData, SystemContainer essenceData) {
		//if player is exercising, add a little of essence to him
		if (cultivation.isExercising() && (
				bodyData.techniqueData.modifier.isValidTechnique() ||
						essenceData.techniqueData.modifier.isValidTechnique()
		)) {
			if (bodyData.hasEnergy(cultivation.getStat(PlayerStat.EXERCISE_COST))
					&& divineData.hasEnergy(cultivation.getStat(PlayerStat.EXERCISE_COST))) {
				bodyData.consumeEnergy(cultivation.getStat(PlayerStat.EXERCISE_COST));
				divineData.consumeEnergy(cultivation.getStat(PlayerStat.EXERCISE_COST).multiply(new BigDecimal("0.34")));
				if (bodyData.techniqueData.modifier.isValidTechnique() && essenceData.hasEnergy(cultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("1.1"))))
					return;
				essenceData.addEnergy(cultivation.getStat(PlayerStat.EXERCISE_CONVERSION));
			}
		}
	}

	private static void handleExerciseAddingCultBase(Player player, ICultivation cultivation, SystemContainer bodyData) {

		if (cultivation.isExercising() &&
				bodyData.techniqueData.modifier.isValidTechnique()) {
			cultivation.addCultivationBase(player, System.BODY,
					//conversion  * 0.01
					cultivation.getStat(PlayerStat.EXERCISE_CONVERSION)
							.multiply(new BigDecimal("0.01"))
			);
		}
	}

	private static final HashMap<System, BigDecimal> energyAccumulatedFromRunning = new HashMap<>();

	private static void handleCombatMovementCosts(Player player, ICultivation cultivation) {
		if (!player.level().isClientSide) return;
		if (!cultivation.isCombat()) {
			if (!energyAccumulatedFromRunning.isEmpty()) {
				for (var system : System.values()) {
					WuxiaPacketHandler.INSTANCE.sendToServer(new ClientUsedEnergyMessage(energyAccumulatedFromRunning.getOrDefault(system, BigDecimal.ZERO), system));
				}
				energyAccumulatedFromRunning.clear();
			}
			return;
		}
		if (!player.onGround() && !player.getAbilities().flying) return;
		var playerSpeed = BigDecimal.valueOf(player.getSpeed() + 0.21f);
		if (player.getAbilities().flying) playerSpeed = playerSpeed.multiply(new BigDecimal("1.6"));
		var totalValue = cultivation.getStat(PlayerStat.AGILITY, true);
		if (totalValue.compareTo(BigDecimal.ZERO) == 0) return;
		var lastWalkedDistance = BigDecimal.valueOf(player.getDeltaMovement().horizontalDistance()).subtract(playerSpeed).max(BigDecimal.ZERO);
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			var agilityStat = systemData.getStat(PlayerStat.AGILITY);
			var agilityParcel = agilityStat.divide(totalValue, RoundingMode.HALF_UP);
			var energyConsumed = lastWalkedDistance.multiply(agilityParcel);
			systemData.consumeEnergy(energyConsumed);
			var accumulated = energyAccumulatedFromRunning.getOrDefault(system, BigDecimal.ZERO);
			accumulated = accumulated.add(energyConsumed);
			energyAccumulatedFromRunning.put(system, accumulated);
			if ((cultivation.getTimer() + 5) % 20 == 0) {
				WuxiaPacketHandler.INSTANCE.sendToServer(new ClientUsedEnergyMessage(accumulated, system));
				energyAccumulatedFromRunning.put(system, BigDecimal.ZERO);
			}
		}
	}

	private static void handleParticlesWhenFlying(Player player, ICultivation cultivation) {
		if (!(player.level() instanceof ServerLevel level)) return;
		if (!cultivation.canFly()) return;
		if (!player.getAbilities().flying) return;
		ParticleOptions particle = (ParticleOptions) WuxiaParticleTypes.FLIGHT_PARTICLE.get();
		level.sendParticles(particle, player.getX(), player.getY()-0.8, player.getZ(), 2, 0.6, 0, 0.6, 0.01);
	}

	private static void handleLowEnergyPunishments(Player player, ICultivation cultivation, SystemContainer bodyData, SystemContainer divineData) {
		// punishment for low energy >>> poor resource management
		if (!bodyData.hasEnergy(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("0.1")))) {
			double relativeAmount = cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY).divide(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY), RoundingMode.HALF_UP).doubleValue();
			int amplifier = 0;
			if (relativeAmount < 0.08) amplifier = 1;
			if (relativeAmount < 0.06) amplifier = 2;
			if (relativeAmount < 0.04) amplifier = 3;
			if (relativeAmount < 0.02) amplifier = 4;
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, amplifier, true, false));
			player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 15, amplifier, true, false));
			if (relativeAmount < 0.005) {
				player.hurt(player.level().damageSources().wither(), 2);
			}
		}
		if (!divineData.hasEnergy(cultivation.getStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("0.1")))) {
			double relativeAmount = cultivation.getStat(System.DIVINE, PlayerSystemStat.ENERGY).divide(cultivation.getStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY), RoundingMode.HALF_UP).doubleValue();
			int amplifier = 0;
			if (relativeAmount < 0.08) amplifier = 1;
			if (relativeAmount < 0.06) amplifier = 2;
			if (relativeAmount < 0.04) amplifier = 3;
			if (relativeAmount < 0.02) amplifier = 4;
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, amplifier, true, false));
			player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 15, amplifier, true, false));
			if (relativeAmount < 0.005) {
				player.hurt(player.level().damageSources().wither(), 2);
			}
		}
	}

	private static void handleCustomPotionEffects(Player player, ICultivation cultivation, SystemContainer divineData, SystemContainer essenceData) {
		MathContext mc = new MathContext(16, RoundingMode.HALF_DOWN);
		if (player.hasEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get())) {
			var effectInstance = player.getEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get());
			if (effectInstance == null) return;
			// 0.022 * 2.4 ^ amplifier
			essenceData.addEnergy(new BigDecimal("0.022").multiply(new BigDecimal("2.4").pow(effectInstance.getAmplifier()), mc));
			// 0.009 * 1.8 ^ amplifier
			divineData.addEnergy(new BigDecimal("0.009").multiply(new BigDecimal("1.8").pow(effectInstance.getAmplifier()), mc));
		}
		if (player.hasEffect(MobEffects.REGENERATION)) {
			var effectInstance = player.getEffect(MobEffects.REGENERATION);
			if (effectInstance == null) return;
			//vanilla regen per tick, better than vanilla because of better precision
			var healedAmount = BigDecimal.valueOf(2).pow(effectInstance.getAmplifier(), mc).divide(BigDecimal.valueOf(50), mc);
			cultivation.setStat(PlayerStat.HEALTH, cultivation.getStat(PlayerStat.HEALTH).add(healedAmount).min(cultivation.getStat(PlayerStat.MAX_HEALTH)));
		}
	}

	/**
	 * A helper procedure to simplify the line actually because this might get repeated a lot in this file
	 *
	 * @param player the player to be synchronized
	 */
	public static void syncClientCultivation(ServerPlayer player) {
		ICultivation cultivation = Cultivation.get(player);
		WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new CultivationSyncMessage(cultivation));
		cultivation.calculateStats();
		var rule = (GameRules.IntegerValue) player.level().getGameRules().getRule(WuxiaGameRules.maxWuxiaAgility);
		WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SynchronizeMaxAgility(rule.getCommandResult()));
	}

	private static void killPlayerWithExplosion(Player player, SystemContainer systemData, Holder<DamageType> damageType, BigDecimal amount) {
		systemData.setStat(PlayerSystemStat.ENERGY, BigDecimal.ZERO);
		var interaction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(player.level(), player) ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE;
		player.level().explode(null, player.getX(), player.getY(), player.getZ(), 5f, false, interaction);
		player.hurt(new WuxiaDamageSource(damageType, WuxiaElements.PHYSICAL.get(), player, amount).setInstantDeath(), amount.floatValue());
	}

	/**
	 * Restores peoples cultivation after death, with some penalties
	 * This fires right after players press respawn
	 * Sync issues with this because client might get the sync package before actually spawning
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent
	public static void onPlayerDeath(PlayerEvent.Clone event) {
		event.getOriginal().reviveCaps();
		ICultivation oldCultivation = Cultivation.get(event.getOriginal());
		ICultivation newCultivation = Cultivation.get(event.getEntity());
		oldCultivation.setSemiDeadState(false);
		if (event.isWasDeath()) {
			//oldCultivation.setSkillCooldown(0);
			if (event.getOriginal().getTags().contains("PLEASE_RTP_ME")) {
				event.getEntity().addTag("PLEASE_RTP_ME");
				var rtpPos = findSafeRTP(event.getOriginal());
				event.getEntity().getPersistentData().putDouble("rtp_to_x", rtpPos.x);
				event.getEntity().getPersistentData().putDouble("rtp_to_y", rtpPos.y);
				event.getEntity().getPersistentData().putDouble("rtp_to_z", rtpPos.z);
			}
			oldCultivation.setStat(PlayerStat.LIVES, oldCultivation.getStat(PlayerStat.LIVES).subtract(BigDecimal.ONE));
			if (oldCultivation.getStat(PlayerStat.LIVES).compareTo(BigDecimal.ZERO) == 0) {
				oldCultivation = new Cultivation();
			} else {
				oldCultivation.setStat(PlayerStat.HEALTH, PlayerStat.HEALTH.defaultValue);
				var bodyData = oldCultivation.getSystemData(System.BODY);
				var divineData = oldCultivation.getSystemData(System.DIVINE);
				var essenceData = oldCultivation.getSystemData(System.ESSENCE);
				bodyData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("7"));
				divineData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("10"));
				essenceData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("0"));
			}
		}
		event.getOriginal().invalidateCaps();
		newCultivation.deserialize(oldCultivation.serialize());
	}

	private static Vec3 findSafeRTP(Player player) {
		Vec3 result = new Vec3(0, 0, 0);
		var level = player.level();
		var playerPositions = new HashSet<Point>();
		Point playerDeathPosition = new Point(player.getBlockX(), player.getBlockZ());
		for (var p : level.players()) {
			playerPositions.add(new Point(p.getBlockX(), p.getBlockZ()));
		}
		WuxiaCraft.LOGGER.info("Player: " + player.getDisplayName() + " is random teleporting!");
		int attempts = 30;
		int spawnX = player.level().getLevelData().getXSpawn();
		int spawnZ = player.level().getLevelData().getZSpawn();
		Point newPosition = null;
		for (int i = 0; i < attempts; i++) {
			boolean isNearSomeone = false;
			int newX = spawnX + player.getRandom().nextInt(20000) - 10000;
			int newZ = spawnZ + player.getRandom().nextInt(20000) - 10000;
			var point = new Point(newX, newZ);
			//I'm using the y variable from point as the z coordinate
			if (point.distance(playerDeathPosition.x, playerDeathPosition.y) >= 1000) {
				for (var p : playerPositions) {
					if (p.distance(point.x, point.y) < 400) {
						isNearSomeone = true;
						break;
					}
				}
			} else {
				isNearSomeone = true;
			}
			if (!isNearSomeone) {
				newPosition = point;
				break;
			}
		}
		if (newPosition != null) {
			var chunk = player.level().getChunkAt(new BlockPos(newPosition.x, 64, newPosition.y));
			int newY = chunk.getMaxBuildHeight();
			for (int i = chunk.getMaxBuildHeight(); i >= 0; i--) {
				var state = chunk.getBlockState(new BlockPos(newPosition.x, i, newPosition.y));
				newY = i;
				if (!state.getBlock().equals(Blocks.AIR)) {
					break;
				}
			}
			result = new Vec3(newPosition.x, newY, newPosition.y);
		}
		return result;
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		syncClientCultivation((ServerPlayer) player);
		fixEnergies(player);
		WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
				new TurnSemiDeadStateMessage(Cultivation.get(player).isSemiDead(),
						//since these two are useless when ppl is alive, might as well just leave it there as if dead
						Component.translatable("wuxiacraft.death.login"), player.getServer().isHardcore()));
	}

	public static void fixEnergies(Player player) {
		var cultivation = Cultivation.get(player);
		for (var system : System.values()) {
			var energy = cultivation.getStat(system, PlayerSystemStat.ENERGY);
			var maxEnergy = cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY);
			if (energy.compareTo(maxEnergy.multiply(new BigDecimal("1.5"))) >= 0) {
				cultivation.setStat(system, PlayerSystemStat.ENERGY, maxEnergy);
			}
		}
	}

	/**
	 * This fires after a player has properly spawned after something
	 * Fixing the Clone event sync issues
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent
	public static void onPlayerResurrect(PlayerEvent.PlayerRespawnEvent event) {
		var player = event.getEntity();
		syncClientCultivation((ServerPlayer) player);
		if (player.getTags().contains("PLEASE_RTP_ME")) {
			player.removeTag("PLEASE_RTP_ME");
			var rtpX = player.getPersistentData().getDouble("rtp_to_x");
			var rtpY = player.getPersistentData().getDouble("rtp_to_y");
			var rtpZ = player.getPersistentData().getDouble("rtp_to_z");
			player.teleportTo(rtpX, rtpY, rtpZ);
			player.getPersistentData().remove("rtp_to_x");
			player.getPersistentData().remove("rtp_to_y");
			player.getPersistentData().remove("rtp_to_z");
		}
	}

	/**
	 * When players wake up they'll recover blood energy and mental energy, because player rested
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent
	public static void onServerWakeUp(SleepFinishedTimeEvent event) {
		var level = event.getLevel();
		level.players().stream().filter(LivingEntity::isSleeping).forEach(player -> {
			ICultivation cultivation = Cultivation.get(player);
			cultivation.setStat(System.BODY, PlayerSystemStat.ENERGY, cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY));
			cultivation.setStat(System.DIVINE, PlayerSystemStat.ENERGY, cultivation.getStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY));
			if (!level.isClientSide()) {
				syncClientCultivation((ServerPlayer) player);
			}
		});
	}

}
