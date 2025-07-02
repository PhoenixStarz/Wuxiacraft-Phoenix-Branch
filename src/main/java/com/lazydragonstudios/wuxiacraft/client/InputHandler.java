package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.client.gui.MeditateScreen;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.networking.BroadcastAnimationChangeRequestMessage;
import com.lazydragonstudios.wuxiacraft.networking.CultivationStateChangeMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import com.lazydragonstudios.wuxiacraft.networking.OpenScreenMessage;

public class InputHandler {

	public static final String KEY_CATEGORY = "wuxiacraft.category.main";
	public static final KeyMapping OPEN_INTROSPECTION = new KeyMapping("wuxiacraft.key.introspection", GLFW.GLFW_KEY_K, KEY_CATEGORY);
	public static final KeyMapping KEY_MEDITATE = new KeyMapping("wuxiacraft.key.meditate", GLFW.GLFW_KEY_Z, KEY_CATEGORY);
	public static final KeyMapping KEY_EXERCISE = new KeyMapping("wuxiacraft.key.exercise", GLFW.GLFW_KEY_X, KEY_CATEGORY);
	public static final KeyMapping KEY_CAST_SKILL = new KeyMapping("wuxiacraft.key.cast_skill", GLFW.GLFW_KEY_C, KEY_CATEGORY);
	public static final KeyMapping KEY_COMBAT_MODE = new KeyMapping("wuxiacraft.key.combat_mode", GLFW.GLFW_KEY_V, KEY_CATEGORY);
	public static final KeyMapping KEY_DIVINE_SENSE = new KeyMapping("wuxiacraft.key.divine_sense", GLFW.GLFW_KEY_V, KEY_CATEGORY);
	public static final KeyMapping KEY_SKILL_WHEEL = new KeyMapping("wuxiacraft.key.skill_wheel", GLFW.GLFW_KEY_TAB, KEY_CATEGORY);

	@SubscribeEvent
	public static void onKeyPressed(InputEvent.Key event) {
		LocalPlayer player = Minecraft.getInstance().player;
		if(Minecraft.getInstance().screen != null) return;
		if (player == null) return;
		if (!(event.getAction() == GLFW.GLFW_RELEASE || event.getAction() == GLFW.GLFW_PRESS)) return;
		var animationState = new ClientAnimationState();
		var cultivation = Cultivation.get(player);
		var skillData = cultivation.getSkills();
		if (OPEN_INTROSPECTION.consumeClick()) {
			WuxiaPacketHandler.INSTANCE.sendToServer(new OpenScreenMessage(OpenScreenMessage.ScreenType.INTROSPECTION));
		}
		if (KEY_MEDITATE.consumeClick()) {
			Minecraft.getInstance().setScreen(new MeditateScreen());
			animationState.setMeditating(true);
			WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
		}
		if (event.getKey() == KEY_EXERCISE.getKey().getValue()) {
			if (event.getAction() == GLFW.GLFW_PRESS) {
				animationState.setExercising(true);
				cultivation.setExercising(true);
			} else if (event.getAction() == GLFW.GLFW_RELEASE) {
				animationState.setExercising(false);
				cultivation.setExercising(false);
			}
			WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
		}
		if (event.getKey() == KEY_CAST_SKILL.getKey().getValue()) {
			if (event.getAction() == GLFW.GLFW_PRESS) {
				skillData.casting = true;
			} else if (event.getAction() == GLFW.GLFW_RELEASE) {
				skillData.casting = false;
			}
			WuxiaPacketHandler.INSTANCE.sendToServer(new CultivationStateChangeMessage(skillData.selectedSkill, skillData.casting, cultivation.isDivineSense()));
		}
		if (event.getKey() ==KEY_COMBAT_MODE.getKey().getValue()) {
			if (event.getAction() == GLFW.GLFW_RELEASE) {
				cultivation.setCombat(!cultivation.isCombat());
			}
			WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
		}
		if (event.getKey() == KEY_DIVINE_SENSE.getKey().getValue()) {
			if (event.getAction() == GLFW.GLFW_PRESS) {
				cultivation.setDivineSense(true);
			} else if (event.getAction() == GLFW.GLFW_RELEASE) {
				cultivation.setDivineSense(false);
			}
			WuxiaPacketHandler.INSTANCE.sendToServer(new CultivationStateChangeMessage(skillData.selectedSkill, skillData.casting, cultivation.isDivineSense()));
		}
	}

}
