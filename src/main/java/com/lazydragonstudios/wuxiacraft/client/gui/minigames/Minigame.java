package com.lazydragonstudios.wuxiacraft.client.gui.minigames;

import com.lazydragonstudios.wuxiacraft.client.gui.MeditateScreen;
import net.minecraft.client.gui.GuiGraphics;

public interface Minigame {

	void init(MeditateScreen screen);

	boolean onMouseClick(double x, double y, int button);

	boolean onMouseRelease(double x, double y, int button);

	void onMouseMove(double x, double y);

	void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

	void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY);

	void tick();

	default void close(MeditateScreen screen) {
	}

}
