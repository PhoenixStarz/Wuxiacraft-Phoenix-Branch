package com.lazydragonstudios.wuxiacraft.formation;

import com.lazydragonstudios.wuxiacraft.blocks.StatRuneBlock;
import com.lazydragonstudios.wuxiacraft.blocks.entity.FormationCore;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FormationGameEventListener implements GameEventListener {

	private BlockPos position;
	private int radius;

	private PositionSource listenerSource;

	public FormationGameEventListener(BlockPos position, int radius) {
		this.position = position;
		this.radius = radius;
		this.listenerSource = new BlockPositionSource(position);
	}

	@Override
	public PositionSource getListenerSource() {
		return this.listenerSource;
	}

	@Override
	public int getListenerRadius() {
		return this.radius + 1;
	}

	@Override
	public boolean handleGameEvent(ServerLevel level, GameEvent gameEvent, GameEvent.Context context, Vec3 pos) {
		if (!gameEvent.equals(GameEvent.BLOCK_DESTROY)) return false;
		var blockState = context.affectedState();
		var block = blockState.getBlock();
		if (!(block instanceof StatRuneBlock)) return false;
		var blockEntity = level.getBlockEntity(this.position);
		if (!(blockEntity instanceof FormationCore formationCore)) return false;
		var blockPos = new BlockPos((int) (pos.x < 0 ? pos.x - 1 : pos.x), (int) pos.y, (int) (pos.z < 0 ? pos.z - 1 : pos.z));
		if (formationCore.containsRune(blockPos)) formationCore.deactivate();
		return true;
	}
}
