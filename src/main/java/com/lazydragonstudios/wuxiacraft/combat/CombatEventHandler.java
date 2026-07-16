package com.lazydragonstudios.wuxiacraft.combat;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaItems;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.networking.CultivationSyncMessage;
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
import net.minecraft.world.entity.EquipmentSlot;
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
			int armorC = 0;
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == WuxiaItems.CELESTIAL_HELMET.get()) armorC += 10;
			if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == WuxiaItems.CELESTIAL_CHESTPLATE.get()) armorC += 20;
			if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == WuxiaItems.CELESTIAL_LEGGINGS.get()) armorC += 15;
			if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == WuxiaItems.CELESTIAL_BOOTS.get()) armorC += 10;
			if (armorC >= 1) resistance = resistance.multiply(new BigDecimal(armorC));
			var pierce = Cultivation.get((Player) attacker).getStat(srcElementRegistryKey, PlayerElementalStat.PIERCE);
			if (((Player) attacker).getItemBySlot(EquipmentSlot.MAINHAND).getItem() == WuxiaItems.CELESTIAL_SWORD.get() ||
				((Player) attacker).getItemBySlot(EquipmentSlot.MAINHAND).getItem() == WuxiaItems.CELESTIAL_AXE.get() ||
				((Player) attacker).getItemBySlot(EquipmentSlot.MAINHAND).getItem() == WuxiaItems.CELESTIAL_PICKAXE.get() ||		
				((Player) attacker).getItemBySlot(EquipmentSlot.MAINHAND).getItem() == WuxiaItems.CELESTIAL_SHOVEL.get() ||		
				((Player) attacker).getItemBySlot(EquipmentSlot.MAINHAND).getItem() == WuxiaItems.CELESTIAL_HOE.get()) { 
					pierce = pierce.multiply(new BigDecimal("1.5")); 
				}
			resistance = resistance.subtract(pierce).max(BigDecimal.ZERO);
		}
		var damage = source.getDamage().subtract(resistance).max(BigDecimal.ZERO);
		float fdamage = damage.floatValue();
		event.setAmount(fdamage);
	}

	/**
	 * Converts a vanilla damage source of any type to elemental damage source
	 *
	 * @param source the vanilla source to be converted from
	 * @return the wuxia damage source with an element
	 */
	private static WuxiaDamageSource getElementalSourceFromVanillaSource(DamageSource source, float amount) {
		if (source.is(DamageTypes.LAVA) ||
			source.is(DamageTypes.BAD_RESPAWN_POINT) ||
			source.is(DamageTypes.EXPLOSION) ||
			source.is(DamageTypes.FIREBALL) ||
			source.is(DamageTypes.FIREWORKS) ||
			source.is(DamageTypes.HOT_FLOOR) ||
			source.is(DamageTypes.IN_FIRE) ||
			source.is(DamageTypes.ON_FIRE) ||
			source.is(DamageTypes.PLAYER_EXPLOSION) ||
			source.is(DamageTypes.UNATTRIBUTED_FIREBALL))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.FIRE.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.LIGHTNING_BOLT) ||
				 source.is(DamageTypes.SONIC_BOOM))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.LIGHTNING.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.FREEZE) ||
				 source.is(DamageTypes.DROWN))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.WATER.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.STARVE) ||
				 source.is(DamageTypes.WITHER) ||
				 source.is(DamageTypes.WITHER_SKULL))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.TIME.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.MAGIC) ||
			 	 source.is(DamageTypes.INDIRECT_MAGIC) ||
				 source.is(DamageTypes.THORNS) ||
				 source.is(DamageTypes.STING))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.POISON.get(), new BigDecimal(amount));
		else if (source.is(DamageTypes.OUTSIDE_BORDER) || 
				source.is(DamageTypes.FELL_OUT_OF_WORLD)||
				source.is(DamageTypes.DRAGON_BREATH) ||
				source.is(DamageTypes.CRAMMING))
			return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.SPACE.get(), new BigDecimal(amount));
		return new WuxiaDamageSource(source.typeHolder(), WuxiaElements.PHYSICAL.get(), source.getEntity(), new BigDecimal(amount));
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
		if (player.getItemInHand(InteractionHand.MAIN_HAND) != ItemStack.EMPTY) return;
		LivingEntity target = event.getEntity();
		double maxHP = target.getMaxHealth();
		double knockSpeed = MathUtil.clamp((event.getAmount() * 0.7 - maxHP * 2) * 0.3, 0, 12);
		Vec3 diff = Objects.requireNonNull(event.getSource().getSourcePosition()).subtract(event.getEntity().getPosition(0.5f));
		diff = new Vec3(diff.x, 0.0, diff.y);
		diff = diff.normalize();
		target.knockback((float) knockSpeed, diff.x, diff.z);
	}
	
}
