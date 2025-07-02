package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.math.BigDecimal;
import java.util.HashMap;

public class ElementToElementConverter extends ElementalConverter {

	public final double conversionRate;

	public final ResourceLocation destinationElement;

	public ElementToElementConverter(double amount, double conversionRate, ResourceLocation elementSource, ResourceLocation elementDestination) {
		super(amount, elementSource);
		this.conversionRate = conversionRate;
		this.destinationElement = elementDestination;
	}

	@Override
	public void convert(double converted, HashMap<String, Object> metaData, BigDecimal proficiency) {
		String elementBase = "element-base-" + this.element.getPath();
		double finalValue = converted * this.conversionRate;
		double initialValue = (double) metaData.getOrDefault(elementBase, 0d);
		var modifier = this.getCurrentCheckpoint(proficiency).modifier().doubleValue();
		finalValue = finalValue * (1 + modifier);
		finalValue += initialValue;
		metaData.put(elementBase, finalValue);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var nameLocation = WuxiaRegistries.TECHNIQUE_ASPECT.get().getKey(this);
		if (nameLocation == null) return;
		var name = Component.translatable("wuxiacraft.aspect." + nameLocation.getPath() + ".name");
		var amount = String.format("%.1f", this.amount);
		var rate = String.format("x%.1f", this.conversionRate);
		var arrow = "->";

		var font = Minecraft.getInstance().font;
		var nameWidth = font.width(name);
		var amountWidth = font.width(amount);
		var rateWidth = font.width(rate);
		var arrowWidth = font.width(arrow);
		var tooTipWidth = Math.max(nameWidth + 10, 5 + amountWidth + 2 + 16 + 2 + arrowWidth + 2 + 16 + 2 + rateWidth + 5);
		RenderSystem.enableBlend();
		guiGraphics.fill(mouseX, mouseY, mouseX + tooTipWidth, mouseY + 31, 0x6A8080A0);
		RenderSystem.enableBlend();
		guiGraphics.blit(new ResourceLocation(this.element.getNamespace(), "textures/elements/" + this.element.getPath() + ".png"), mouseX + 5 + amountWidth + 2, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		RenderSystem.enableBlend();
		guiGraphics.blit(new ResourceLocation(this.destinationElement.getNamespace(), "textures/elements/" + this.destinationElement.getPath() + ".png"), mouseX + 5 + amountWidth + 2 + 16 + 2 + arrowWidth + 2, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		guiGraphics.drawString(Minecraft.getInstance().font, name, mouseX + 5, mouseY + 2, 0xFFFFFF);
		guiGraphics.drawString(Minecraft.getInstance().font, amount, mouseX + 5, mouseY + 18, 0xFFFFFF);
		guiGraphics.drawString(Minecraft.getInstance().font, arrow, mouseX + 5 + amountWidth + 2 + 16 + 2, mouseY + 18, 0xFFFFFF);
		guiGraphics.drawString(Minecraft.getInstance().font, rate, mouseX + 5 + amountWidth + 2 + 16 + 2 + arrowWidth + 2 + 16 + 2, mouseY + 18, 0xFFFFFF);
		RenderSystem.disableBlend();
	}
}
