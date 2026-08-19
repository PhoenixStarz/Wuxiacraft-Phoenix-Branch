package com.lazydragonstudios.wuxiacraft.sect;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.world.data.WuxiaSavedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SectEventHandler {

	@SubscribeEvent
	public void onWorldTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		var level = event.getServer().overworld();
	/*	var sectData = WuxiaSavedData.get(level);
		HashSet<UUID> toRemove =new HashSet<>();
		sectData.sects.forEach((id, sect) -> {
			sect.onServerTick(event.getServer());
			if(sect.isMarkedForRemoval()) toRemove.add(id);
		});
		toRemove.forEach(sectData.sects::remove); */
	}

	@SubscribeEvent
	public void onCheckSectMember(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (event.side != LogicalSide.SERVER) return;
		var level = event.player.getServer().overworld();
	//	var sectData = WuxiaSavedData.get(level);
		var cultivation = Cultivation.get(event.player);
		var sectId = cultivation.getSectId();
		if (sectId == null) return;
	/*	Sect sect = sectData.sects.get(sectId);
		if (sect == null || !sect.getMembers().contains(event.player.getUUID())) {
			cultivation.setSectId(null);
		} */
		sectId = cultivation.getSectId();
		if (sectId != null) return;
		CultivationEventHandler.syncClientCultivation((ServerPlayer) event.player);
	}

}
