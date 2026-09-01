package com.lazydragonstudios.wuxiacraft.client.gui.minigames;

import com.lazydragonstudios.wuxiacraft.client.gui.MeditateScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaButton;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaFlowPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTexturedButton;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaVerticalFlowPanel;
import com.lazydragonstudios.wuxiacraft.cultivation.body.*;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.ElementSystemConverter;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.BodyTransformationAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.networking.SelectBodyPartElementMessage;
import com.lazydragonstudios.wuxiacraft.networking.SelectBodyTransformationMessage;
import com.lazydragonstudios.wuxiacraft.networking.RemoveSelectedElementByBodyPartMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;

public class BodyRefiningMinigame implements Minigame {

	private int guiLeft;

	private int guiTop;

	private WuxiaFlowPanel partGroupsSelector;

	private WuxiaFlowPanel bodyPartsSelector;

	private WuxiaFlowPanel partElementSelector;

	private WuxiaFlowPanel partTypesSelector;

	private final HashMap<BodyPartGroup, WuxiaButton> groupButtons = new HashMap<>();

	private final HashMap<ResourceLocation, WuxiaButton> partButtons = new HashMap<>();

	private final HashMap<ResourceLocation, WuxiaTexturedButton> elementButtons = new HashMap<>();
	
	private final HashMap<ResourceLocation, WuxiaButton> typeButtons = new HashMap<>();

	private BodyPartGroup selectedPartGroup = null;
	private ResourceLocation selectedBodyPart = null;
	private ResourceLocation selectedPartType = null;
	private Boolean selectedTransformations = false;

	private Font font;

	@Override
	public void init(MeditateScreen screen) {
		this.font = screen.getMinecraft().font;
		guiLeft = screen.getGuiLeft();
		guiTop = screen.getGuiTop();
		partGroupsSelector = new WuxiaVerticalFlowPanel(guiLeft - 62, guiTop + 24, 65, 139, Component.empty());
		bodyPartsSelector = new WuxiaVerticalFlowPanel(guiLeft + 5, guiTop + 24, 137, 139, Component.empty());
		partElementSelector = new WuxiaVerticalFlowPanel(guiLeft + 147, guiTop + 24, 39, 139, Component.empty());
		partTypesSelector = new WuxiaVerticalFlowPanel(guiLeft + 196, guiTop + 24, 65, 139, Component.empty());
		partGroupsSelector.setMargin(3);
		bodyPartsSelector.setMargin(3);
		partElementSelector.setMargin(3);
		partTypesSelector.setMargin(3);
		screen.addChild(partGroupsSelector);
		screen.addChild(bodyPartsSelector);
		screen.addChild(partElementSelector);
		screen.addChild(partTypesSelector);
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		if (!bodyData.techniqueData.modifier.isValidTechnique()) return;

		for (var partGroup : BodyPartGroup.values()) {
			var button = new WuxiaButton(0, 0, 60, 14, Component.translatable("wuxiacraft.part_group." + String.valueOf(partGroup).toLowerCase()),
					() -> selectABodyPartGroup(partGroup));
			this.partGroupsSelector.addChild(button);
			this.groupButtons.put(partGroup, button);
		}

		this.loadBodyParts();

		for (var elementLocation : WuxiaRegistries.ELEMENTS.get().getKeys()) {
			boolean cancel = true;
			if (elementLocation.getPath().equals(WuxiaElements.DEMONIC.getId().getPath())) continue;
			for (var knownAspect : cultivation.getAspects().getKnownAspects().stream().toList()) {
				if (WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(knownAspect) instanceof ElementSystemConverter con) {
					if (con.element.equals(elementLocation) && con.system == System.BODY) {
						cancel = false;
						break;
					}
				}
			}
			if (cancel == true) continue;
			var elementTexture = new ResourceLocation(elementLocation.getNamespace(), "textures/elements/" + elementLocation.getPath() + ".png");
			var button = new WuxiaTexturedButton(0, 0, 16, 16, Component.empty(), () -> selectAElement(elementLocation),
					new ResourceLocation[]{elementTexture}, new Rectangle[]{new Rectangle(0, 0, 16, 16)});
			this.partElementSelector.addChild(button);
			this.elementButtons.put(elementLocation, button);
		}

		for (var partType : WuxiaRegistries.BODY_PART_TYPE.get().getKeys()) {
			var button = new WuxiaButton(0, 0, 60, 14, Component.translatable("wuxiacraft.part_type." + partType.getPath()),
					() -> selectABodyPartType(partType));
			this.partTypesSelector.addChild(button);
			this.typeButtons.put(partType, button);
		}
		var button = new WuxiaButton(0,0,60,14, Component.translatable("wuxiacraft.gui.transformation_selection_button"),
				() -> selectTransformationsBoolean());
		this.partGroupsSelector.addChild(button);
		this.groupButtons.put(null, button);
	}
	
