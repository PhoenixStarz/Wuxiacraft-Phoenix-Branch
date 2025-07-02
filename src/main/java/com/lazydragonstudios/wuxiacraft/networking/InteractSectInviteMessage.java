package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.world.data.SectSavedData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record InteractSectInviteMessage(UUID serverId, boolean accepted) {

	public static void encode(InteractSectInviteMessage msg, FriendlyByteBuf buf) {
		buf.writeUUID(msg.serverId);
		buf.writeBoolean(msg.accepted);
	}

	public static InteractSectInviteMessage decode(FriendlyByteBuf buf) {
		return new InteractSectInviteMessage(buf.readUUID(), buf.readBoolean());
	}

	public static void handleMessage(InteractSectInviteMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		if (side.isClient()) return;
		ctx.enqueueWork(() -> {
			ServerPlayer sender = ctx.getSender();
			if (sender == null) return;
			MinecraftServer server = sender.getServer();
			if (server == null) return;
			var overWorld = server.overworld();
			var sectSavedData = SectSavedData.get(overWorld);
			var sect = sectSavedData.sects.get(msg.serverId);
			if (sect == null) return;
			if (sect.sectInvites.contains(sender.getUUID())) {
				sect.sectInvites.remove(sender.getUUID());
				if (msg.accepted()) {
					sect.getMembers().add(sender.getUUID());
					sect.getMembersName().put(sender.getUUID(), sender.getDisplayName());
				}
			}
		});
	}

}
