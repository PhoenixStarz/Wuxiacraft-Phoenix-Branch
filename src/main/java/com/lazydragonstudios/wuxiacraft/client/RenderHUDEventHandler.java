package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.networking.BroadcastAnimationChangeRequestMessage;
import com.lazydragonstudios.wuxiacraft.networking.CultivationStateChangeMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHUDEventHandler {

	@SubscribeEvent
	public static void onOpeningAnyScreen(ScreenOpenEvent event) {
		if (event.getScreen() instanceof Screen) {
			var player = Minecraft.getInstance().player;
	    	if (player == null) return;
		  	ClientAnimationState animationState = new ClientAnimationState();
			ICultivation cultivation = Cultivation.get(player);
			animationState.setExercising(false);
			cultivation.setExercising(false);
			cultivation.getSkills().casting = false;
			WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
			WuxiaPacketHandler.INSTANCE.sendToServer(new CultivationStateChangeMessage(cultivation.getSkills().selectedSkill, cultivation.getSkills().casting, cultivation.isDivineSense()));
	 	}
	}
}