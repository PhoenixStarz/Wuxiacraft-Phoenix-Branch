package com.lazydragonstudios.wuxiacraft.formation;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.FormationCoreBlock;
import com.lazydragonstudios.wuxiacraft.blocks.RunemakingTableBlock;
import com.lazydragonstudios.wuxiacraft.blocks.StatRuneBlock;
import com.lazydragonstudios.wuxiacraft.blocks.entity.FormationCore;
import com.lazydragonstudios.wuxiacraft.blocks.entity.RunemakingTable;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlocks;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRecipeTypes;
import com.lazydragonstudios.wuxiacraft.item.FormationBarrierBadge;
import com.lazydragonstudios.wuxiacraft.item.RuneStencil;
import com.lazydragonstudios.wuxiacraft.networking.PlayerAttackBarrierMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.util.LinkedList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FormationEventHandler {

	public static final TagKey<Item> CORE_ACCESS_TAG = ItemTags.create(new ResourceLocation(WuxiaCraft.MOD_ID, "core_access_badge"));

	public static final TagKey<Item> INTERACT_TAG = ItemTags.create(new ResourceLocation(WuxiaCraft.MOD_ID, "interact_badge"));

	public static final TagKey<Item> BREAK_TAG = ItemTags.create(new ResourceLocation(WuxiaCraft.MOD_ID, "break_badge"));

	public static final TagKey<Item> STATS_TAG = ItemTags.create(new ResourceLocation(WuxiaCraft.MOD_ID, "formation_stats_badge"));

	@SubscribeEvent
	public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		var cultivation = Cultivation.get(player);
		var formationPos = cultivation.getFormation();
		if (formationPos == null) return;
		var level = player.level();
		var bEntity = level.getBlockEntity(formationPos);
		if (!(bEntity instanceof FormationCore core)) {
			cultivation.setFormation(null);
			return;
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		Player player = event.player;
		Level level = player.level();
		level.getProfiler().push("playerFormationTick");
		var cultivation = Cultivation.get(player);
		cultivation.setWithinFormationRange(player.getX(), player.getY(), player.getZ());
		if (cultivation.isWithinFormationRange()) {
			var formationPos = cultivation.getFormationStats().getFormationActive();
			if (formationPos == null) {
				level.getProfiler().pop();
				return;
			}
			var blockEntity = level.getBlockEntity(formationPos);
			if (blockEntity == null || (blockEntity instanceof FormationCore core && !core.isActive())) {
				cultivation.getFormationStats().setFormationActive(null);
			}
			level.getProfiler().pop();
			return;
		} else {
			var playerPos = new BlockPos((int)player.getX(), (int)player.getY(), (int)player.getZ());
			var chunk = level.getChunkAt(playerPos);
			var activeFormationCores = getActiveFormationCoresNearby(chunk, level);
			for (var core : activeFormationCores) {
				if (player == core.getOwner()) continue;
				var coreRange = 12 + core.getRuneRange() * 2;
				var distSqr = playerPos.distSqr(core.getBlockPos());
				if (distSqr > coreRange * coreRange) continue;
				var badge = getItemBadge(player, core, STATS_TAG);
				if (badge == ItemStack.EMPTY) continue;
				var formationPos = core.getBlockPos();
				if (formationPos != null) {
					cultivation.setBarrierFormation(formationPos);
				}
			}
		}
		for (int i = 0; i < 2; i++) {
		var formationPos = cultivation.getFormation();
			if (i == 1) formationPos = cultivation.getBarrierFormation();
			if (formationPos == null) continue;
			var blockEntity = level.getBlockEntity(formationPos);
			if (!(blockEntity instanceof FormationCore core)) {
				level.getProfiler().pop();
				return;
			}
			if (!core.isActive()) {
				level.getProfiler().pop();
				return;
			}
			cultivation.getFormationStats().setFormationActive(formationPos);
			cultivation.getFormationStats().copyFrom(core.getFormationPlayerStats());
			cultivation.getFormationStats().setRange(12 + core.getRuneRange() * 2);
			cultivation.setWithinFormationRange(player.getX(), player.getY(), player.getZ());
			if (cultivation.isWithinFormationRange()) {
				level.getProfiler().pop();
				return;
			}
		}
		level.getProfiler().pop();
	}

	private static LinkedList<FormationCore> getActiveFormationCoresNearby(LevelChunk chunk, Level level) {
		int chunkRadius = 5;
		LinkedList<FormationCore> activeFormationCores = new LinkedList<>();
		for (int cx = -chunkRadius; cx <= chunkRadius; cx++) {
			for (int cz = -chunkRadius; cz <= chunkRadius; cz++) {
				var currentChunkPos = new ChunkPos(chunk.getPos().x + cx, chunk.getPos().z + cz);
				var currentChunk = level.getChunk(currentChunkPos.x, currentChunkPos.z);
				var blockEntities = currentChunk.getBlockEntities();
				for (var blockEntity : blockEntities.values()) {
					if (!(blockEntity instanceof FormationCore core)) continue;
					if (core.isActive()) {
						activeFormationCores.add(core);
					}
				}
			}
		}
		return activeFormationCores;
	}

	@SubscribeEvent
	public static void onPlayerMayBreak(BlockEvent.BreakEvent event) {
		var breaker = event.getPlayer();
		if (breaker != null) {
			if (breaker.isCreative()) return;
			Level level = breaker.level();
			var chunk = level.getChunkAt(event.getPos());
			var activeFormationCores = getActiveFormationCoresNearby(chunk, level);
			for (var core : activeFormationCores) {
				if (breaker == core.getOwner()) continue;
				var barrierAmount = core.getStat(FormationStat.BARRIER_AMOUNT);
				if (barrierAmount.compareTo(BigDecimal.ZERO) <= 0) continue;
				var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).doubleValue();
				var distSqr = event.getPos().distSqr(core.getBlockPos());
				if (distSqr > barrierRange * barrierRange) continue;
				if (event.getState().getBlock() instanceof FormationCoreBlock || event.getState().getBlock() instanceof StatRuneBlock) {
					var badge = getItemBadge(breaker, core, CORE_ACCESS_TAG);
					if (badge != ItemStack.EMPTY) continue;
				} else {
					var badge = getItemBadge(breaker, core, BREAK_TAG);
					if (badge != ItemStack.EMPTY) continue;
				}
				event.setCanceled(true);
				break;
			}
		} else event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		var interactive = event.getEntity();
		if (interactive == null || interactive.isCreative() || interactive.isSpectator()) return;
		Level level = interactive.level();
		var chunk = level.getChunkAt(event.getPos());
		var activeFormationCores = getActiveFormationCoresNearby(chunk, level);
		for (var core : activeFormationCores) {
			if (interactive == core.getOwner()) continue;
			var barrierAmount = core.getStat(FormationStat.BARRIER_AMOUNT);
			if (barrierAmount.compareTo(BigDecimal.ZERO) <= 0) continue;
			var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).doubleValue();
			var distSqr = event.getPos().distSqr(core.getBlockPos());
			if (distSqr > barrierRange * barrierRange) continue;
			Block block = level.getBlockState(event.getPos()).getBlock();
			if (block instanceof FormationCoreBlock || block instanceof StatRuneBlock) {
				var badge = getItemBadge(interactive, core, CORE_ACCESS_TAG);
				if (badge != ItemStack.EMPTY) continue;
			} else {
				var badge = getItemBadge(interactive, core, INTERACT_TAG);
				if (badge != ItemStack.EMPTY) continue;
			}
			event.setCanceled(true);
			break;
		}
	}

	private static ItemStack getItemBadge(Player player, FormationCore core, TagKey<Item> badgeTag) {
		var inv = player.getInventory();
		for (var itemStack : inv.items) {
			if (!(itemStack.getItem() instanceof FormationBarrierBadge)) continue;
			if (badgeTag != null && !(itemStack.is(badgeTag))) continue;
			var tag = itemStack.getTag();
			if (tag == null) continue;
			if (!tag.contains("formation")) continue;
			var formationTag = tag.getCompound("formation");
			String ownerTag = formationTag.getString("ownerName");
			String formationOwner = core.getOwnerName();
			if (!ownerTag.equals(formationOwner)) continue;
			int x = formationTag.getInt("x");
			int y = formationTag.getInt("y");
			int z = formationTag.getInt("z");
			var blockPos = new BlockPos(x, y, z);
			if (blockPos.compareTo(core.getBlockPos()) == 0) {
				return itemStack;
			}
		}
		return ItemStack.EMPTY;
	}

	@SubscribeEvent
	public static void onPlayerBreakWithStencil(BlockEvent.BreakEvent event) {
		var breaker = event.getPlayer();
		if (breaker == null) return;
		var itemInHand = breaker.getItemInHand(breaker.getUsedItemHand());
		if (!(itemInHand.getItem() instanceof RuneStencil)) return;
		var itemTag = itemInHand.getTag();
		int runeSelected = 0;
		if (itemTag != null) {
			if (itemTag.contains("runeSelected")) {
				runeSelected = itemTag.getInt("runeSelected");
			}
		}
		RunemakingTable fakeTable = new RunemakingTable(new BlockPos(breaker.getBlockX(), breaker.getBlockY(), breaker.getBlockZ()), WuxiaBlocks.RUNEMAKING_TABLE.get().defaultBlockState());
		fakeTable.setLevel(breaker.level());
		fakeTable.setItem(0, itemInHand);
		fakeTable.setItem(1, new ItemStack(event.getState().getBlock()));
		fakeTable.setSelectedRune(runeSelected);
		var result = fakeTable.getItem(2);
		if (result.isEmpty()) return;
		event.setCanceled(true);
		itemInHand.hurtAndBreak(1, event.getPlayer(), player -> {
			player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		var pos = event.getPos();
		event.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
		var entity = new ItemEntity(breaker.level(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, result);
		event.getLevel().addFreshEntity(entity);
	}

	@SubscribeEvent
	public static void onAttackOwner(AttackEntityEvent event) {
		var target = event.getTarget();
		if (!(target instanceof Player targetPlayer)) return;
		var targetCultivation = Cultivation.get(targetPlayer);
		var formationPos = targetCultivation.getFormation();
		if (formationPos == null) return;
		var blockEntity = targetPlayer.level().getBlockEntity(formationPos);
		if (!(blockEntity instanceof FormationCore core)) return;
		if (core.getStat(FormationStat.BARRIER_AMOUNT).compareTo(BigDecimal.ZERO) <= 0) return;
		var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).doubleValue();
		var distSqr = formationPos.distToCenterSqr(targetPlayer.getPosition(0));
		if (distSqr <= barrierRange * barrierRange) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onTryingToAttackBarrier(final PlayerInteractEvent.LeftClickEmpty event) {
		if (event.getSide() == LogicalSide.SERVER) return;
		Level level = event.getEntity().level();
		var chunk = level.getChunkAt(event.getPos());
		var activeFormationCores = getActiveFormationCoresNearby(chunk, level);
		for (var core : activeFormationCores) {
			if (event.getEntity() == core.getOwner()) continue;
			var badge = getItemBadge(event.getEntity(), core, null);
			if (badge != ItemStack.EMPTY) continue;
			var barrierAmount = core.getStat(FormationStat.BARRIER_AMOUNT);
			if (barrierAmount.compareTo(BigDecimal.ZERO) <= 0) continue;
			var barrierRange = core.getStat(FormationStat.BARRIER_RANGE).doubleValue();
			var distSqr = event.getEntity().position().distanceToSqr(core.getBlockPos().getCenter()) - 25;
			if (distSqr > barrierRange * barrierRange) continue;
			WuxiaPacketHandler.INSTANCE.sendToServer(new PlayerAttackBarrierMessage(core.getBlockPos(), (float) event.getEntity().getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
			core.attackBarrierMelee(event.getEntity(), (float) event.getEntity().getAttribute(Attributes.ATTACK_DAMAGE).getValue());
		}
	}

}
