package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.client.gui.tab.SectLessTab;
import com.lazydragonstudios.wuxiacraft.sect.Sect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Supplier;

public record SendSectInvitesToClientMessage(HashMap<UUID, Sect> invitees) {

	public static void encode(SendSectInvitesToClientMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.invitees.size());
		for (var sect : msg.invitees.entrySet()) {
			buf.writeUUID(sect.getKey());
			buf.writeNbt(sect.getValue().saveData());
		}
	}

	public static SendSectInvitesToClientMessage decode(FriendlyByteBuf buf) {
		HashMap<UUID, Sect> invitees = new HashMap<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			var uuid = buf.readUUID();
			var sectTag = buf.readNbt();
			if (sectTag == null) sectTag = new CompoundTag();
			var sect = new Sect();
			sect.lodData(sectTag);
			invitees.put(uuid, sect);
		}
		return new SendSectInvitesToClientMessage(invitees);
	}

	public static void handleMessage(SendSectInvitesToClientMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (direction.getReceptionSide() != LogicalSide.CLIENT) return;
		ctx.enqueueWork(() -> {
			SectLessTab.SECT_INVITES.clear();
			SectLessTab.SECT_INVITES.putAll(msg.invitees);
			SectLessTab.updateSectInvites = true;
		});
	}
}
