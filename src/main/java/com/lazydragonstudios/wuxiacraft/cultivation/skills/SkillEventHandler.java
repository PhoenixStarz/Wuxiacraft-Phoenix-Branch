package com.lazydragonstudios.wuxiacraft.cultivation.skills;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.HashSet;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillEventHandler {

	public static HashMap<ServerLevel, HashMap<BlockPos, BlockDestroyProgress>> skillBlockDestroyProgress = new HashMap<>();

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (event.side != LogicalSide.SERVER) return;
		for (var level : skillBlockDestroyProgress.keySet()) {
			var toRemove = new HashSet<BlockPos>();
			for (var blockPos : skillBlockDestroyProgress.get(level).keySet()) {
				var destroyProgress = skillBlockDestroyProgress.get(level).get(blockPos);
				if (event.getServer().getTickCount() - destroyProgress.lastUpdatedTick >= 20) {
					destroyProgress.destroyProgress = Math.max(destroyProgress.destroyProgress - 0.08f, -1f);
					destroyProgress.lastUpdatedTick = event.getServer().getTickCount();
				}
				level.destroyBlockProgress(-1, blockPos, Math.max( (int) (destroyProgress.destroyProgress * 10f), 0));
				if (destroyProgress.destroyProgress < 0f) {
					toRemove.add(blockPos);
				}
				else if(destroyProgress.destroyProgress > 1f) {
					level.destroyBlock(blockPos, true, level.getEntity(destroyProgress.breakerId));
					toRemove.add(blockPos);
				}
			}
			toRemove.forEach(pos -> skillBlockDestroyProgress.get(level).remove(pos));
		}
	}

	public static class BlockDestroyProgress {

		public float destroyProgress = 0;

		public int breakerId = 0;

		public long lastUpdatedTick = 0;

		public long ticksStarted = 0;

		public BlockDestroyProgress(float destroyProgress, int breakerId, long ticksStarted) {
			this.destroyProgress = destroyProgress;
			this.breakerId = breakerId;
			this.ticksStarted = ticksStarted;
			this.lastUpdatedTick = ticksStarted;
		}
	}

}
