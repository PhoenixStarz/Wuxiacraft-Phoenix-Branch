package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public abstract class IntrospectionTab {

	public final String name;

	/**
	 * the icon's position in the tab_icons file
	 */
	public final Point icon;

	public IntrospectionTab(String name, Point icon) {
		this.name = name;
		this.icon = icon;
	}

	public void init(IntrospectionScreen screen) {
	}

	public void close(IntrospectionScreen screen) {
		screen.clearWidgets();
	}

	public abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY);

	public abstract void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY);

}
