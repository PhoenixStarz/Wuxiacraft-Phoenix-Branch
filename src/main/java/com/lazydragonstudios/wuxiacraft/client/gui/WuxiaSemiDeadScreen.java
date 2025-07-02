package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.networking.AskToDieMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WuxiaSemiDeadScreen extends Screen {

	public WuxiaSemiDeadScreen() {
		super(Component.empty());
	}

	@Override
	protected void init() {
		this.addRenderableWidget(Button.builder(Component.translatable("wuxiacraft.ask_to_die"),
				(btn) -> {
					WuxiaPacketHandler.INSTANCE.sendToServer(new AskToDieMessage());
					var player = Minecraft.getInstance().player;
					if (player == null) return;
					player.setHealth(-1);
					Cultivation.get(player).setSemiDeadState(false);
				}).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
		//noinspection ConstantConditions
		if (this.font == null) return;
		guiGraphics.pose().pushPose();
		guiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
		guiGraphics.drawCenteredString(this.font, Component.translatable("wuxiacraft.semi_dead.title"), this.width / 2 / 2, 30, 16777215);
		guiGraphics.pose().popPose();
		LocalPlayer player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var timer = cultivation.getSemiDeadTimer() / 20;
		guiGraphics.drawCenteredString(this.font, Component.translatable("wuxiacraft.semi_dead.time", timer), this.width / 2, 85, 16777215);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifier) {
		return true;
	}

	@Override
	public boolean charTyped(char p_94683_, int p_94684_) {
		return true;
	}
}
