package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
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

public class ElementalGenerator extends TechniqueAspect {

	public double generated;

	public ResourceLocation element;

	@SuppressWarnings("rawtypes")
	private final static HashMap<Class, Integer> priority = new HashMap<>();

	static {
		priority.put(ElementalGenerator.class, -5);
		priority.put(ConditionalElementalGenerator.class, -4);
		priority.put(WeaponElementalGenerator.class, -3);
		priority.put(ElementalConverter.class, -2);
		priority.put(ElementalConsumer.class, -1);
	}

	public ElementalGenerator(double generated, ResourceLocation element) {
		super();
		this.generated = generated;
		this.element = element;
	}

	@Override
	public void accept(HashMap<String, Object> metaData, BigDecimal proficiency) {
		super.accept(metaData, proficiency);
		String elementBase = "element-base-" + element.getPath();
		var modifier = this.getCurrentCheckpoint(proficiency).modifier();
		var generated = this.generated * (1 + modifier.doubleValue());
		metaData.put(elementBase, (double) metaData.getOrDefault(elementBase, 0d) + generated);
	}

	@Override
	public boolean canConnect(TechniqueAspect aspect) {
		if (aspect instanceof ElementalGenerator gen) {
			return gen.element.equals(this.element);
		}
		if (aspect instanceof ElementalConsumer con) {
			return con.element.equals(this.element);
		}
		if (aspect instanceof ElementalConverter con) {
			return con.element.equals(this.element);
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var nameLocation = WuxiaRegistries.TECHNIQUE_ASPECT.get().getKey(this);
		if (nameLocation == null) return;
		var name = Component.translatable("wuxiacraft.aspect." + nameLocation.getPath() + ".name");
		var generated = this.generated;
		var player = Minecraft.getInstance().player;
		if (player != null) {
			var cultivation = Cultivation.get(player);
			var proficiency = cultivation.getAspects().getAspectProficiency(nameLocation);
			var modifier = this.getCurrentCheckpoint(proficiency).modifier();
			generated *= 1d + modifier.doubleValue();
		}
		var amount = String.format("%.1f", generated);

		var font = Minecraft.getInstance().font;
		var nameWidth = font.width(name);
		var amountWidth = font.width(amount);
		var tooTipWidth = Math.max(nameWidth + 10, 28 + amountWidth);
		RenderSystem.enableBlend();
		guiGraphics.fill(mouseX, mouseY, mouseX + tooTipWidth, mouseY + 31, 0x6A8080A0);
		RenderSystem.enableBlend();
		guiGraphics.blit(new ResourceLocation(this.element.getNamespace(), "textures/elements/" + this.element.getPath() + ".png"), mouseX + 5, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		guiGraphics.drawString(Minecraft.getInstance().font, name, mouseX + 5, mouseY + 2, 0xFFFFFF);
		guiGraphics.drawString(Minecraft.getInstance().font, amount, mouseX + 23, mouseY + 18, 0xFFFFFF);
		RenderSystem.disableBlend();
	}

	@Nonnull
	@Override
	public LinkedList<AbstractWidget> getStatsSheetDescriptor(BigDecimal proficiency) {
		var widgets = super.getStatsSheetDescriptor(proficiency);
		var checkpoint = this.getCurrentCheckpoint(proficiency);
		var generated = this.generated;
		generated *= 1 + checkpoint.modifier().doubleValue();
		widgets.add(4, new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui.generates", generated), 0xFFAA00));
		return widgets;
	}

	@Override
	public int connectPrioritySorter(TechniqueAspect aspect1, TechniqueAspect aspect2) {
		int priority1 = priority.getOrDefault(aspect1.getClass(), 0);
		int priority2 = priority.getOrDefault(aspect2.getClass(), 0);
		int finalPriority = priority1 - priority2;
		return finalPriority != 0 ? finalPriority / Math.abs(finalPriority) : 0;
	}
}
