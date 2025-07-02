package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.sect.Sect;
import com.lazydragonstudios.wuxiacraft.world.data.SectSavedData;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CreateSectMessage(String name) {

	public static void handleMessage(CreateSectMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var direction = ctx.getDirection();
		if (!direction.getReceptionSide().isServer()) return;
		ctx.enqueueWork(() -> {
			var sender = ctx.getSender();
			if (sender == null) return;
			var server = sender.getServer();
			if (server == null) return;
			var sectData = SectSavedData.get(server.overworld());
			var newSect = new Sect();
			newSect.setSectName(msg.name());
			newSect.setSectMaster(sender.getUUID());
			newSect.addMember(sender.getUUID());
			sectData.sects.put(newSect.getSectId(), newSect);
			var cultivation = Cultivation.get(sender);
			cultivation.setSectId(newSect.getSectId());
			CultivationEventHandler.syncClientCultivation(sender);
		});
	}
}
