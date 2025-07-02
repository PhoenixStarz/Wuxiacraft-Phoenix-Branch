package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SelectBodyPartElementMessage(ResourceLocation bodyPartLocation, ResourceLocation elementLocation) {

	public static void encode(SelectBodyPartElementMessage msg, FriendlyByteBuf buf) {
		buf.writeResourceLocation(msg.bodyPartLocation);
		buf.writeResourceLocation(msg.elementLocation);
	}

	public static SelectBodyPartElementMessage decode(FriendlyByteBuf buf) {
		return new SelectBodyPartElementMessage(buf.readResourceLocation(), buf.readResourceLocation());
	}

	public static void handleMessage(SelectBodyPartElementMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (direction.getReceptionSide() == LogicalSide.CLIENT) return;
		ctx.enqueueWork(() -> {
			var player = ctx.getSender();
			if (player == null) return;
			var cultivation = Cultivation.get(player);
			var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
			bodyData.selectElementToPart(msg.bodyPartLocation, msg.elementLocation);
		});
	}

}
