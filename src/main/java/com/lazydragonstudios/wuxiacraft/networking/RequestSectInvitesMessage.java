package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.sect.Sect;
import com.lazydragonstudios.wuxiacraft.world.data.SectSavedData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Supplier;

public record RequestSectInvitesMessage() {

	public static void encode(RequestSectInvitesMessage msg, FriendlyByteBuf buf) {

	}

	public static RequestSectInvitesMessage decode(FriendlyByteBuf buf) {
		return new RequestSectInvitesMessage();
	}

	public static void handleMessage(RequestSectInvitesMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (direction.getReceptionSide() != LogicalSide.SERVER) return;
		ctx.enqueueWork(() -> {
			ServerPlayer sender = ctx.getSender();
			if (sender == null) return;
			MinecraftServer server = sender.getServer();
			if (server == null) return;
			var overWorld = server.overworld();
			var sectSavedData = SectSavedData.get(overWorld);
			var senderId = sender.getUUID();
			HashMap<UUID, Sect> invitees = new HashMap<>();
			for (var sect : sectSavedData.sects.values()) {
				if (sect.sectInvites.contains(senderId)) {
					var sectData = new Sect();
					sectData.lodData(sect.sharePublicInformation());
					invitees.put(sect.getSectId(), sectData);
				}
			}
			if (!invitees.isEmpty()) {
				WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendSectInvitesToClientMessage(invitees));
			}
		});
	}

}
