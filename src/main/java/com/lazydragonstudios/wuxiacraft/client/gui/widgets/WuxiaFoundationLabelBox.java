package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.cultivation.Element;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.RoundingMode;
import java.util.LinkedList;

@ParametersAreNonnullByDefault
public class WuxiaFoundationLabelBox extends AbstractWidget {

	private ResourceLocation elementLocation;

	private ICultivation cultivation;

	private boolean opened = false;

	private Runnable onClicked;

	private Element element;

	private LinkedList<Component> componentLines = new LinkedList<>();

	public WuxiaFoundationLabelBox(int x, int y, ResourceLocation elementLocation, ICultivation cultivation, Runnable onClicked) {
		super(x, y, 0, 0, Component.empty());
		this.elementLocation = elementLocation;
		this.cultivation = cultivation;
		this.onClicked = onClicked;
		this.element = WuxiaRegistries.ELEMENTS.get().getValue(this.elementLocation);
		this.prepareMessage();
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	private void prepareMessage() {
		if (this.element == null) return;
		if (this.cultivation == null) return;
		this.componentLines.clear();
		var foundationAmount = this.cultivation.getStat(System.ESSENCE, this.elementLocation, PlayerSystemElementalStat.FOUNDATION);
		foundationAmount = foundationAmount.setScale(Math.min(2, foundationAmount.scale()), RoundingMode.HALF_DOWN);
		var message = Component.literal(this.opened ? "+ " : "- ")
				.withStyle(this.opened ? ChatFormatting.RED : ChatFormatting.GREEN).
				append(Component.translatable("wuxiacraft.gui.foundation",
						Component.translatable(elementLocation.getNamespace() + ".element." + elementLocation.getPath()),
						foundationAmount.toPlainString()
				).withStyle(ChatFormatting.GOLD));
		componentLines.add(message);
		if (this.opened) {
			for (var stat : this.element.getPlayerStats().keySet()) {
				var statAmount = this.element.getFoundationStatValue(stat, foundationAmount);
				statAmount = statAmount.setScale(Math.min(statAmount.scale(), stat.displayScale), RoundingMode.HALF_DOWN);
				var messageLine = Component.literal("    ")
						.append(
								Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statAmount.toPlainString())
						).withStyle(ChatFormatting.WHITE);
				this.componentLines.add(messageLine);
			}
			for (var stat : this.element.getElementalStats().keySet()) {
				for (var elementLocation : this.element.getElementalStats().get(stat).keySet()) {
					var statAmount = this.element.getFoundationStatValue(stat, elementLocation, foundationAmount);
					statAmount = statAmount.setScale(Math.min(statAmount.scale(), stat.displayScale), RoundingMode.HALF_DOWN);
					var messageLine = Component.literal("    ")
							.append(Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
									Component.translatable(elementLocation.getNamespace() + ".element." + elementLocation.getPath()),
									statAmount.toPlainString())
							).withStyle(ChatFormatting.WHITE);
					this.componentLines.add(messageLine);
				}
			}
			for (var system : this.element.getSystemStats().keySet()) {
				for (var stat : this.element.getSystemStats().get(system).keySet()) {
					var statAmount = this.element.getFoundationStatValue(stat, foundationAmount);
					statAmount = statAmount.setScale(Math.min(statAmount.scale(), stat.displayScale), RoundingMode.HALF_DOWN);
					var messageLine = Component.literal("    ").append(
							Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
									statAmount.toPlainString())
					).withStyle(ChatFormatting.WHITE);
					this.componentLines.add(messageLine);
				}
			}
			for (var system : this.element.getSystemElementalStats().keySet()) {
				for (var stat : this.element.getSystemElementalStats().get(system).keySet()) {
					for (var elementLocation : this.element.getSystemElementalStats().get(system).get(stat).keySet()) {
						var statAmount = this.element.getStat(system, elementLocation, stat);
						statAmount = statAmount.setScale(Math.min(statAmount.scale(), 2), RoundingMode.HALF_DOWN);
						var messageLine = Component.literal("    ").append(
								Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
										system.name(),
										Component.translatable(elementLocation.getNamespace() + ".element." + elementLocation.getPath()),
										statAmount.toPlainString())
						).withStyle(ChatFormatting.WHITE);
						this.componentLines.add(messageLine);
					}
				}
			}
			this.setMessage(message);
		} else {
			this.setMessage(message);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), Minecraft.getInstance().font.lineHeight + 1)
				&& this.isValidClickButton(button) && this.active && this.visible) {
			this.opened = !this.opened;
			this.prepareMessage();
			onClicked.run();
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
		return false;
	}

	@Override
	public void setMessage(Component message) {
		super.setMessage(message);
		Font font = Minecraft.getInstance().font;
		int width = 0;
		for (var p : this.componentLines) {
			width = Math.max(font.width(p.getString()), width);
		}
		int height = (font.lineHeight + 1) * this.componentLines.size() - 2;
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		var font = Minecraft.getInstance().font;
		var currentTopPos = this.getY();
		for (var line : this.componentLines) {
			guiGraphics.drawString(font, line, this.getX(), currentTopPos, 0xFFAA00, true);
			currentTopPos += font.lineHeight + 1;
		}
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}
}
