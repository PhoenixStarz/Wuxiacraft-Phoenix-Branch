package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("FieldCanBeLocal")
public class EnergiesOverlay implements IGuiOverlay {

	public static final ResourceLocation ENERGY_BAR = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/overlay/energy_bars.png");

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen instanceof ChatScreen) return;
		if (mc.player == null) return;
		if (mc.player.isCreative()) return;
		var cultivation = Cultivation.get(mc.player);

		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(width - 10 - 15 - 2 - 62, height - 75, 0);
		var position = 0;
		for (var system : System.values()) {
			var energy = cultivation.getStat(system, PlayerSystemStat.ENERGY);
			var max_energy = cultivation.getStat(system, PlayerSystemStat.MAX_ENERGY);
			if (max_energy.compareTo(BigDecimal.ZERO) < 1) return;
			var energy_ratio = energy.divide(max_energy, RoundingMode.HALF_UP);
			int barFill = energy_ratio.multiply(new BigDecimal("60")).intValue();
			guiGraphics.blit(ENERGY_BAR, 0, 3 + position * 17, 0, 0, 62, 9, 64, 64); // black bar
			guiGraphics.blit(ENERGY_BAR, 64, position * 17, position * 15, 36, 15, 15, 64, 64); // icon
			guiGraphics.blit(ENERGY_BAR, 1, 3 + position * 17, 0, 9 + position * 9, barFill, 9, 64, 64); // bar fill
			guiGraphics.drawCenteredString(gui.getFont(), Component.literal(
							energy_ratio.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_EVEN) + "%"),
					31, 3 + position * 17, 0xFFAA00);
			position++;
		}

		guiGraphics.pose().popPose();
	}
}
