package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkillAspectType {

	public final Creator creator;

	public static SkillAspectType build(Creator creator) {
		return new SkillAspectType(creator);
	}

	public SkillAspectType(Creator creator) {
		this.creator = creator;
	}

	@OnlyIn(Dist.CLIENT)
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var registryName = WuxiaRegistries.SKILL_ASPECT.get().getKey(this);
		if (registryName == null) return;
		var font = Minecraft.getInstance().font;
		var title = Component.translatable(registryName.getNamespace() + ".skill." + registryName.getPath());
		int titleWidth = font.width(title);
		RenderSystem.enableBlend();
		guiGraphics.fill(mouseX, mouseY, mouseX + titleWidth + 10, mouseY + 13, 0x6A8080A0);
		guiGraphics.drawString(font, title, mouseX + 5, mouseY + 2, 0xFFFFFF);
		RenderSystem.disableBlend();
	}

	@FunctionalInterface
	public interface Creator {
		SkillAspect create(ICultivation cultivation);
	}

}
