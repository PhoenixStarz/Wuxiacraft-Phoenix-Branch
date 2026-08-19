package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record AttemptRebirthMessage() {

	public static void encode(AttemptRebirthMessage msg, FriendlyByteBuf buf) {
	}

	public static AttemptRebirthMessage decode(FriendlyByteBuf buf) {
		return new AttemptRebirthMessage();
	}

	public static void handleMessage(AttemptRebirthMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;

		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer == null) return;
			var cultivation = Cultivation.get(serverPlayer);
			if (cultivation.attemptRebirth())
			serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.rebirth_successful", cultivation.getRebirths()), true);
			WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new CultivationSyncMessage(cultivation));
			cultivation.calculateStats();
			CultivationEventHandler.syncClientCultivation(serverPlayer);
		});
	}
}
