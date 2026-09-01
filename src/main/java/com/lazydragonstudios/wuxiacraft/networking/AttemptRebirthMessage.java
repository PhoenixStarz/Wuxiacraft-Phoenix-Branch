package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

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
			int i = 0;
			var aspectData = cultivation.getAspects();
			List<TechniqueAspect> neededAspects = new LinkedList<TechniqueAspect>(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValues().stream().toList());
			neededAspects.remove(WuxiaTechniqueAspects.UNKNOWN.get());
			neededAspects.remove(WuxiaTechniqueAspects.EMPTY.get());
			neededAspects.remove(WuxiaTechniqueAspects.DEVOURING.get());
			neededAspects.remove(WuxiaTechniqueAspects.CONSUMPTION.get());
			neededAspects.remove(WuxiaTechniqueAspects.GLUTTONY.get());
			neededAspects.remove(WuxiaTechniqueAspects.BEELZEBUB.get());
			neededAspects.remove(WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.get());
			if(cultivation.getRebirths() < 1) {
				neededAspects.remove(WuxiaTechniqueAspects.ASHES_OF_REBIRTH.get());
				neededAspects.remove(WuxiaTechniqueAspects.REKINDLED_SPARK.get());
				neededAspects.remove(WuxiaTechniqueAspects.IGNITION.get());
				neededAspects.remove(WuxiaTechniqueAspects.EMBER_OF_REKINDLING.get());
				neededAspects.remove(WuxiaTechniqueAspects.CINDER_OF_RENEWAL.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPIRITUAL_RECONSTRUCTION.get());
			}
			if(cultivation.getRebirths() < 2) {
				neededAspects.remove(WuxiaTechniqueAspects.INFERNO.get());
				neededAspects.remove(WuxiaTechniqueAspects.RENEWAL.get());
				neededAspects.remove(WuxiaTechniqueAspects.RESURRECTION.get());
				neededAspects.remove(WuxiaTechniqueAspects.FLAME_OF_PURIFICATION.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPARK_OF_AWAKENING.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPIRIT_OF_RESTORATION.get());
			}
			if(cultivation.getRebirths() < 3) {
				neededAspects.remove(WuxiaTechniqueAspects.ASCENT.get());
				neededAspects.remove(WuxiaTechniqueAspects.TRANSCENDENCE.get());
				neededAspects.remove(WuxiaTechniqueAspects.THE_ETERNAL_CYCLE.get());
				neededAspects.remove(WuxiaTechniqueAspects.ESSENCE_OF_REBIRTH.get());
			}
			for (var knownAspectLocation : cultivation.getAspects().getKnownAspects().stream().toList()) {
				var knownAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(knownAspectLocation);
				if (!neededAspects.contains(knownAspect)) continue;
				neededAspects.remove(knownAspect);
				var currentCheckpoint = knownAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(knownAspectLocation));
				if (currentCheckpoint != knownAspect.checkpoints.getLast()) {
					serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.aspect." + knownAspectLocation.getPath() + ".name")
							.append(Component.translatable("wuxiacraft.missing_proficiency_for_rebirth")),
						true);
					return;
				}
			}
			if(neededAspects.isEmpty()) {
				if (cultivation.attemptRebirth())
				serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.rebirth_successful", cultivation.getRebirths()), true);
				WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new CultivationSyncMessage(cultivation));
			} else {
				neededAspects.remove(cultivation.getAspects().getKnownAspects().stream().toList());
				serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.missing_aspect_for_rebirth")
						.append(Component.translatable("wuxiacraft.aspect." + WuxiaRegistries.TECHNIQUE_ASPECT.get().getKey(neededAspects.get(0)).getPath() + ".name")),
					true);
			}
		});
	}
}