	public void loadBodyParts() {
		this.selectedTransformations = false;
		var transformationButton = this.groupButtons.get(null);
		if (transformationButton != null) transformationButton.setColor(1f, 1f, 1f);	
		this.bodyPartsSelector.clearChildren();
		this.partButtons.clear();
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		for (var partLocation : bodyData.unlockedParts()) {
			var part = WuxiaRegistries.BODY_PART.get().getValue(partLocation);
			if (part == null) continue;
			if (this.selectedPartGroup != null && part.getGroup() != this.selectedPartGroup) continue;
			var partType = WuxiaRegistries.BODY_PART_TYPE.get().getKey(part.getType()).getPath();
			if (this.selectedPartType != null && !partType.equals(this.selectedPartType.getPath())) continue;
			int elementColor = 0xFFFFFF;
			if (bodyData.getSelectedElementByPart(partLocation) != null)
			switch (bodyData.getSelectedElementByPart(partLocation).getPath()) {
				case "physical": elementColor = 0x691717;
					break;
				case "fire": elementColor = 0xFE8944;
					break;
				case "earth": elementColor = 0x715727;
					break;
				case "metal": elementColor = 0xFFF6BC;
					break;
				case "water": elementColor = 0x324496;
					break;
				case "wood": elementColor = 0x2E7D00;
					break;
				case "lightning": elementColor = 0x9631F0;
					break;
				case "wind": elementColor = 0xC5C5C5;
					break;
				case "poison": elementColor = 0x2D6E37;
					break;
				case "light": elementColor = 0xFFFFFF;
					break;
				case "dark": elementColor = 0x000000;
					break;
				case "space": elementColor = 0x25053E;
					break;
				case "time": elementColor = 0x6FF1A6;
					break;
				case "rebirth": elementColor = 0x22ADFE;
					break;
			}
			var button = new WuxiaButton(0, 0, 123, 14, Component.translatable("wuxiacraft.body_part." + partLocation.getPath()),
					() -> selectABodyPart(partLocation), elementColor);
			this.bodyPartsSelector.addChild(button);
			this.partButtons.put(partLocation, button);
		}
	}
	
	public void selectABodyPartGroup(BodyPartGroup location) {
		if (this.selectedPartGroup != null) {
			var button = this.groupButtons.get(this.selectedPartGroup);
			button.setColor(1f, 1f, 1f);
		}
		if (this.selectedPartGroup == location) {			
			var button = this.groupButtons.get(location);
			this.selectedPartGroup = null;
			button.setColor(1f, 1f, 1f);
		} else
		this.selectedPartGroup = location;
		if (this.selectedPartGroup != null) {
			var button = this.groupButtons.get(this.selectedPartGroup);
			button.setColor(1f, 0.7f, 0.2f);
		}
		this.selectedBodyPart = null;
		this.loadBodyParts();
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
		if (bodyData.getSelectedElementByPart(this.selectedBodyPart) == location) {
			bodyData.removeSelectedElementByBodyPart(this.selectedBodyPart);
			WuxiaPacketHandler.INSTANCE.sendToServer(new RemoveSelectedElementByBodyPartMessage(this.selectedBodyPart));
		} else {
			bodyData.selectElementToPart(this.selectedBodyPart, location);
			WuxiaPacketHandler.INSTANCE.sendToServer(new SelectBodyPartElementMessage(this.selectedBodyPart, location));
		}
		this.loadBodyParts();
		this.selectABodyPart(this.selectedBodyPart);
	}
	
