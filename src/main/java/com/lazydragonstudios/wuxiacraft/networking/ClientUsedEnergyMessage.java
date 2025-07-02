package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class ClientUsedEnergyMessage {

	private final BigDecimal energyUsed;

	private final System systemUsed;

	public ClientUsedEnergyMessage(BigDecimal energyUsed, System systemUsed) {
		this.energyUsed = energyUsed;
		this.systemUsed = systemUsed;
	}

	public static void encode(ClientUsedEnergyMessage message, FriendlyByteBuf buf) {
		CompoundTag tag = new CompoundTag();
		tag.putString("energyUsed", message.energyUsed.toPlainString());
		buf.writeEnum(message.systemUsed);
		buf.writeNbt(tag);
	}

	public static ClientUsedEnergyMessage decode(FriendlyByteBuf buf) {
		System system = buf.readEnum(System.class);
		CompoundTag tag = buf.readNbt();
		var energyUsed = new BigDecimal(tag.getString("energyUsed"));
		return new ClientUsedEnergyMessage(energyUsed, system);
	}

	public static void handleMessage(ClientUsedEnergyMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if(side.isClient()) return;
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			var cultivation = Cultivation.get(serverPlayer);
			var systemData = cultivation.getSystemData(msg.systemUsed);
			systemData.consumeEnergy(msg.energyUsed.max(BigDecimal.ZERO));
		});
	}
}
