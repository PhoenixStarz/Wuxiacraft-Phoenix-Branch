package com.lazydragonstudios.wuxiacraft.world.dimension;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.*;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimensionEventHandler {

	@SubscribeEvent
	public static void levelTick(TickEvent.LevelTickEvent event) {
		DimensionManager.INSTANCE.tick(event.level);
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
	if (event.getPlayer() != null) {
		Level level = event.getPlayer().level();
		if (!(level.dimension().equals(DimensionManager.DIVINE_DIMENSION))) return;
		Player player = event.getPlayer();
        if (player.isCreative()) return;
		ICultivation cultivation = Cultivation.get(player);
		SystemContainer divineData = cultivation.getSystemData(System.DIVINE);
		DivineCultivationStage stage = (DivineCultivationStage) divineData.getStage();
		BlockPos pos = DimensionManager.INSTANCE.structurePosForPlayer(player);
		double distSqr = event.getPos().distSqr(pos);
		if (distSqr < stage.getDimensionSize() * stage.getDimensionSize()) return;
		event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLivingDestroyBlock(LivingDestroyBlockEvent event) {
	if (event.getEntity() != null) {
		Level level = event.getEntity().level();
		if (!(level.dimension().equals(DimensionManager.DIVINE_DIMENSION))) return;
		if (!(event.getEntity() instanceof Player player)) {
			event.setCanceled(true);
			return;
		}
        if (player.isCreative()) return;
		ICultivation cultivation = Cultivation.get(player);
		SystemContainer divineData = cultivation.getSystemData(System.DIVINE);
		DivineCultivationStage stage = (DivineCultivationStage) divineData.getStage();
		BlockPos pos = DimensionManager.INSTANCE.structurePosForPlayer(player);
		double distSqr = event.getPos().distSqr(pos);
		if (distSqr < stage.getDimensionSize() * stage.getDimensionSize()) return;
		event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
	if (event.getEntity() != null) {
		Level level = event.getEntity().level();
		if (!(level.dimension().equals(DimensionManager.DIVINE_DIMENSION))) return;
		if (!(event.getEntity() instanceof Player player)) {
			event.setCanceled(true);
			return;
		}
        if (player.isCreative()) return;
		ICultivation cultivation = Cultivation.get(player);
		SystemContainer divineData = cultivation.getSystemData(System.DIVINE);
		DivineCultivationStage stage = (DivineCultivationStage) divineData.getStage();
		BlockPos pos = DimensionManager.INSTANCE.structurePosForPlayer(player);
		double distSqr = event.getPos().distSqr(pos);
		if (distSqr < stage.getDimensionSize() * stage.getDimensionSize()) return;
		event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onBlockPlaceMulti(BlockEvent.EntityMultiPlaceEvent event) {
	if (event.getEntity() != null) {
		Level level = event.getEntity().level();
		if (!(level.dimension().equals(DimensionManager.DIVINE_DIMENSION))) return;
		if (!(event.getEntity() instanceof Player player)) {
			event.setCanceled(true);
			return;
		}
        if (player.isCreative()) return;
		ICultivation cultivation = Cultivation.get(player);
		SystemContainer divineData = cultivation.getSystemData(System.DIVINE);
		DivineCultivationStage stage = (DivineCultivationStage) divineData.getStage();
		BlockPos pos = DimensionManager.INSTANCE.structurePosForPlayer(player);
		double distSqr = event.getPos().distSqr(pos);
		if (distSqr < stage.getDimensionSize() * stage.getDimensionSize()) return;
		event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onExplosion(ExplosionEvent event) {
	Level level = event.getLevel();
	if (!(level.dimension().equals(DimensionManager.DIVINE_DIMENSION))) return;
	event.setCanceled(true);
	}
	

}
