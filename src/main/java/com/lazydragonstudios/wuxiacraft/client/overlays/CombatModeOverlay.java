package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CombatModeOverlay implements IGuiOverlay {

	public static final ResourceLocation COMBAT_MODE = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/overlay/combat_mode.png");

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		if(!cultivation.isCombat()) return;
		guiGraphics.blit(COMBAT_MODE, screenWidth - 64, screenHeight - 16, 0, 0, 64, 15, 64, 15);
	}
}
