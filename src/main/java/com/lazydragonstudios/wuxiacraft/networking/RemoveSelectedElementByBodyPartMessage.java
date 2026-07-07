package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record RemoveSelectedElementByBodyPartMessage(ResourceLocation bodyPartLocation) {

	public static void encode(RemoveSelectedElementByBodyPartMessage msg, FriendlyByteBuf buf) {
		buf.writeResourceLocation(msg.bodyPartLocation);
	}

	public static RemoveSelectedElementByBodyPartMessage decode(FriendlyByteBuf buf) {
		return new RemoveSelectedElementByBodyPartMessage(buf.readResourceLocation());
	}

	public static void handleMessage(RemoveSelectedElementByBodyPartMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (direction.getReceptionSide() == LogicalSide.CLIENT) return;
		ctx.enqueueWork(() -> {
			var player = ctx.getSender();
			if (player == null) return;
			var cultivation = Cultivation.get(player);
			var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
			bodyData.removeSelectedElementByBodyPart(msg.bodyPartLocation);
		});
	}

}
