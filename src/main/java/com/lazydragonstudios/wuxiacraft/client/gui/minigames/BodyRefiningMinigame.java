package com.lazydragonstudios.wuxiacraft.client.gui.minigames;

import com.lazydragonstudios.wuxiacraft.client.gui.MeditateScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaButton;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaFlowPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTexturedButton;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaVerticalFlowPanel;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.networking.SelectBodyPartElementMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class BodyRefiningMinigame implements Minigame {

	private int guiLeft;

	private int guiTop;

	private WuxiaFlowPanel bodyPartsSelector;

	private WuxiaFlowPanel partElementSelector;

	private final HashMap<ResourceLocation, WuxiaButton> partButtons = new HashMap<>();

	private final HashMap<ResourceLocation, WuxiaTexturedButton> elementButtons = new HashMap<>();

	private ResourceLocation selectedBodyPart = null;

	private Font font;

	@Override
	public void init(MeditateScreen screen) {
		this.font = screen.getMinecraft().font;
		guiLeft = screen.getGuiLeft();
		guiTop = screen.getGuiTop();
		bodyPartsSelector = new WuxiaVerticalFlowPanel(guiLeft + 5, guiTop + 24, 137, 139, Component.empty());
		partElementSelector = new WuxiaVerticalFlowPanel(guiLeft + 147, guiTop + 24, 39, 139, Component.empty());
		bodyPartsSelector.setMargin(3);
		partElementSelector.setMargin(3);
		screen.addChild(bodyPartsSelector);
		screen.addChild(partElementSelector);
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		if (!bodyData.techniqueData.modifier.isValidTechnique()) return;

		for (var partLocation : bodyData.unlockedParts()) {
			var part = WuxiaRegistries.BODY_PART.get().getValue(partLocation);
			if (part == null) continue;
			var button = new WuxiaButton(0, 0, 123, 14, Component.translatable("wuxiacraft.body_part." + partLocation.getPath()),
					() -> selectABodyPart(partLocation));
			this.bodyPartsSelector.addChild(button);
			this.partButtons.put(partLocation, button);
		}

		for (var elementLocation : WuxiaRegistries.ELEMENTS.get().getKeys()) {
			if (elementLocation.getPath().equals("butt")) continue;
			var elementTexture = new ResourceLocation(elementLocation.getNamespace(), "textures/elements/" + elementLocation.getPath() + ".png");
			var button = new WuxiaTexturedButton(0, 0, 16, 16, Component.empty(), () -> selectAElement(elementLocation),
					new ResourceLocation[]{elementTexture}, new Rectangle[]{new Rectangle(0, 0, 16, 16)});
			this.partElementSelector.addChild(button);
			this.elementButtons.put(elementLocation, button);
		}

	}

	public void selectABodyPart(ResourceLocation location) {
		if (this.selectedBodyPart != null) {
			var button = this.partButtons.get(this.selectedBodyPart);
			button.setColor(1f, 1f, 1f);
		}
		this.selectedBodyPart = location;
		if (this.selectedBodyPart != null) {
			var button = this.partButtons.get(this.selectedBodyPart);
			button.setColor(1f, 0.7f, 0.2f);
		}
	}

	public void selectAElement(ResourceLocation location) {
		if (this.selectedBodyPart == null) return;
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		bodyData.selectElementToPart(this.selectedBodyPart, location);
		WuxiaPacketHandler.INSTANCE.sendToServer(new SelectBodyPartElementMessage(this.selectedBodyPart, location));
	}

	@Override
	public boolean onMouseClick(double x, double y, int button) {
		return false;
	}

	@Override
	public boolean onMouseRelease(double x, double y, int button) {
		return false;
	}

	@Override
	public void onMouseMove(double x, double y) {

	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (this.selectedBodyPart == null) return;
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		ResourceLocation elementLocation = bodyData.getSelectedElementByPart(this.selectedBodyPart);
		if (elementLocation != null) {
			var button = this.elementButtons.get(elementLocation);
			var buttonBounds = new Rectangle(
					this.partElementSelector.getX() + button.getX() - this.partElementSelector.currentScrollX - this.guiLeft,
					this.partElementSelector.getY() + button.getY() - this.partElementSelector.currentScrollY - this.guiTop,
					button.getWidth(), button.getHeight());
			guiGraphics.fill(buttonBounds.x - 1, buttonBounds.y - 1, buttonBounds.x + buttonBounds.width + 1, buttonBounds.y + buttonBounds.height + 1, 0x6AD03005);
		}

		var forgedElement = bodyData.getForgedElementByPart(this.selectedBodyPart);
		var elementText = Component.translatable(forgedElement != null ? "wuxiacraft.element." + forgedElement.getPath() : "wuxiacraft.gui.none");
		var forgedText = Component.translatable("wuxiacraft.gui.forged", elementText);
		var forgedAmount = bodyData.getForgedAmountByPart(this.selectedBodyPart).setScale(2, RoundingMode.HALF_UP).toPlainString();
		var amountText = Component.translatable("wuxiacraft.gui.forgedAmount", forgedAmount);
		var width = Math.max(this.font.width(amountText), this.font.width(forgedText));
		guiGraphics.fill(200, 24, 200 + width + 10, 24 + 31, 0x6A8080A0);
		guiGraphics.drawString(this.font, forgedText, 205, 29, 0xFFffFFff, true);
		guiGraphics.drawString(this.font, amountText, 205, 42, 0xFFffFFff, true);
	}

	@Override
	public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (MathUtil.inBounds(mouseX, mouseY, this.partElementSelector.getX(), this.partElementSelector.getY(), this.partElementSelector.getWidth(), this.partElementSelector.getHeight())) {
			for (var elementLocation : this.elementButtons.keySet()) {
				var button = this.elementButtons.get(elementLocation);
				var buttonBounds = new Rectangle(
						this.partElementSelector.getX() + button.getX() - this.partElementSelector.currentScrollX,
						this.partElementSelector.getY() + button.getY() - this.partElementSelector.currentScrollY,
						button.getWidth(), button.getHeight());
				if (MathUtil.inBounds(mouseX, mouseY, buttonBounds.getX(), buttonBounds.getY(), buttonBounds.getWidth(), buttonBounds.getHeight())) {
					var elementName = Component.translatable("wuxiacraft.element." + elementLocation.getPath());
					var width = this.font.width(elementName);
					guiGraphics.fill(mouseX, mouseY, mouseX + 6 + width, mouseY + 13, 0x6A8080A0);
					guiGraphics.drawString(this.font, elementName, mouseX + 2, mouseY + 2, 0xFFffFFff);
				}
			}
		}
	}

	@Override
	public void tick() {

	}

	@Override
	public void close(MeditateScreen screen) {
		Minigame.super.close(screen);
		screen.clearChildren();
	}
}
