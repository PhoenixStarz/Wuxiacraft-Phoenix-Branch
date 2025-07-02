package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.init.WuxiaGameRules;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SynchronizeMaxAgility(int maxAgility) {

	public static void encode(SynchronizeMaxAgility msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.maxAgility);
	}

	public static SynchronizeMaxAgility decode(FriendlyByteBuf buf) {
		return new SynchronizeMaxAgility(buf.readInt());
	}

	public static void handleMessage(SynchronizeMaxAgility msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (direction.getReceptionSide() != LogicalSide.CLIENT) return;
		ctx.enqueueWork(() -> {
			handleMessageClient(msg);
			ctx.setPacketHandled(true);
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleMessageClient(SynchronizeMaxAgility msg) {
		if (Minecraft.getInstance().player == null) return;
		var level = Minecraft.getInstance().player.level();
		var rule = (GameRules.IntegerValue)level.getGameRules().getRule(WuxiaGameRules.maxWuxiaAgility);
		rule.set(msg.maxAgility, null);
	}
}
