package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.blocks.entity.FormationCore;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlayerAttackBarrierMessage(BlockPos corePos, float attackStrengthMultiplier) {

	public static void encode(PlayerAttackBarrierMessage message, FriendlyByteBuf buf) {
		buf.writeBlockPos(message.corePos);
		buf.writeFloat(message.attackStrengthMultiplier);
	}

	public static PlayerAttackBarrierMessage decode(FriendlyByteBuf buf) {
		return new PlayerAttackBarrierMessage(buf.readBlockPos(), buf.readFloat());
	}

	public static void handleMessage(PlayerAttackBarrierMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;
		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer == null) return;
			var level = serverPlayer.level();
			var blockEntity = level.getBlockEntity(msg.corePos);
			if (!(blockEntity instanceof FormationCore formationCore)) return;
			formationCore.attackBarrierMelee(serverPlayer, msg.attackStrengthMultiplier);
		});
	}
}