	public void selectABodyPartType(ResourceLocation location) {
		if (this.selectedPartType != null) {
			var button = this.typeButtons.get(this.selectedPartType);
			button.setColor(1f, 1f, 1f);
		}
		if (this.selectedPartType == location) {
			var button = this.typeButtons.get(location);
			this.selectedPartType = null;
			button.setColor(1f, 1f, 1f);
		} else
		this.selectedPartType = location;
		if (this.selectedPartType != null) {
			var button = this.typeButtons.get(this.selectedPartType);
			button.setColor(1f, 0.7f, 0.2f);
		}
		this.selectedBodyPart = null;
		this.loadBodyParts();
	}

	public void selectTransformationsBoolean() {
		var button = this.groupButtons.get(null);
		if (button == null) return;
		if (this.selectedTransformations == false) {
			button.setColor(1f, 0.7f, 0.2f);
			this.selectedTransformations = true;
			this.selectedBodyPart = null;
			this.loadTransformations();
		} else {
			button.setColor(1f, 1f, 1f);
			this.loadBodyParts();
		}
	}

	public void loadTransformations() {
		if (this.selectedPartGroup != null) {
			var button = this.groupButtons.get(this.selectedPartGroup);
			button.setColor(1f, 1f, 1f);
			this.selectedPartGroup = null;
		}
		if (this.selectedPartType != null) {
			var button = this.typeButtons.get(this.selectedPartType);
			button.setColor(1f, 1f, 1f);
			this.selectedPartType = null;
		}		
		this.bodyPartsSelector.clearChildren();
		this.partButtons.clear();
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);
		var aspectData = cultivation.getAspects();
		var knownTransformationAspects = aspectData.getKnownAspects().stream()
				.filter(aspectLocation -> TechniqueUtil.getTransformationAspects().contains(aspectLocation))
				.sorted(Comparator.comparing(aspectData::getAspectProficiency).reversed()).toList();
		for (var transformationAspectLocation : knownTransformationAspects) {
			var transformationAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(transformationAspectLocation);
			if (transformationAspect != null) {
				var checkpoint = transformationAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(transformationAspectLocation));
				if (checkpoint instanceof BodyTransformationAspect.TransformationCheckpoint transformationCheckpoint) {
					var button = new WuxiaButton(0, 0, 123, 14, Component.translatable("wuxiacraft.aspect." + transformationAspectLocation.getPath() + ".name"),
							() -> selectABodyTransformation(transformationCheckpoint.getTransformationLocation()), 0xFFFFFF);
					this.bodyPartsSelector.addChild(button);
					this.partButtons.put(transformationCheckpoint.getTransformationLocation(), button);
				}
			}
		}
		if (bodyData.getDisplayBodyTransformation() != null) {
			var button = this.partButtons.get(bodyData.getDisplayBodyTransformation());
			button.setColor(1f, 0.7f, 0.2f);
		}
	}

	public void selectABodyTransformation(ResourceLocation location) {
		if (Minecraft.getInstance().player == null) return;
		var cultivation = Cultivation.get(Minecraft.getInstance().player);
		var bodyData = (BodyCultivationContainer) cultivation.getSystemData(System.BODY);

		if (bodyData.getDisplayBodyTransformation() != null) {
			var button = this.partButtons.get(bodyData.getDisplayBodyTransformation());
			button.setColor(1f, 1f, 1f);
		}
		if (bodyData.getDisplayBodyTransformation() == location) {
			var button = this.partButtons.get(location);
			bodyData.setDisplayTransformation(null);
			button.setColor(1f, 1f, 1f);
			WuxiaPacketHandler.INSTANCE.sendToServer(new SelectBodyTransformationMessage(new ResourceLocation("wuxiacraft:none")));
		} else
		bodyData.setDisplayTransformation(location);
		if (bodyData.getDisplayBodyTransformation() != null) {
			var button = this.partButtons.get(bodyData.getDisplayBodyTransformation());
			button.setColor(1f, 0.7f, 0.2f);
			WuxiaPacketHandler.INSTANCE.sendToServer(new SelectBodyTransformationMessage(location));
		}
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
		guiGraphics.fill(200, 5, 200 + width + 10, 27, 0x6A8080A0);
		guiGraphics.drawString(this.font, forgedText, 205, 6, 0xFFffFFff, true);
		guiGraphics.drawString(this.font, amountText, 205, 18, 0xFFffFFff, true);
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
