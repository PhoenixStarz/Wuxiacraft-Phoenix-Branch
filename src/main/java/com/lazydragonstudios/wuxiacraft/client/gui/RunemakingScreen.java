package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaFlowPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTexturedButton;
import com.lazydragonstudios.wuxiacraft.container.RunemakingMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.LinkedList;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RunemakingScreen extends AbstractContainerScreen<RunemakingMenu> {

	private static final ResourceLocation RUNEMAKING_SCREEN = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/runemaking_table.png");

	public static LinkedList<WuxiaTexturedButton> runeBtn = new LinkedList<>();

	public RunemakingScreen(RunemakingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		this.width = 200;
		this.height = 172;
	}

	@Override
	protected void init() {
		super.init();
		WuxiaFlowPanel panel = new WuxiaFlowPanel(this.getGuiLeft() + 10, this.getGuiTop() + 10, 158, 73, Component.empty());
		panel.margin = 2;
		this.addRenderableWidget(panel);
		if (this.minecraft == null) return;
		if (this.minecraft.gameMode == null) return;
		for (int i = 0; i < 8; i++) {
			int finalI = i;
			WuxiaTexturedButton rune = new WuxiaTexturedButton(0, 0, 18, 18,
					Component.empty(),
					() -> this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, finalI),
					new ResourceLocation[]{RUNEMAKING_SCREEN, RUNEMAKING_SCREEN},
					new Rectangle[]{new Rectangle(200, 0, 256, 256), new Rectangle(i * 18, 172, 256, 256)}
			);
			runeBtn.add(rune);
			panel.addChild(rune);
		}
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.renderBackground(guiGraphics);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(this.leftPos, this.topPos, 0);
		guiGraphics.blit(RUNEMAKING_SCREEN, 0, 0, 0, 0, 200, 172);
		guiGraphics.pose().popPose();
	}

	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
		this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
	}
}
