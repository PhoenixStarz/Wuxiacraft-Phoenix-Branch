package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaScrollPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTechniqueComposeGrid;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.TechniqueGrid;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ManualScreen extends Screen {

	private WuxiaScrollPanel panel;

	private WuxiaTechniqueComposeGrid gridComposer;

	private TechniqueGrid grid;

	private int guiTop = 0;

	private int guiLeft = 0;

	private static final ResourceLocation BOOK_GUI = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/book_gui.png");

	private int radius;

	private Component author;

	public ManualScreen(TechniqueGrid grid, int radius, @Nullable Component author) {
		super(Component.empty());
		this.grid = grid;
		this.radius = radius;
		this.author = author;
	}

	@Override
	protected void init() {
		var scaledResX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		var scaledResY = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		this.guiTop = (scaledResY - 200) / 2;
		this.guiLeft = (scaledResX - 200) / 2;
		if (!(this.guiTop >= 0 && this.guiLeft >= 0)) return;
		this.panel = new WuxiaScrollPanel(this.guiLeft, this.guiTop, 210, 210, Component.literal(""));
		this.gridComposer = new WuxiaTechniqueComposeGrid(0, 0, this.grid);
		this.gridComposer.setGridRadius(this.radius);
		this.panel.addChild(this.gridComposer);
		this.addRenderableWidget(this.panel);
		if (this.author != null) {
			this.addRenderableWidget(new WuxiaLabel(5, 195, Component.translatable("wuxiacraft.gui.author", this.author), 0xFFAA00));
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.pose().translate(this.guiLeft, this.guiTop, 0);
		guiGraphics.blit(BOOK_GUI, 0, 0, 0, 0, 200, 200);
		guiGraphics.pose().popPose();
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
