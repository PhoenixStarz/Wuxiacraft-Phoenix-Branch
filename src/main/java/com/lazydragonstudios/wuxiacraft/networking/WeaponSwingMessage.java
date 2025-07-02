package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.math.BigDecimal;
import java.util.function.Supplier;

public record WeaponSwingMessage(System system, BigDecimal amount) {

	public static void encode(WeaponSwingMessage msg, FriendlyByteBuf buf) {
		buf.writeEnum(msg.system);
		buf.writeComponent(Component.literal(msg.amount.toPlainString()));
	}

	public static WeaponSwingMessage decode(FriendlyByteBuf buf) {
		return new WeaponSwingMessage(buf.readEnum(System.class), new BigDecimal(buf.readComponent().getString()));
	}

	public static void handleMessage(WeaponSwingMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer == null) return;
			var cultivation = Cultivation.get(serverPlayer);
			if (cultivation.isCombat()) return;
			cultivation.addCultivationBase(serverPlayer, msg.system, msg.amount);
		});
	}
}
