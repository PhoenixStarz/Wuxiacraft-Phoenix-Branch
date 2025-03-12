package com.lazydragonstudios.wuxiacraft.combat;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.networking.CultivationSyncMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;

import java.math.BigDecimal;
import java.rmi.server.Operation;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatEventHandler {
	 
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
		event.setAmount(event.getAmount() + (cultivation.getStat(PlayerStat.STRENGTH)).floatValue());
		//if it was a punch, then we apply a little of knock back
		if (player.getItemInHand(InteractionHand.MAIN_HAND) == ItemStack.EMPTY) return;
		LivingEntity target = event.getEntityLiving();
		double maxHP = target.getMaxHealth();
		if (target instanceof Player targetPlayer)
			maxHP = Cultivation.get(targetPlayer).getStat(PlayerStat.MAX_HEALTH).doubleValue();
		double knockSpeed = MathUtil.clamp((event.getAmount() * 0.7 - maxHP) * 0.3, 0, 12);
		Vec3 diff = Objects.requireNonNull(event.getSource().getSourcePosition()).subtract(event.getEntityLiving().getPosition(0.5f));
		diff = diff.normalize();
		target.knockback((float) knockSpeed, diff.x, diff.z);
	}
/* 
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onPlayerHeal(LivingHealEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		if (event.getAmount() <= 0) return;
		var amount = BigDecimal.valueOf(event.getAmount());
		var cultivation = Cultivation.get(player);
		var bodyData = cultivation.getSystemData(System.BODY);
		BigDecimal energy_used = cultivation.getStat(PlayerStat.HEALTH_REGEN_COST);
		BigDecimal amount_healed = cultivation.getStat(PlayerStat.HEALTH_REGEN);
		if (cultivation.isCombat()) {
			if (bodyData.getStat(PlayerSystemStat.ENERGY).subtract(energy_used).compareTo(bodyData.getStat(PlayerSystemStat.MAX_ENERGY).multiply(new BigDecimal("0.1"))) >= 0) {
			if (bodyData.consumeEnergy(energy_used)) { // this is the correct way to use consume energy ever
				event.setAmount((amount.add(amount_healed)).floatValue());
				}
			}
		}
	}
*/
}
