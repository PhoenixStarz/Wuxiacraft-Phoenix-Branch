package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

public class QuinElementalGenerator extends TechniqueAspect {

	public double generated1;
	public double generated2;
	public double generated3;
	public double generated4;
	public double generated5;

	public ResourceLocation element1;
	public ResourceLocation element2;
	public ResourceLocation element3;
	public ResourceLocation element4;
	public ResourceLocation element5;

	@SuppressWarnings("rawtypes")
	private final static HashMap<Class, Integer> priority = new HashMap<>();

	static {
		priority.put(ElementalGenerator.class, -3);
		priority.put(ElementalConverter.class, -2);
		priority.put(ElementalConsumer.class, -1);
	}

	public QuinElementalGenerator(double generated1, ResourceLocation element1, double generated2, ResourceLocation element2, double generated3, ResourceLocation element3, double generated4, ResourceLocation element4, double generated5, ResourceLocation element5) {
		super();
		this.generated1 = generated1;
		this.generated2 = generated2;
		this.generated3 = generated3;
		this.generated4 = generated4;
		this.generated5 = generated5;
		this.element1 = element1;
		this.element2 = element2;
		this.element3 = element3;
		this.element4 = element4;
		this.element5 = element5;
	}

	@Override
	public void accept(HashMap<String, Object> metaData, BigDecimal proficiency) {
		super.accept(metaData, proficiency);
		String elementBase1 = "element-base-" + element1.getPath();
		String elementBase2 = "element-base-" + element2.getPath();
		String elementBase3 = "element-base-" + element3.getPath();
		String elementBase4 = "element-base-" + element4.getPath();
		String elementBase5 = "element-base-" + element5.getPath();
		var modifier = this.getCurrentCheckpoint(proficiency).modifier();
		var generated1 = this.generated1 * (1 + modifier.doubleValue());
		var generated2 = this.generated2 * (1 + modifier.doubleValue());
		var generated3 = this.generated3 * (1 + modifier.doubleValue());
		var generated4 = this.generated4 * (1 + modifier.doubleValue());
		var generated5 = this.generated5 * (1 + modifier.doubleValue());
		metaData.put(elementBase1, (double) metaData.getOrDefault(elementBase1, 0d) + generated1);
		metaData.put(elementBase2, (double) metaData.getOrDefault(elementBase2, 0d) + generated2);
		metaData.put(elementBase3, (double) metaData.getOrDefault(elementBase3, 0d) + generated3);
		metaData.put(elementBase4, (double) metaData.getOrDefault(elementBase4, 0d) + generated4);
		metaData.put(elementBase5, (double) metaData.getOrDefault(elementBase5, 0d) + generated4);
	}

