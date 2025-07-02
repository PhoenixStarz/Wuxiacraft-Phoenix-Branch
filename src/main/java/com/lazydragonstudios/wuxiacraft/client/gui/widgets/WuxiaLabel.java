package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WuxiaLabel extends AbstractWidget {

	private final int color;

	public WuxiaLabel(int x, int y, Component message, int color) {
		super(x, y, 0, 0, message);
		this.color = color;
		this.setMessage(message);
	}

	@Override
	public void setMessage(Component message) {
		super.setMessage(message);
		int width = Minecraft.getInstance().font.width(message.getString());
		int height = Minecraft.getInstance().font.lineHeight;
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return false;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		guiGraphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX(), this.getY(), this.color);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

	}
}
