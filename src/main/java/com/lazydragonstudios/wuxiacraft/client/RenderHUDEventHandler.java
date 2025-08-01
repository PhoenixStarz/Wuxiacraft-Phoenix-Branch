package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.networking.BroadcastAnimationChangeRequestMessage;
import com.lazydragonstudios.wuxiacraft.networking.CultivationStateChangeMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHUDEventHandler {

	/**
	 * Disable vanilla health rendering if enabled
	 *
	 * @param event a description of what is happening
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPreRenderHUD(RenderGuiOverlayEvent.Pre event) {
		if (Minecraft.getInstance().player == null) return;
		if (WuxiaConfigs.HEALTH_BAR_ENABLED.get() != true) return;
		if (event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onOpeningAnyScreen(ScreenEvent.Opening event) {
		if (Minecraft.getInstance().player == null) return;
		var animationState = new ClientAnimationState();
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		animationState.setExercising(false);
		cultivation.setExercising(false);
		cultivation.getSkills().casting = false;
		WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
		WuxiaPacketHandler.INSTANCE.sendToServer(new CultivationStateChangeMessage(cultivation.getSkills().selectedSkill, cultivation.getSkills().casting, cultivation.isDivineSense()));
	}
}
