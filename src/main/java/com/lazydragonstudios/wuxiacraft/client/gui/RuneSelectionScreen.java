package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaFlowPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTexturedButton;
import com.lazydragonstudios.wuxiacraft.item.RuneStencil;
import com.lazydragonstudios.wuxiacraft.networking.RuneSelectionMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

import java.awt.*;
import java.util.LinkedList;

public class RuneSelectionScreen extends Screen {

	public static final ResourceLocation RUNE_SELECTION = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/rune_selection.png");

	private int guiWidth = 178;

	private int guiHeight = 93;

	public RuneSelectionScreen(Component pTitle) {
		super(pTitle);
	}

	public static LinkedList<WuxiaTexturedButton> runeBtn = new LinkedList<>();

	@Override
	protected void init() {
		super.init();
		if (this.minecraft == null) return;
		if (this.minecraft.gameMode == null) return;
		WuxiaFlowPanel panel = new WuxiaFlowPanel((this.width - this.guiWidth) / 2 + 10, (this.height - this.guiHeight) / 2 + 10, 158, 73, Component.empty());
		panel.margin = 2;
		this.addRenderableWidget(panel);
		for (int i = 0; i < 8; i++) {
			int finalI = i;
			WuxiaTexturedButton rune = new WuxiaTexturedButton(0, 0, 18, 18,
					Component.empty(),
					() -> this.selectRune(finalI),
					new ResourceLocation[]{RUNE_SELECTION, RUNE_SELECTION},
					new Rectangle[]{new Rectangle(178, 0, 256, 256), new Rectangle(i * 18, 93, 256, 256)}
			);
			runeBtn.add(rune);
			panel.addChild(rune);
		}
	}

	private void selectRune(int rune) {
		WuxiaPacketHandler.INSTANCE.sendToServer(new RuneSelectionMessage(rune));
		var player = Minecraft.getInstance().player;
		var itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (!(itemStack.getItem() instanceof RuneStencil)) return;
		var tag = itemStack.getTag();
		if (tag == null) tag = new CompoundTag();
		tag.putInt("runeSelected", rune);
		itemStack.setTag(tag);
		this.onClose();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(guiGraphics);
		guiGraphics.blit(RUNE_SELECTION, (this.width - this.guiWidth) / 2, (this.height - this.guiHeight) / 2, 0, 0, this.guiWidth, this.guiHeight);
		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
	}
}
