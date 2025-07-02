package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaScrollPanel;
import net.minecraft.client.gui.GuiGraphics;

public abstract class SectSubTab {

	public abstract void init(WuxiaScrollPanel tabPanel);

	public abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY);
}
