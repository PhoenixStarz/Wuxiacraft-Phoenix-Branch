package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.SkillActivatorAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.init.*;
import com.lazydragonstudios.wuxiacraft.networking.*;
import com.lazydragonstudios.wuxiacraft.world.data.WuxiaSavedData;
import com.lazydragonstudios.wuxiacraft.world.dimension.DimensionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import vazkii.patchouli.api.PatchouliAPI;

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
		if (player == null || player.isSpectator()) return;
		player.level().getProfiler().push("playerCultivationUpdate");
		//defining variables I'm sure I'm gonna use a lot inside here
		ICultivation cultivation = Cultivation.get(player);
		var bodyData = cultivation.getSystemData(System.BODY);
		var divineData = cultivation.getSystemData(System.DIVINE);
		var essenceData = cultivation.getSystemData(System.ESSENCE);
		var itemStack = player.getInventory().getItem(0);

		handleClientSync(player, cultivation);
		handleSkillCasting(player, cultivation);
		handleBodyEnergyRegen(player, cultivation, bodyData);
		handleEnergyRegen(player, cultivation);
		handleEnergyOverflow(cultivation);
		handleBarrierRegen(cultivation, essenceData);
		handleHungerRegen(player, cultivation, essenceData);
		handleExerciseEnergies(cultivation, bodyData, divineData, essenceData);
		handleExerciseAddingCultBase(player, cultivation, bodyData);
		handleLowEnergyPunishments(player, cultivation, bodyData, divineData);
		handleCustomPotionEffects(player, cultivation, divineData, essenceData, bodyData);
		handleCombatMovementCosts(player, cultivation);
		handleParticlesWhenFlying(player, cultivation);
		handleShouldAddStepAssist(player, cultivation);
		handlePlayerHealth(player, cultivation);
		handleHealthRegeneration(player, cultivation, bodyData);
		handleCanFly(player, cultivation);
		if (!event.side.isClient()) {
		handleTribulationTick((ServerPlayer)player);
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
				cultivation.isCombat() && essenceStage.isCanHaveBarrier()) {
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
		if (essenceStage.isCanConvertToFood()) {
			BigDecimal cost = cultivation.getStat(PlayerStat.HUNGER_REGEN_COST);
			float regenAmount = cultivation.getStat(PlayerStat.HUNGER_REGEN).floatValue();
			BigDecimal maxEnergy = cultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY);
			FoodData foodData = player.getFoodData();
			if ((foodData.getFoodLevel() < 20 || foodData.getSaturationLevel() < foodData.getFoodLevel())
				 && essenceData.consumeEnergy(cost)) {
				float saturationLevel = foodData.getSaturationLevel();
				int foodLevel = foodData.getFoodLevel();
				saturationLevel = Math.min(saturationLevel + 0.5f, foodLevel);
				if (saturationLevel >= foodLevel) {
					foodLevel = Math.min(foodLevel + 1, 20);
				}
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
	private static void handlePlayerHealth(Player player, ICultivation cultivation) {
		var attributes = player.getAttributes();
		var maxHealthInstance = attributes.getInstance(Attributes.MAX_HEALTH);
		   double extraHealth = (cultivation.getStat(PlayerStat.MAX_HEALTH).subtract(new BigDecimal("20"))).doubleValue();
		   var modifier = new AttributeModifier(UUID.fromString("fac24357-405f-4870-a21f-d386cc22039d"), "wuxiacraft.health_modifier", +extraHealth, AttributeModifier.Operation.ADDITION);
		   maxHealthInstance.removeModifier(modifier);
			  maxHealthInstance.addPermanentModifier(modifier);
	}
	

	/**
	 * Body energy into health regeneration
	 *
	 * @param event A description of what is happening
	 */
	public static void handleHealthRegeneration(Player player, ICultivation cultivation, SystemContainer bodyData) {
		BigDecimal regen = cultivation.getStat(PlayerStat.HEALTH_REGEN);
		BigDecimal cost = cultivation.getStat(PlayerStat.HEALTH_REGEN_COST);
		BigDecimal maxEnergy = cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY);
		if (player.level().isClientSide()) return;
		if (cultivation.getDemonicStage() > 20) {
			Holder<DamageType> damageType = player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.DEMONIC_CORRUPTION);
			player.hurt(new WuxiaDamageSource(damageType, WuxiaElements.DEMONIC.get(), player,
					regen.multiply(new BigDecimal(cultivation.getDemonicStage()/100f)).add(BigDecimal.ONE)), regen.floatValue() * cultivation.getDemonicStage()/100f + 1f);
		}else {
			if (player.getHealth() < player.getMaxHealth() && bodyData.hasEnergy(maxEnergy.multiply(new BigDecimal("0.25"))) && bodyData.consumeEnergy(cost)) {
				player.heal(regen.floatValue());
			}
		}
	}

	private static void handleClientSync(Player player, ICultivation cultivation) {
		//Sync the cultivation with the client every so often
		cultivation.advanceTimer();
		if (cultivation.getTimer() >= 100) {
			cultivation.resetTimer();
			if (!player.level().isClientSide()) {
				var TimeofDay = player.level().getGameTime();
				cultivation.setToD(TimeofDay);
				cultivation.setStat(PlayerStat.CULTPOINT, cultivation.getStat(PlayerStat.CULTPOINT).add(BigDecimal.ONE).min(BigDecimal.valueOf(WuxiaConfigs.MAX_CULTPOINTS.get())));
				//around 1h for full recovery
				BigDecimal demonicFoundation = cultivation.getStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION);
				BigDecimal maxCultivationBase = cultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE);
				if(demonicFoundation.compareTo(BigDecimal.ZERO) > 0) {
					cultivation.setStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, 
							demonicFoundation.subtract(maxCultivationBase.multiply(new BigDecimal(0.00138))).max(BigDecimal.ZERO));
					cultivation.setDemonicStage(10*demonicFoundation.intValue()/maxCultivationBase.intValue());
					cultivation.addStat(WuxiaElements.DEMONIC.getId(), PlayerElementalStat.COMPREHENSION, 
							demonicFoundation.subtract(cultivation.getStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION)).divide(new BigDecimal(100)));
				}
				syncClientCultivation((ServerPlayer) player);
				for (var system : System.values()) {
					var systemData = cultivation.getSystemData(system);
					systemData.techniqueData.grid.fixProficiencies(cultivation.getAspects());
				}
			}
		}
	}

	// TODO change CAST SPEED and COOLDOWN SPEED to their respective systems later on
	private static void handleSkillCasting(Player player, ICultivation cultivation) {
		var skillData = cultivation.getSkills();
		var selectedSkill = skillData.getSkillAt(skillData.selectedSkill);
		if (cultivation.isCombat()) {
			if (skillData.casting && selectedSkill.getStatValue(SkillStat.CURRENT_COOLDOWN).compareTo(BigDecimal.ZERO) <= 0) {
				if (!selectedSkill.getSkillChain().isEmpty() && selectedSkill.getSkillChain().getFirst() instanceof SkillActivatorAspect activator) {
					selectedSkill.addStat(SkillStat.CURRENT_CASTING, cultivation.getSystemData(System.ESSENCE).getStat(PlayerSystemStat.CAST_SPEED).multiply(BigDecimal.TEN));
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
				skill.addStat(SkillStat.CURRENT_COOLDOWN, new BigDecimal("-1").multiply(cultivation.getSystemData(System.ESSENCE).getStat(PlayerSystemStat.COOLDOWN_SPEED).multiply(BigDecimal.TEN)));
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
		if (player.getFoodData().getFoodLevel() > 5) {
			BigDecimal hunger_modifier = new BigDecimal("0.2");
			if (player.getFoodData().getFoodLevel() >= 10) hunger_modifier = hunger_modifier.add(new BigDecimal("0.3"));
			if (player.getFoodData().getFoodLevel() >= 15) hunger_modifier = hunger_modifier.add(new BigDecimal("0.3"));
			if (player.getFoodData().getFoodLevel() >= 20) hunger_modifier = hunger_modifier.add(new BigDecimal("0.3"));
			BigDecimal finalEnergyRegen = cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY_REGEN).multiply(hunger_modifier);
			//bodyEnergy < bodyMaxEnergy * 0.7 (70%)
			BigDecimal maxEnergyMultiplicand = new BigDecimal(cultivation.isWithinFormationRange() ? "1" : "0.7");
			boolean canRegenBodyEnergy = cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY).compareTo(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(maxEnergyMultiplicand)) < 0;
			if (canRegenBodyEnergy) {
				bodyData.addEnergy(cultivation.getStat(System.BODY, PlayerSystemStat.MAX_ENERGY).multiply(maxEnergyMultiplicand).subtract(cultivation.getStat(System.BODY, PlayerSystemStat.ENERGY))
						.min(finalEnergyRegen).max(BigDecimal.ZERO));
					//^energy regen limiter based of max energy 
				if (finalEnergyRegen.floatValue() > 5 ) finalEnergyRegen = new BigDecimal(5);
				if (!cultivation.isWithinFormationRange())
				player.causeFoodExhaustion(finalEnergyRegen.floatValue());
			}
		}
	}

	private static void handleEnergyRegen(Player player, ICultivation cultivation) {
		//others don't
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			if (system != System.BODY) { //body already regenerated at that points
				if (systemData.getStage().canRegenEnergy() || cultivation.isWithinFormationRange())
				systemData.addEnergy(cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY).subtract(cultivation.getStat(system, PlayerSystemStat.ENERGY))
						.min(cultivation.getStat(system, PlayerSystemStat.ENERGY_REGEN)).max(BigDecimal.ZERO));
					//^energy regen limiter based of max energy 
			}	
			//kill if above 150%
			var systemDamageType = WuxiaDamageTypes.ENERGY_EXCESS_ESSENCE;
			if (system == System.BODY) {
				systemDamageType = WuxiaDamageTypes.ENERGY_EXCESS_BODY;
			} else
			if (system == System.DIVINE) {
				systemDamageType = WuxiaDamageTypes.ENERGY_EXCESS_DIVINE;
			} 
			if (cultivation.getStat(system, PlayerSystemStat.ENERGY).compareTo(cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("1.5"))) > 0) {
				killPlayerWithExplosion(player, systemData, player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(systemDamageType),
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
				essenceData.addEnergy(cultivation.getStat(PlayerStat.EXERCISE_CONVERSION).min((cultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY).subtract(cultivation.getStat(System.ESSENCE, PlayerSystemStat.ENERGY)))).max(BigDecimal.ZERO));
			}
		}
	}

	private static void handleExerciseAddingCultBase(Player player, ICultivation cultivation, SystemContainer bodyData) {
			//if player is exercising, add a little of cult to him
			if (cultivation.isExercising() && bodyData.techniqueData.modifier.isValidTechnique()) {
				cultivation.advanceCultTimer();
				if (cultivation.getCultTimer() > 200) {// 10.05s //
					cultivation.resetCultTimer();
					cultivation.addCultivationBase(player, System.BODY, cultivation.getStat(PlayerStat.EXERCISE_CONVERSION).add(BigDecimal.ONE));
				}
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
		if ((!player.onGround() && !player.getAbilities().flying) || ClientAnimationState.get(player).isSwordFlight()) return;
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
		if (player.isInvisible()) return;
		if (!player.getAbilities().flying) return;
		if (cultivation.getTimer() % 5 != 0) return;
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

	private static void handleCustomPotionEffects(Player player, ICultivation cultivation, SystemContainer divineData, SystemContainer essenceData, SystemContainer bodyData) {
		MathContext mc = new MathContext(16, RoundingMode.HALF_DOWN);
		if (player.hasEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get())) {
			var effectInstance = player.getEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get());
			if (effectInstance != null) {
				// 0.022 * 2.4 ^ amplifier
				essenceData.addEnergy(new BigDecimal("0.022").multiply(new BigDecimal("2.4").pow(effectInstance.getAmplifier()), mc));
				// 0.009 * 1.8 ^ amplifier
				divineData.addEnergy(new BigDecimal("0.009").multiply(new BigDecimal("1.8").pow(effectInstance.getAmplifier()), mc));
			}
		} 
		if (player.hasEffect(WuxiaMobEffects.ENLIGHTENMENT.get())) {
			var effectInstance = player.getEffect(WuxiaMobEffects.ENLIGHTENMENT.get());
			if (effectInstance != null) {
				// 0.022 * 2.4 ^ amplifier
				divineData.addEnergy(new BigDecimal("0.022").multiply(new BigDecimal("2.4").pow(effectInstance.getAmplifier()), mc));
				// 0.009 * 1.8 ^ amplifier
				bodyData.addEnergy(new BigDecimal("0.009").multiply(new BigDecimal("1.8").pow(effectInstance.getAmplifier()), mc));
			}
		}
		if (player.hasEffect(WuxiaMobEffects.PILL_RESONANCE.get())) {
			var effectInstance = player.getEffect(WuxiaMobEffects.PILL_RESONANCE.get());
			if (effectInstance != null) {
				// 0.022 * 2.4 ^ amplifier
				bodyData.addEnergy(new BigDecimal("0.022").multiply(new BigDecimal("2.4").pow(effectInstance.getAmplifier()), mc));
				// 0.009 * 1.8 ^ amplifier
				essenceData.addEnergy(new BigDecimal("0.009").multiply(new BigDecimal("1.8").pow(effectInstance.getAmplifier()), mc));
			}
		}
	}

	private static void handleTribulationTick(ServerPlayer player) {
		ICultivation cultivation = Cultivation.get(player);
        if (!cultivation.isTribulating()) return;
        Tribulation tribulation = cultivation.getTribulation();
        var systemData = cultivation.getSystemData(tribulation.tribSystem);
		if (player.isAlive()) { 
        	if (tribulation.tick(player)) {
                cultivation.setTribulating(false);
				if (cultivation.attemptBreakthrough(tribulation.tribSystem))
				player.sendSystemMessage(Component.translatable("wuxiacraft.breakthough_successful")
						.append(Component.translatable(systemData.currentStage.getNamespace() + ".stage." + systemData.currentStage.getPath())),
					true);
            } 
		}
		else {
			tribulation.reset();
			cultivation.setTribulating(false);
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
		if (player.level().isClientSide()); else {
		var interaction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(player.level(), player) ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE;
		player.level().explode(null, player.getX(), player.getY(), player.getZ(), 5f, false, interaction);
		player.hurt(new WuxiaDamageSource(damageType, WuxiaElements.PHYSICAL.get(), player, amount).setInstantDeath(), amount.floatValue());
		}
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
		if (event.isWasDeath()) {
			//oldCultivation.setSkillCooldown(0);
			if (WuxiaConfigs.LIVES_ENABLED.get())
			oldCultivation.setStat(PlayerStat.LIVES, oldCultivation.getStat(PlayerStat.LIVES).subtract(BigDecimal.ONE));
			if (oldCultivation.getStat(PlayerStat.LIVES).compareTo(BigDecimal.ZERO) == 0) {
				var player = event.getOriginal();
				Level level = player.level();
				ItemStack itemStack = new ItemStack(WuxiaItems.SOUL_CORE.get(), 1);
				CompoundTag	tag = new CompoundTag();
				itemStack.setTag(tag);
				tag.putString("name", player.getDisplayName().getString());
				for (System systems : System.values()) {
					CompoundTag	systemTag = new CompoundTag();
					SystemContainer systemData = oldCultivation.getSystemData(systems);
					systemTag.putString("stage", systemData.currentStage.getNamespace() + ".stage." + systemData.currentStage.getPath());
					double progress = (oldCultivation.getStat(systems, PlayerSystemStat.MAX_CULTIVATION_BASE).doubleValue()
							+(oldCultivation.getStat(systems, PlayerSystemStat.CULTIVATION_BASE)).doubleValue()*9)/10;
					systemTag.putDouble("amount", progress);
					tag.put(systems.toString().toLowerCase(), systemTag);
				}
				tag.putInt("durability", 100);
				int rebirthCount = oldCultivation.getRebirths();
				oldCultivation = new Cultivation();
				oldCultivation.setRebirths(rebirthCount);
				ItemEntity entity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemStack);
				entity.setNoPickUpDelay();
				level.addFreshEntity(entity);
			} else {
				var bodyData = oldCultivation.getSystemData(System.BODY);
				var divineData = oldCultivation.getSystemData(System.DIVINE);
				var essenceData = oldCultivation.getSystemData(System.ESSENCE);
				bodyData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("7"));
				divineData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("10"));
				essenceData.setStat(PlayerSystemStat.ENERGY, new BigDecimal("0"));
				BigDecimal demonicFoundation = oldCultivation.getStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION);
				BigDecimal maxCultivationBase = oldCultivation.getStat(System.ESSENCE, PlayerSystemStat.MAX_CULTIVATION_BASE);
				oldCultivation.setStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, 
						demonicFoundation.subtract(maxCultivationBase).min(BigDecimal.ZERO));
			}
		}
		event.getOriginal().invalidateCaps();
		newCultivation.deserialize(oldCultivation.serialize());
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		var cultivation = Cultivation.get(player);
		long T1= player.level().getGameTime();
		long T2= cultivation.getToD();
		cultivation.setStat(PlayerStat.CULTPOINT, cultivation.getStat(PlayerStat.CULTPOINT).add(BigDecimal.valueOf((T1-T2)/100L)).min(BigDecimal.valueOf(WuxiaConfigs.MAX_CULTPOINTS.get())));
		syncClientCultivation((ServerPlayer) player);
		fixEnergies(player);
		CompoundTag playerData = event.getEntity().getPersistentData();
        CompoundTag data;
        if (!playerData.contains(Player.PERSISTED_NBT_TAG)) {
            data = new CompoundTag();
        } else {
            data = playerData.getCompound(Player.PERSISTED_NBT_TAG);
        }
        if (!player.level().isClientSide()) {
       	 	if (ModList.get() != null && ModList.get().getModContainerById("patchouli").isPresent()) {
                if (WuxiaConfigs.STARTER_BOOK.get()){
                    if (!data.getBoolean("wuxiacraft:starterBook")) {
                        ItemStack book = PatchouliAPI.get().getBookStack(new ResourceLocation("wuxiacraft:cultivators_codex"));
                        event.getEntity().addItem(book);
                        data.putBoolean("wuxiacraft:starterBook", true);
                        playerData.put(Player.PERSISTED_NBT_TAG, data);
                    }
                }
            }
        }
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

	@SubscribeEvent
    public static void onLevelLoaded(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD) {
            WuxiaSavedData.init(serverLevel.getDataStorage());
        }
    }

}
