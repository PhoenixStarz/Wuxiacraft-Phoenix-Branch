package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.Tribulation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record StartTribulationMessage(int numberOfLightningStrikes, int lightningStrength, float lightningStrengthGrowth, System system) {

	public static void encode(StartTribulationMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.numberOfLightningStrikes);
		buf.writeInt(msg.lightningStrength);
		buf.writeFloat(msg.lightningStrengthGrowth);
		buf.writeEnum(msg.system);
	}

	public static StartTribulationMessage decode(FriendlyByteBuf buf) {
		return new StartTribulationMessage(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readEnum(System.class));
	}

	public static void handleMessage(StartTribulationMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;

		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer == null) return;
			var cultivation = Cultivation.get(serverPlayer);
			cultivation.setTribulation(new Tribulation(msg.numberOfLightningStrikes, msg.lightningStrength, msg.lightningStrengthGrowth, msg.system));
			cultivation.getTribulation().reset();
			cultivation.setTribulating(true);
			if(msg.numberOfLightningStrikes > 0)
			serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.tribulation_start"), true);
			WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new CultivationSyncMessage(cultivation));
		});
	}
}
