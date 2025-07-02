package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MasterDiscipleEventHandler {

	@SubscribeEvent
	public static void onDiscipleCultivate(CultivatingEvent event) {
		var cultivation = Cultivation.get(event.getPlayer());
		var masterDiscipleContainer = cultivation.getMasterDiscipleContainer();
		var masterReference = masterDiscipleContainer.getMaster();
		if(masterReference == null) return;
		var server = event.getPlayer().getServer();
		var masterPlayer = server.getPlayerList().getPlayer(masterReference.getPlayerId());
		var masterCultivation = Cultivation.get(masterPlayer);
		var cultivatorPlayer = event.getPlayer();
		//64 blocks
		if(cultivatorPlayer.distanceToSqr(masterPlayer) < 4096.0) {
			//TODO logic for getting master main element and contrast against disciple to add a bonus
		}
	}

}