	@Override
	public boolean canConnect(TechniqueAspect aspect) {
		if (aspect instanceof ElementalGenerator gen) {
			return (gen.element.equals(this.element1) || gen.element.equals(this.element2) || gen.element.equals(this.element3) || gen.element.equals(this.element4) || gen.element.equals(this.element5));
		}
		if (aspect instanceof DualElementalGenerator gen) {
			return (gen.element1.equals(this.element1) || gen.element1.equals(this.element2) || gen.element1.equals(this.element3) || gen.element1.equals(this.element4) || gen.element1.equals(this.element5)
				 || gen.element2.equals(this.element1) || gen.element2.equals(this.element2) || gen.element2.equals(this.element3) || gen.element2.equals(this.element4) || gen.element2.equals(this.element5));
		}
		if (aspect instanceof TriElementalGenerator gen) {
			return (gen.element1.equals(this.element1) || gen.element1.equals(this.element2) || gen.element1.equals(this.element3) || gen.element1.equals(this.element4) || gen.element1.equals(this.element5)
				 || gen.element2.equals(this.element1) || gen.element2.equals(this.element2) || gen.element2.equals(this.element3) || gen.element2.equals(this.element4) || gen.element2.equals(this.element5)
				 || gen.element3.equals(this.element1) || gen.element3.equals(this.element2) || gen.element3.equals(this.element3) || gen.element3.equals(this.element4) || gen.element3.equals(this.element5));
		}
		if (aspect instanceof QuadElementalGenerator gen) {
			return (gen.element1.equals(this.element1) || gen.element1.equals(this.element2) || gen.element1.equals(this.element3) || gen.element1.equals(this.element4) || gen.element1.equals(this.element5)
				 || gen.element2.equals(this.element1) || gen.element2.equals(this.element2) || gen.element2.equals(this.element3) || gen.element2.equals(this.element4) || gen.element2.equals(this.element5)
				 || gen.element3.equals(this.element1) || gen.element3.equals(this.element2) || gen.element3.equals(this.element3) || gen.element3.equals(this.element4) || gen.element3.equals(this.element5)
				 || gen.element4.equals(this.element1) || gen.element4.equals(this.element2) || gen.element4.equals(this.element3) || gen.element4.equals(this.element4) || gen.element4.equals(this.element5));
		}
		if (aspect instanceof QuinElementalGenerator gen) {
			return (gen.element1.equals(this.element1) || gen.element1.equals(this.element2) || gen.element1.equals(this.element3) || gen.element1.equals(this.element4) || gen.element1.equals(this.element5)
				 || gen.element2.equals(this.element1) || gen.element2.equals(this.element2) || gen.element2.equals(this.element3) || gen.element2.equals(this.element4) || gen.element2.equals(this.element5)
				 || gen.element3.equals(this.element1) || gen.element3.equals(this.element2) || gen.element3.equals(this.element3) || gen.element3.equals(this.element4) || gen.element3.equals(this.element5)
				 || gen.element4.equals(this.element1) || gen.element4.equals(this.element2) || gen.element4.equals(this.element3) || gen.element4.equals(this.element4) || gen.element4.equals(this.element5)
				 || gen.element5.equals(this.element1) || gen.element5.equals(this.element2) || gen.element5.equals(this.element3) || gen.element5.equals(this.element4) || gen.element5.equals(this.element5));
		}
		if (aspect instanceof ElementalConsumer con) {
			return (con.element.equals(this.element1) || con.element.equals(this.element2) || con.element.equals(this.element3) || con.element.equals(this.element4) || con.element.equals(this.element5));
		}
		if (aspect instanceof ElementalConverter con) {
			return (con.element.equals(this.element1) || con.element.equals(this.element2) || con.element.equals(this.element3) || con.element.equals(this.element4) || con.element.equals(this.element5));
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
		var nameLocation = this.getRegistryName();
		if (nameLocation == null) return;
		var name = new TranslatableComponent("wuxiacraft.aspect." + nameLocation.getPath() + ".name");
		var generated1 = this.generated1;
		var generated2 = this.generated2;
		var generated3 = this.generated3;
		var generated4 = this.generated4;
		var generated5 = this.generated5;
		var player = Minecraft.getInstance().player;
		if (player != null) {
			var cultivation = Cultivation.get(player);
			var proficiency = cultivation.getAspects().getAspectProficiency(nameLocation);
			var modifier = this.getCurrentCheckpoint(proficiency).modifier();
			generated1 *= 1d + modifier.doubleValue();
			generated2 *= 1d + modifier.doubleValue();
			generated3 *= 1d + modifier.doubleValue();
			generated4 *= 1d + modifier.doubleValue();
			generated5 *= 1d + modifier.doubleValue();
		}
		var amount1 = String.format("%.1f", generated1);
		var amount2 = String.format("%.1f", generated2);
		var amount3 = String.format("%.1f", generated3);
		var amount4 = String.format("%.1f", generated4);
		var amount5 = String.format("%.1f", generated5);

		var font = Minecraft.getInstance().font;
		var nameWidth = font.width(name);
		var amountWidth1 = font.width(amount1);
		var amountWidth2 = font.width(amount2);
		var amountWidth3 = font.width(amount3);
		var amountWidth4 = font.width(amount4);
		var amountWidth5 = font.width(amount5);
		var tooTipWidth = Math.max(nameWidth + 10, 150 + amountWidth1 + amountWidth2 + amountWidth3 + amountWidth4 + amountWidth4);
		RenderSystem.enableBlend();
		GuiComponent.fill(poseStack, mouseX, mouseY, mouseX + tooTipWidth, mouseY + 31, 0x6A8080A0);
		RenderSystem.setShaderTexture(0, new ResourceLocation(this.element1.getNamespace(), "textures/elements/" + this.element1.getPath() + ".png"));
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, mouseX + 5, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		RenderSystem.setShaderTexture(0, new ResourceLocation(this.element2.getNamespace(), "textures/elements/" + this.element2.getPath() + ".png"));
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, mouseX + 35 + amountWidth1, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		RenderSystem.setShaderTexture(0, new ResourceLocation(this.element3.getNamespace(), "textures/elements/" + this.element3.getPath() + ".png"));
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, mouseX + 65 + amountWidth1 + amountWidth2, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		RenderSystem.setShaderTexture(0, new ResourceLocation(this.element4.getNamespace(), "textures/elements/" + this.element4.getPath() + ".png"));
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, mouseX + 95 + amountWidth1 + amountWidth2 + amountWidth3, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		RenderSystem.setShaderTexture(0, new ResourceLocation(this.element5.getNamespace(), "textures/elements/" + this.element5.getPath() + ".png"));
		RenderSystem.enableBlend();
		GuiComponent.blit(poseStack, mouseX + 125 + amountWidth1 + amountWidth2 + amountWidth3 + amountWidth4, mouseY + 13, 16, 16, 0, 0, 16, 16, 16, 16);
		font.drawShadow(poseStack, name, mouseX + 5, mouseY + 2, 0xFFFFFF);
		font.drawShadow(poseStack, amount1, mouseX + 23, mouseY + 18, 0xFFFFFF);
		font.drawShadow(poseStack, amount2, mouseX + 53 + amountWidth1, mouseY + 18, 0xFFFFFF);
		font.drawShadow(poseStack, amount3, mouseX + 83 + amountWidth1 + amountWidth2, mouseY + 18, 0xFFFFFF);
		font.drawShadow(poseStack, amount4, mouseX + 113 + amountWidth1 + amountWidth2 + amountWidth3, mouseY + 18, 0xFFFFFF);
		font.drawShadow(poseStack, amount5, mouseX + 143 + amountWidth1 + amountWidth2 + amountWidth3 + amountWidth4, mouseY + 18, 0xFFFFFF);
		RenderSystem.disableBlend();
	}

	@Nonnull
	@Override
	public LinkedList<AbstractWidget> getStatsSheetDescriptor(BigDecimal proficiency) {
		var widgets = super.getStatsSheetDescriptor(proficiency);
		var checkpoint = this.getCurrentCheckpoint(proficiency);
		var generated1 = this.generated1;
		var generated2 = this.generated2;
		var generated3 = this.generated3;
		var generated4 = this.generated4;
		var generated5 = this.generated5;
		generated1 *= 1 + checkpoint.modifier().doubleValue();
		generated2 *= 1 + checkpoint.modifier().doubleValue();
		generated3 *= 1 + checkpoint.modifier().doubleValue();
		generated4 *= 1 + checkpoint.modifier().doubleValue();
		generated5 *= 1 + checkpoint.modifier().doubleValue();
		widgets.add(4, new WuxiaLabel(0, 0, new TranslatableComponent("wuxiacraft.gui.generates", generated1 + " " + element1.getPath()), 0xFFAA00));
		widgets.add(4, new WuxiaLabel(0, 0, new TranslatableComponent("wuxiacraft.gui.generates", generated2 + " " + element2.getPath()), 0xFFAA00));
		widgets.add(4, new WuxiaLabel(0, 0, new TranslatableComponent("wuxiacraft.gui.generates", generated3 + " " + element3.getPath()), 0xFFAA00));
		widgets.add(4, new WuxiaLabel(0, 0, new TranslatableComponent("wuxiacraft.gui.generates", generated4 + " " + element4.getPath()), 0xFFAA00));
		widgets.add(4, new WuxiaLabel(0, 0, new TranslatableComponent("wuxiacraft.gui.generates", generated5 + " " + element5.getPath()), 0xFFAA00));
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
