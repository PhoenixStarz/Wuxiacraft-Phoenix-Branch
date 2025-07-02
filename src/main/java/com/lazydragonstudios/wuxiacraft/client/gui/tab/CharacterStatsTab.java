package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.*;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.SystemContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.networking.UpdateRegulatorsMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;

public class CharacterStatsTab extends IntrospectionTab {

	private final HashMap<PlayerStat, WuxiaLabel> displayLabels = new HashMap<>();

	private final HashMap<ResourceLocation, HashMap<PlayerElementalStat, WuxiaLabel>> displayElementalLabels = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, WuxiaLabel>> displaySystemLabels = new HashMap<>();

	private final HashMap<System, HashMap<ResourceLocation, HashMap<PlayerSystemElementalStat, WuxiaLabel>>> displaySystemElementalLabels = new HashMap<>();

	private WuxiaColumnFlowPanel statsPanel;

	private HashMap<System, WuxiaVerticalFlowPanel> systemStats;

	private WuxiaSliderButton agilitySlider;

	private WuxiaSliderButton strengthSlider;

	private WuxiaLabel strengthSliderLabel;

	private WuxiaLabel agilitySliderLabel;

	public CharacterStatsTab(String name) {
		super(name, new Point(0, 36));
	}

	@Override
	public void init(IntrospectionScreen screen) {
		int color = 0xFFAA00;
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		statsPanel = new WuxiaColumnFlowPanel(0, 0, 100, 100, Component.empty());
		statsPanel.margin = 2;
		systemStats = new HashMap<>();
		screen.addRenderableWidget(statsPanel);
		for (var stat : PlayerStat.values()) {
			BigDecimal statDecimal = cultivation.getStat(stat);
			var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), 2), RoundingMode.HALF_UP).toEngineeringString();
			var label = new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statValue), color);
			displayLabels.put(stat, label);
			statsPanel.addChild(label);
		}
		for (var elementLocation : cultivation.getElementalStats().keySet()) {
			for (var stat : cultivation.getElementalStats().get(elementLocation).keySet()) {
				BigDecimal statDecimal = cultivation.getStat(elementLocation, stat);
				var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), 2), RoundingMode.HALF_UP).toEngineeringString();
				var label = new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statValue), color);
				displayElementalLabels.putIfAbsent(elementLocation, new HashMap<>());
				displayElementalLabels.get(elementLocation).put(stat, label);
				statsPanel.addChild(label);
			}
		}
		for (var system : System.values()) {
			displaySystemLabels.put(system, new HashMap<>());
			WuxiaVerticalFlowPanel systemStatPanel = new WuxiaVerticalFlowPanel(0, 0, 100, 100, Component.empty());
			systemStatPanel.margin = 2;
			systemStats.put(system, systemStatPanel);
			SystemContainer systemData = cultivation.getSystemData(system);
			ResourceLocation realmLocation = systemData.getStage().realm;
			var realmNameLabel = new WuxiaLabel(0, 0, Component.translatable(realmLocation.getNamespace() + ".realm." + realmLocation.getPath()), color);
			ResourceLocation currentStageLocation = systemData.currentStage;
			var stageNameLabel = new WuxiaLabel(0, 0, Component.translatable(currentStageLocation.getNamespace() + ".stage." + currentStageLocation.getPath()), color);
			systemStatPanel.addChild(realmNameLabel);
			systemStatPanel.addChild(stageNameLabel);
			for (var stat : PlayerSystemStat.values()) {
				BigDecimal statDecimal = cultivation.getStat(system, stat);
				var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), 2), RoundingMode.HALF_UP).toEngineeringString();
				var label = new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statValue), color);
				displaySystemLabels.get(system).put(stat, label);
				systemStats.get(system).addChild(label);
			}
			for (var stat : systemData.getElementalStats()) {
				for (var element : systemData.getElementsForStat(stat)) {
					if (stat == PlayerSystemElementalStat.FOUNDATION) {
						var foundationBox = new WuxiaFoundationLabelBox(0, 0, element, cultivation, systemStats.get(system)::recalculateContentSpace);
						systemStats.get(system).addChild(foundationBox);
						continue;
					}
					BigDecimal statDecimal = cultivation.getStat(system, element, stat);
					var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), 2), RoundingMode.HALF_UP).toEngineeringString();
					var label = new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
							Component.translatable(element.getNamespace() + ".element." + element.getPath()),
							statValue), color);
					displaySystemElementalLabels.putIfAbsent(system, new HashMap<>());
					displaySystemElementalLabels.get(system).putIfAbsent(element, new HashMap<>());
					displaySystemElementalLabels.get(system).get(element).putIfAbsent(stat, label);
					systemStats.get(system).addChild(label);
				}
			}
			statsPanel.recalculateContentSpace();
			screen.addRenderableWidget(systemStats.get(system));
		}
		strengthSliderLabel = new WuxiaLabel(38, 51, Component.translatable("wuxiacraft.gui.strength", ""), 0xFFAA00);
		screen.addRenderableWidget(strengthSliderLabel);
		strengthSlider = new WuxiaSliderButton(68, 44, 160, 20, cultivation.getStrengthRegulator(), v -> {
			cultivation.setStrengthRegulator(v);
			sendRegulatorsMessage();
		});
		agilitySliderLabel = new WuxiaLabel(230, 51, Component.translatable("wuxiacraft.gui.agility", ""), 0xFFAA00);
		screen.addRenderableWidget(agilitySliderLabel);
		agilitySlider = new WuxiaSliderButton(260, 44, 160, 20, cultivation.getAgilityRegulator(), v -> {
			cultivation.setAgilityRegulator(v);
			sendRegulatorsMessage();
		});
		screen.addRenderableWidget(agilitySlider);
		screen.addRenderableWidget(strengthSlider);
	}

	public void sendRegulatorsMessage() {
		WuxiaPacketHandler.INSTANCE.sendToServer(new UpdateRegulatorsMessage(strengthSlider.getValue(), agilitySlider.getValue()));
	}

	@Override
	public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		for (var stat : displayLabels.keySet()) {
			BigDecimal statDecimal = cultivation.getStat(stat);
			var statValue = statDecimal.setScale(Math.min(stat.displayScale, statDecimal.scale()), RoundingMode.HALF_UP).toEngineeringString();
			displayLabels.get(stat).setMessage(Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statValue));
		}
		for (var elementLocation : this.displayElementalLabels.keySet()) {
			for (var stat : this.displayElementalLabels.get(elementLocation).keySet()) {
				BigDecimal statDecimal = cultivation.getStat(elementLocation, stat);
				var statValue = statDecimal.setScale(Math.min(stat.displayScale, statDecimal.scale()), RoundingMode.HALF_UP).toEngineeringString();
				displayElementalLabels.get(elementLocation).get(stat).setMessage(Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
						Component.translatable("wuxiacraft.element." + elementLocation.getPath()),
						statValue));
			}
		}
		statsPanel.rearrangeItems();
		for (var system : System.values()) {
			if (!displaySystemLabels.containsKey(system)) continue;
			for (var stat : displaySystemLabels.get(system).keySet()) {
				BigDecimal statDecimal = cultivation.getStat(system, stat);
				var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), stat.displayScale), RoundingMode.HALF_UP).toEngineeringString();
				displaySystemLabels.get(system).get(stat).setMessage(Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(), statValue));
			}
			if (!displaySystemElementalLabels.containsKey(system)) continue;
			if (displaySystemElementalLabels.get(system).isEmpty()) continue;
			for (var element : displaySystemElementalLabels.get(system).keySet()) {
				for (var stat : displaySystemElementalLabels.get(system).get(element).keySet()) {
					BigDecimal statDecimal = cultivation.getStat(system, element, stat);
					var statValue = statDecimal.setScale(Math.min(statDecimal.scale(), 2), RoundingMode.HALF_UP).toEngineeringString();
					displaySystemElementalLabels.get(system).get(element).get(stat)
							.setMessage(Component.translatable("wuxiacraft.gui." + stat.name().toLowerCase(),
									Component.translatable(element.getNamespace() + ".element." + element.getPath()),
									statValue));
				}
			}
		}
		var scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		var scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		int availableXSpace = scaledWidth - 36;
		int availableYSpace = scaledHeight - 72;
		statsPanel.setX(36);
		statsPanel.setY(72);
		statsPanel.setWidth(availableXSpace);
		statsPanel.setHeight(availableYSpace / 2);
		for (var system : System.values()) {
			if (!this.systemStats.containsKey(system)) continue;
			this.systemStats.get(system).setX(36 + (int) ((double) system.ordinal() * (double) availableXSpace / 3.0d));
			this.systemStats.get(system).setY(72 + availableYSpace / 2);
			this.systemStats.get(system).setWidth(availableXSpace / 3);
			this.systemStats.get(system).setHeight(availableYSpace / 2);
			this.systemStats.get(system).rearrangeItems();
		}
		if (this.strengthSlider == null) return;
		if (this.agilitySlider == null) return;
		int strengthLabelWidth = Minecraft.getInstance().font.width(strengthSliderLabel.getMessage());
		int agilityLabelWidth = Minecraft.getInstance().font.width(agilitySliderLabel.getMessage());
		this.strengthSlider.setX(40 + strengthLabelWidth);
		this.agilitySliderLabel.setX(42 + strengthLabelWidth + strengthSlider.getWidth());
		this.agilitySlider.setX(44 + strengthLabelWidth + strengthSlider.getWidth() + agilityLabelWidth);
	}

	@Override
	public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

	}
}
