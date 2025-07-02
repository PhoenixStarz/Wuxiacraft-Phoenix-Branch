package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SkillBarsOverlay implements IGuiOverlay {

	protected static final ResourceLocation BARS = new ResourceLocation("minecraft:textures/gui/bars.png");

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		if (!cultivation.isCombat()) return;
		MathContext mc = new MathContext(16, RoundingMode.HALF_UP);
		var skillData = cultivation.getSkills();
		var selectedSkill = skillData.getSkillAt(skillData.selectedSkill);
		var cooldown = selectedSkill.getStatValue(SkillStat.CURRENT_MAX_COOLDOWN);
		var currentCooldown = selectedSkill.getStatValue(SkillStat.CURRENT_COOLDOWN);
		var castTime = selectedSkill.getAppliedStats(cultivation, SkillStat.CAST_TIME);
		var currentCastTime = selectedSkill.getStatValue(SkillStat.CURRENT_CASTING);
		var castBarFill = BigDecimal.ZERO;
		var coolBarFill = BigDecimal.ZERO;
		if (castTime.compareTo(BigDecimal.ZERO) > 0)
			castBarFill = currentCastTime.multiply(new BigDecimal("182")).divide(castTime, mc);
		if (cooldown.compareTo(BigDecimal.ZERO) > 0)
			coolBarFill = currentCooldown.multiply(new BigDecimal("182")).divide(cooldown, mc);
		int y = screenHeight - 32 + 3;
		var x = screenWidth / 2 - 91;
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(x, y, 0);
		if (coolBarFill.compareTo(BigDecimal.ZERO) > 0) {
			guiGraphics.blit(BARS, 0, 0, 0, 50, 182, 5);
			guiGraphics.blit(BARS, 0, 0, 0, 55, coolBarFill.intValue(), 5);
			guiGraphics.blit(BARS, 0, 0, 0, 115, 182, 5);
		} else if (skillData.casting) {
			guiGraphics.blit(BARS, 0, 0, 0, 10, 182, 5);
			guiGraphics.blit(BARS, 0, 0, 0, 15, castBarFill.intValue(), 5);
			guiGraphics.blit(BARS, 0, 0, 0, 115, 182, 5);
		}
		guiGraphics.pose().popPose();
	}
}
