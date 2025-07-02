package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

public class ElementSystemConverter extends ElementalConverter {

	/**
	 * the system to be converted to
	 */
	public System system;

	@SuppressWarnings("rawtypes")
	private final static HashMap<Class, Integer> priority = new HashMap<>();

	static {
		priority.put(ElementSystemConverter.class, -2);
		priority.put(SystemGather.class, -1);
	}

	public ElementSystemConverter(double amount, ResourceLocation element, System system) {
		super(amount, element);
		this.system = system;
	}

	@Override
	public int canConnectFromCount() {
		return -1;
	}

	@Override
	public void convert(double converted, HashMap<String, Object> metaData, BigDecimal proficiency) {
		String systemRawBase = system.name().toLowerCase() + "-raw-cultivation-base";
		String elementBonus = "element-" + this.element.getPath();
		metaData.put(systemRawBase, (double) metaData.getOrDefault(systemRawBase, 0d) + converted);
		metaData.put(elementBonus, (double) metaData.getOrDefault(elementBonus, 0d) + converted);
	}

	@Override
	public boolean canConnect(TechniqueAspect aspect) {
		if (aspect instanceof ElementSystemConverter con) {
			return con.element.equals(this.element);
		}
		if (aspect instanceof SystemGather) {
			return true;
		}
		return super.canConnect(aspect);
	}

	@Override
	public boolean shouldConnect(TechniqueAspect candidateTechAspect, LinkedList<TechniqueAspect> connectedTo) {
		if(candidateTechAspect instanceof SystemGather) {
			return connectedTo.stream().filter(aspect -> aspect instanceof ElementSystemConverter || aspect instanceof SystemGather).toList().isEmpty();
		}
		return super.shouldConnect(candidateTechAspect, connectedTo);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var nameLocation = WuxiaRegistries.TECHNIQUE_ASPECT.get().getKey(this);
		if (nameLocation == null) return;
		var name = Component.translatable("wuxiacraft.aspect." + nameLocation.getPath() + ".name");
		var convertCap = this.amount;
		var player = Minecraft.getInstance().player;
		if (player != null) {
			var cultivation = Cultivation.get(player);
			var proficiency = cultivation.getAspects().getAspectProficiency(nameLocation);
			var modifier = this.getCurrentCheckpoint(proficiency).modifier();
			convertCap *= 1d + modifier.doubleValue();
		}
		var amount = String.format(" ->     (%.1f)", convertCap);

		var font = Minecraft.getInstance().font;
		var nameWidth = font.width(name);
		var amountWidth = font.width(amount);
		var tooTipWidth = Math.max(nameWidth + 10, 28 + amountWidth);
		RenderSystem.enableBlend();
		guiGraphics.fill(mouseX, mouseY, mouseX + tooTipWidth, mouseY + 31, 0x8A8080A0);
		RenderSystem.enableBlend();
		guiGraphics.blit(new ResourceLocation(this.element.getNamespace(), "textures/elements/" + this.element.getPath() + ".png"), mouseX + 5, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(new ResourceLocation(this.element.getNamespace(), "textures/systems/" + this.system.name().toLowerCase() + ".png"), mouseX + 38, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		guiGraphics.drawString(Minecraft.getInstance().font, name, mouseX + 5, mouseY + 2, 0xFFFFFF);
		guiGraphics.drawString(Minecraft.getInstance().font, amount, mouseX + 23, mouseY + 18, 0xFFFFFF);
		RenderSystem.disableBlend();
	}

	@Nonnull
	@Override
	public LinkedList<AbstractWidget> getStatsSheetDescriptor(BigDecimal proficiency) {
		var widgets = super.getStatsSheetDescriptor(proficiency);
		var checkpoint = this.getCurrentCheckpoint(proficiency);
		var amount = this.amount;
		amount *= 1 + checkpoint.modifier().doubleValue();
		widgets.add(4, new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui.generates", amount), 0xFFAA00));
		return widgets;
	}

	@Override
	public int connectPrioritySorter(TechniqueAspect aspect1, TechniqueAspect aspect2) {
		int priority1 = priority.getOrDefault(aspect1.getClass(), 0);
		int priority2 = priority.getOrDefault(aspect2.getClass(), 0);
		int finalPriority = priority1 - priority2;
		return finalPriority != 0 ? finalPriority / Math.abs(finalPriority) : 0;
	}

	@Override
	public boolean canShowForSystem(System system) {
		return system == this.system;
	}
}
