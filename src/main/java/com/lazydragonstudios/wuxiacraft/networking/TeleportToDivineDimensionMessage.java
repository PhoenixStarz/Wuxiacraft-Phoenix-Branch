package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.DivineCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.world.dimension.DimensionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Set;
import java.util.function.Supplier;

public record TeleportToDivineDimensionMessage(Boolean otherEntities) {

	public static void encode(TeleportToDivineDimensionMessage msg, FriendlyByteBuf buf) {
		buf.writeBoolean(msg.otherEntities);
	}

	public static TeleportToDivineDimensionMessage decode(FriendlyByteBuf buf) {
		return new TeleportToDivineDimensionMessage(buf.readBoolean());
	}

	public static void handleMessage(TeleportToDivineDimensionMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;

		ctx.enqueueWork(() -> {
			var serverPlayer = ctx.getSender();
			if (serverPlayer == null) return;
			ICultivation cultivation = Cultivation.get(serverPlayer);
			DivineCultivationContainer divineData = (DivineCultivationContainer) cultivation.getSystemData(System.DIVINE);
			DimensionManager.INSTANCE.maybeGenerateDivinePlatform(serverPlayer);
			BlockPos dmPos = DimensionManager.INSTANCE.structurePosForPlayer(serverPlayer);
			ServerLevel dimLevel = serverPlayer.getServer().getLevel(DimensionManager.DIVINE_DIMENSION);
       		if (serverPlayer.level().dimension().equals(DimensionManager.DIVINE_DIMENSION)) {
				if (divineData.getStoredLocation() != null) {
					dmPos = divineData.getStoredLocation().getValue();
					dimLevel = serverPlayer.getServer().getLevel(divineData.getStoredLocation().getKey());
				} else {
					dmPos = new BlockPos(0,64,0);
					dimLevel = serverPlayer.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation("minecraft:overworld")));
				}
			}
			serverPlayer.stopRiding();
			double x = dmPos.getX() + 0.5;
			double y = dmPos.getY() + 1.0;
			double z = dmPos.getZ() + 0.5;
			if (msg.otherEntities) {
				var aoeEntities = serverPlayer.level().getEntities(serverPlayer, serverPlayer.getBoundingBox().inflate(4));			
				for (var entity : aoeEntities) {
					if (!(entity instanceof ServerPlayer targetPlayer)) continue;
					var targetCultivation = Cultivation.get(targetPlayer);
					var targetDivineData = (DivineCultivationContainer) cultivation.getSystemData(System.DIVINE);
					if (!targetPlayer.level().dimension().equals(DimensionManager.DIVINE_DIMENSION)) 
					targetDivineData.setStoredLocation(targetPlayer.level().dimension(), targetPlayer.blockPosition());
					targetPlayer.changeDimension(dimLevel, new ITeleporter() {
						public void teleport(ServerLevel level, ServerPlayer player) {
							player.teleportTo(level, x, y, z, 1f, 1f);
						}
					});	
					targetPlayer.teleportTo(x, y, z);
					CultivationEventHandler.syncClientCultivation(targetPlayer);
				}
			}
			if (!serverPlayer.level().dimension().equals(DimensionManager.DIVINE_DIMENSION)) 
			divineData.setStoredLocation(serverPlayer.level().dimension(), serverPlayer.blockPosition());
			serverPlayer.changeDimension(dimLevel, new ITeleporter() {
				public void teleport(ServerLevel level, ServerPlayer player) {
					player.teleportTo(level, x, y, z, 1f, 1f);
				}
       		});	
			serverPlayer.teleportTo(x, y, z);
			CultivationEventHandler.syncClientCultivation(serverPlayer);
		});
	}
}