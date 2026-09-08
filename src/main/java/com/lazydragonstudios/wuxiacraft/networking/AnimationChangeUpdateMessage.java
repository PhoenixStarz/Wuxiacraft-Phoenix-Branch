package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.capabilities.IClientAnimationState;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;
import java.util.function.Supplier;
import java.math.BigDecimal;

public record AnimationChangeUpdateMessage(UUID playerId, CompoundTag animationState, double barrier, double maxBarrier, int demonicStage, boolean combat, ResourceLocation bodyTransformation) {

	public static void encode(AnimationChangeUpdateMessage msg, FriendlyByteBuf buf) {
		buf.writeUUID(msg.playerId);
		buf.writeNbt(msg.animationState);
		buf.writeDouble(msg.barrier);
		buf.writeDouble(msg.maxBarrier);
		buf.writeInt(msg.demonicStage);
		buf.writeBoolean(msg.combat);
		buf.writeResourceLocation(msg.bodyTransformation);
	}

	public static AnimationChangeUpdateMessage decode(FriendlyByteBuf buf) {
		UUID playerId = buf.readUUID();
		CompoundTag animationState = buf.readAnySizeNbt();
		return new AnimationChangeUpdateMessage(playerId, animationState, buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readBoolean(), buf.readResourceLocation());
	}

	public static void handleMessageCommon(AnimationChangeUpdateMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) {
			handleMessageClient(msg, ctx);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleMessageClient(AnimationChangeUpdateMessage msg, NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			var player = Minecraft.getInstance().player;
			if (player == null) return;
			var level = player.level();
			var target = level.getPlayerByUUID(msg.playerId);
			if (target == null) return;
			IClientAnimationState animationState = ClientAnimationState.get(target);
			animationState.deserialize(msg.animationState);
			ICultivation cultivation = Cultivation.get(target);
			cultivation.setExercising(animationState.isExercising());
			cultivation.setCombat(msg.combat);
			cultivation.setStat(PlayerStat.BARRIER, BigDecimal.valueOf(msg.barrier));
			cultivation.forceSetStat(PlayerStat.MAX_BARRIER, BigDecimal.valueOf(msg.maxBarrier));
			cultivation.setDemonicStage(msg.demonicStage);
			if(ForgeRegistries.ENTITY_TYPES.containsKey(msg.bodyTransformation)) {
				cultivation.setBodyTransformation(msg.bodyTransformation);
			} else {
				cultivation.setBodyTransformation(null);
			}
		});
	}
}
