package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientSideEventHandler {

	@SubscribeEvent
	public static void onPlayerWalk(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		if (event.side != LogicalSide.CLIENT) return;
		if (!(event.player instanceof LocalPlayer player)) return;
		var cultivation = Cultivation.get(player);
		if (cultivation.isExercising()) {
			player.input.up = false;
			player.input.down = false;
			player.input.left = false;
			player.input.right = false;
			player.input.jumping = false;
		}
	}
}
