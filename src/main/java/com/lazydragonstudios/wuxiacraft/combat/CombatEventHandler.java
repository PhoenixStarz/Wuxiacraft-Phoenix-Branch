package com.lazydragonstudios.wuxiacraft.combat;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.networking.CultivationSyncMessage;
import com.lazydragonstudios.wuxiacraft.networking.TurnSemiDeadStateMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatEventHandler {

	/**
	 * Barriers will break the damage event even before it happens.
	 * With this, knock backs are gonna be ignored and also armor damage.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public static void onPlayerTakesDamage(LivingAttackEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		var cultivation = Cultivation.get(player);
		BigDecimal barrier_amount = cultivation.getStat(PlayerStat.BARRIER);
		var damage = BigDecimal.valueOf(event.getAmount());
		DamageSource source = event.getSource();
		if (source instanceof WuxiaDamageSource wuxiaDamage) damage = wuxiaDamage.getDamage();
		else if (source.getEntity() instanceof Player attacker) {
			damage = damage.add(Cultivation.get(attacker).getStat(PlayerStat.STRENGTH, false));
		}
		if (barrier_amount.compareTo(BigDecimal.ZERO) > 0) {
			cultivation.setStat(PlayerStat.BARRIER, barrier_amount.subtract(damage).max(BigDecimal.ZERO));
			cultivation.setStat(PlayerStat.BARRIER_REGEN_COOLDOWN, BigDecimal.valueOf(90));
			damage = damage.subtract(barrier_amount).max(BigDecimal.ZERO);
			if (player instanceof ServerPlayer serverPlayer)
				CultivationEventHandler.syncClientCultivation(serverPlayer);
		}
		if (damage.compareTo(BigDecimal.ZERO) <= 0) event.setCanceled(true);
	}

	/**
	 * All damage done to players will be intersected and be applied through here!
	 * This will post a LivingDamageEvent for compatibility
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerHurt(LivingHurtEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		var cultivation = Cultivation.get(player);
		WuxiaDamageSource source;
		//if not one of our damage sources
		if (!(event.getSource() instanceof WuxiaDamageSource)) {
			//then we convert it
			source = getElementalSourceFromVanillaSource(event.getSource(), event.getAmount());
		} else {
			source = (WuxiaDamageSource) event.getSource();
		}
		var sourceElement = source.getElement();
		var srcElementRegistryKey = WuxiaRegistries.ELEMENTS.get().getKey(sourceElement);
		var resistance = cultivation.getStat(srcElementRegistryKey, PlayerElementalStat.RESISTANCE);
		Entity attacker = source.getEntity();
		if (attacker instanceof Player) {
			var pierce = Cultivation.get((Player) attacker).getStat(srcElementRegistryKey, PlayerElementalStat.PIERCE);
			resistance = resistance.subtract(pierce).max(BigDecimal.ZERO);
		}
		if (player.hasEffect(MobEffects.DAMAGE_RESISTANCE) && !source.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
			var resistanceInstance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
			var amplifier = resistanceInstance.getAmplifier()+1;
			var reduction = source.getDamage().multiply(BigDecimal.valueOf((amplifier * 5f) / 25f).setScale(2, RoundingMode.HALF_DOWN)).max(BigDecimal.ZERO);
			resistance = resistance.add(reduction);
		}
		var damage = source.getDamage().subtract(resistance).max(BigDecimal.ZERO);
		var armorValue = BigDecimal.valueOf(player.getArmorValue()).setScale(2, RoundingMode.HALF_DOWN);
		var toughnessValue = BigDecimal.valueOf(player.getAttributeValue(Attributes.ARMOR_TOUGHNESS)).setScale(2, RoundingMode.HALF_DOWN);
		var newDamage = getDamageAfterAbsorb(damage, armorValue, toughnessValue);
		var damageReduced = damage.subtract(newDamage);
		player.getInventory().hurtArmor(source, damageReduced.floatValue(), Inventory.ALL_ARMOR_SLOTS);

		if (newDamage.compareTo(BigDecimal.ZERO) > 0 && source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
			var enchantmentModifiers = new BigDecimal(EnchantmentHelper.getDamageProtection(player.getArmorSlots(), source));
			newDamage = getDamageAfterMagicAbsorb(newDamage, enchantmentModifiers);
		}

		boolean isInstantDeath = source.isInstantDeath();
		source = new WuxiaDamageSource(source.typeHolder(), source.getElement(), source.getEntity(), newDamage);
		if (isInstantDeath) {
			source = source.setInstantDeath();
		}

		ForgeHooks.onLivingDamage(event.getEntity(), source, source.getDamage().floatValue());
		event.setCanceled(true);
	}

	/**
	 * Same rules as vanilla one but with BigDecimals
	 *
	 * @param damage             the damage dealt to the player
	 * @param totalArmor         the armor value of the player armor
	 * @param toughnessAttribute the toughness of said armor
	 * @return the damage after armor made it's work
	 */
	public static BigDecimal getDamageAfterAbsorb(BigDecimal damage, BigDecimal totalArmor, BigDecimal toughnessAttribute) {
		BigDecimal f = BigDecimal.valueOf(2).add(toughnessAttribute).divide(BigDecimal.valueOf(4), RoundingMode.HALF_UP);
		BigDecimal f1 = totalArmor.subtract(damage.divide(f, RoundingMode.HALF_UP)).max(totalArmor.multiply(new BigDecimal("0.2"))).min(BigDecimal.valueOf(20));
		return damage.multiply(BigDecimal.ONE.subtract(f1.divide(BigDecimal.valueOf(25), RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Same rules as vanilla one but with BigDecimals
	 *
	 * @param damage           incoming damage
	 * @param enchantModifiers enchandment modifiers
	 * @return the damage after enchantments made their work
	 */
	public static BigDecimal getDamageAfterMagicAbsorb(BigDecimal damage, BigDecimal enchantModifiers) {
		var f = enchantModifiers.max(BigDecimal.ZERO).min(BigDecimal.valueOf(20));
		return damage.multiply(BigDecimal.ONE.subtract(f.divide(new BigDecimal("25"), RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Converts a vanilla damage source of any type to elemental damage source
	 *
	 * @param source the vanilla source to be converted from
	 * @return the wuxia damage source with an element
	 */
	private static WuxiaDamageSource getElementalSourceFromVanillaSource(DamageSource source, float amount) {
		if (source.is(DamageTypes.LAVA))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.FIRE.get(), new BigDecimal(amount)).setInstantDeath();
		else if (source.is(DamageTypes.LIGHTNING_BOLT))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.LIGHTNING.get(), new BigDecimal(amount)).setInstantDeath();
		else if (source.is(DamageTypes.FREEZE) || source.is(DamageTypes.DROWN))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.WATER.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.WITHER))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.TIME.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.MAGIC))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.POISON.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.OUTSIDE_BORDER))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.PHYSICAL.get(), new BigDecimal(amount)).setInstantDeath();
		return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.PHYSICAL.get(), source.getEntity(), new BigDecimal(amount));
	}

	/**
	 * This will intersect all player damages
	 * This is here mostly for compatibility
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerDamage(LivingDamageEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		if (!(event.getSource() instanceof WuxiaDamageSource source)) return;
		var cultivation = Cultivation.get(player);
		var damage = source.getDamage();
		cultivation.setStat(PlayerStat.HEALTH, cultivation.getStat(PlayerStat.HEALTH).subtract(damage));

		event.getEntity().getCombatTracker().recordDamage(event.getSource(), event.getAmount());
		player.awardStat(Stats.DAMAGE_TAKEN, (int) event.getAmount());
		//decided that food exhaustion has nothing to do with damage.
		//and it'll be used to heal the character anyway.

		if (cultivation.getStat(PlayerStat.HEALTH).compareTo(BigDecimal.ZERO) <= 0) {
			if (source.isInstantDeath()) {
				player.setHealth(-1);
			} else {
				cultivation.setSemiDeadState(true);
				WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
						new TurnSemiDeadStateMessage(true, event.getSource().getLocalizedDeathMessage(player),
								player.getServer() != null && player.getServer().isHardcore()));
			}
		}

		//this will make players health bar always keep up with
		WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new CultivationSyncMessage(cultivation));
		event.setCanceled(true);
	}

	/**
	 * Only works in combat mode
	 * This will add the strength to basic attacks from the player
	 * This way I guess players won't have a ton of modifiers
	 * And I can also send mobs flying away
	 *
	 * @param event A description of what's happening
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onPlayerDealsDamage(LivingHurtEvent event) {
		if (!(event.getSource().getEntity() instanceof Player player)) return;
		if (event.getSource() instanceof WuxiaDamageSource)
			return; // Means it was wuxiacraft that came up with this attack, so damage is already calculated

		ICultivation cultivation = Cultivation.get(player);
		if (!cultivation.isCombat()) return;
		event.setAmount(event.getAmount() + cultivation.getStat(PlayerStat.STRENGTH).floatValue());
		//if it was a punch, then we apply a little of knock back
		if (player.getItemInHand(InteractionHand.MAIN_HAND) == ItemStack.EMPTY) return;
		LivingEntity target = event.getEntity();
		double maxHP = target.getMaxHealth();
		if (target instanceof Player targetPlayer)
			maxHP = Cultivation.get(targetPlayer).getStat(PlayerStat.MAX_HEALTH).doubleValue();
		double knockSpeed = MathUtil.clamp((event.getAmount() * 0.7 - maxHP * 2) * 0.3, 0, 12);
		Vec3 diff = Objects.requireNonNull(event.getSource().getSourcePosition()).subtract(event.getEntity().getPosition(0.5f));
		diff = new Vec3(diff.x, 0.0, diff.y);
		diff = diff.normalize();
		target.knockback((float) knockSpeed, diff.x, diff.z);
	}

	/**
	 * Normally when healing from other sources, like different mods
	 *
	 * @param event A description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerHeal(LivingHealEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		if (event.getAmount() <= 0) return;
		event.setCanceled(true);
		var amount = BigDecimal.valueOf(event.getAmount());
		var cultivation = Cultivation.get(player);
		cultivation.addStat(PlayerStat.HEALTH, amount);
		cultivation.setStat(PlayerStat.HEALTH, cultivation.getStat(PlayerStat.HEALTH).min(cultivation.getStat(PlayerStat.MAX_HEALTH)));
	}

}
