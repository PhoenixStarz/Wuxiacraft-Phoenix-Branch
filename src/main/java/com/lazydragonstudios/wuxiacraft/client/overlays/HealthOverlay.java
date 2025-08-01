package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.util.StatsUtil;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class HealthOverlay implements IGuiOverlay {

	public static final ResourceLocation HEALTH_BAR = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/overlay/health_bar.png");

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;
		if (mc.player.isCreative()) return;
		if (WuxiaConfigs.HEALTH_BAR_ENABLED.get() != true) return;
		if (!gui.shouldDrawSurvivalElements()) return;
		int i = width / 2 - 91;
		int j = height - gui.leftHeight;
		//health
		guiGraphics.blit(HEALTH_BAR, i, j, 81, 9, 0, 0, 81, 9, 81, 18);
		MathContext mathContext =  new MathContext(6, RoundingMode.HALF_UP);
		var hp = new BigDecimal(Math.ceil(mc.player.getHealth() * 10) / 10);
        var max_hp = new BigDecimal(mc.player.getMaxHealth());
		int fill = hp.multiply(new BigDecimal("81"), mathContext).divide(max_hp, mathContext).min(new BigDecimal(81)).intValue();
		guiGraphics.blit(HEALTH_BAR, i, j, fill, 9, 0, 9, fill, 9, 81, 18);
		//text
		String life = StatsUtil.getShortHealthAmount(hp);// + "/" + getShortHealthAmount((int) max_hp);
		int healthStringWidth = gui.getFont().width(life);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate((int) (i + (81f - healthStringWidth) / 2), j + 2, 1f);
		guiGraphics.pose().scale(0.77f, 0.77f, 1f);
		guiGraphics.drawString(gui.getFont(), life, 0, 0, 0xFFFFFF);
		gui.leftHeight += 11;
		guiGraphics.pose().popPose();
	}

}