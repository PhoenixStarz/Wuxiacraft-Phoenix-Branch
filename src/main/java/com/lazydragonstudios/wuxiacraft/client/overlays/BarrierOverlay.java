package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.EssenceCultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.util.StatsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BarrierOverlay implements IGuiOverlay {

	public static final ResourceLocation BARRIER_BAR = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/overlay/barrier_bar.png");

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;
		if (mc.player.isCreative()) return;
		if (!gui.shouldDrawSurvivalElements()) return;
		int i = width / 2 - 91;
		int j = height - gui.leftHeight;
		ICultivation cultivation = Cultivation.get(mc.player);
		var essenceData = cultivation.getSystemData(System.ESSENCE);
		var stage = essenceData.getStage();
		if(!(stage instanceof EssenceCultivationStage essenceStage)) return;
		if(!essenceStage.isCanHaveBarrier()) return;
		//health
		guiGraphics.blit(BARRIER_BAR, i, j, 81, 9, 0, 0, 81, 9, 81, 18);
		MathContext mathContext =  new MathContext(6, RoundingMode.HALF_UP);
		var max_barrier = cultivation.getStat(PlayerStat.MAX_BARRIER);
		var barrier = cultivation.getStat(PlayerStat.BARRIER);
		if (max_barrier.compareTo(BigDecimal.ZERO) < 1) return;
		int fill = barrier.multiply(new BigDecimal("81"), mathContext).divide(max_barrier, mathContext).min(new BigDecimal(81)).intValue();
		guiGraphics.blit(BARRIER_BAR, i, j, fill, 9, 0, 9, fill, 9, 81, 18);
		//text
		String life = StatsUtil.getShortHealthAmount(barrier);
		int healthStringWidth = gui.getFont().width(life);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate((int) (i + (81f - healthStringWidth) / 2), j + 2, 1f);
		guiGraphics.pose().scale(0.77f, 0.77f, 1f);
		guiGraphics.drawString(gui.getFont(), life, 0, 0, 0xFFFFFF);
		gui.leftHeight += 11;
		guiGraphics.pose().popPose();
	}

}
