package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.capabilities.ClientAnimationState;
import com.lazydragonstudios.wuxiacraft.client.gui.minigames.*;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.*;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRealms;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.networking.AttemptRebirthMessage;
import com.lazydragonstudios.wuxiacraft.networking.StartTribulationMessage;
import com.lazydragonstudios.wuxiacraft.networking.BroadcastAnimationChangeRequestMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MeditateScreen extends Screen {

	public static final ResourceLocation MEDITATE_SCREEN = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/cultivation_minigame_screen.png");

	public MeditateScreen() {
		super(Component.empty());
	}

	private Minigame minigame = null;

	private System system = System.ESSENCE;

	private boolean canBreakThrough = false;
	private boolean canRebirth = false;

	private static final HashMap<ResourceLocation, Supplier<Minigame>> stageMiniGames = new HashMap<>();

	static {
		stageMiniGames.put(WuxiaRealms.BODY_MORTAL_STAGE.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_TENDON_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_MARROW_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_VEINS_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_MUSCLE_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_SKIN_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_BONE_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_TORSO_FORGING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_VISCERA_TEMPERING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_HEAD_TEMPERING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_STANDARD_MERIDIAN_TEMPERING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.BODY_EXTRAORDINARY_MERIDIAN_TEMPERING.getId(), BodyRefiningMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_MORTAL_STAGE.getId(), HoldingClickMinigame::new);			 
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_GATHERING_STAGE.getId(), HoldingClickMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_PATHWAYS_STAGE.getId(), HoldingClickMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_CONDENSATION_STAGE.getId(), HoldingClickMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_PHENOMENON_STAGE.getId(), DraggingToDanTianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_SHAPING_STAGE.getId(), DraggingToDanTianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_MOLDING_STAGE.getId(), DraggingToDanTianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_QI_SOLIDIFICATION_STAGE.getId(), DraggingToDanTianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_CORE_SHAPING_STAGE.getId(), DraggingThroughPathwaysMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_GOLDEN_CORE_STAGE.getId(), DraggingThroughPathwaysMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_CORE_EXPANSION_STAGE.getId(), DraggingThroughPathwaysMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_REVOLVING_CORE_STAGE.getId(), DraggingThroughPathwaysMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_IMMORTAL_TRANSFORMATION_STAGE.getId(), ClosingTheCircleMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_IMMORTAL_POND_STAGE.getId(), ClosingTheCircleMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_RIVER_EXPANSION_STAGE.getId(), ClosingTheCircleMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_BOUNDLESS_SEA_STAGE.getId(), ClosingTheCircleMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_IMMORTAL_OCEAN_STAGE.getId(), DraggingAllAspectsToDantianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_OCEAN_OBLITERATION_STAGE.getId(), DraggingAllAspectsToDantianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_STARRY_FIELD_STAGE.getId(), DraggingAllAspectsToDantianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_GALAXY_STAGE.getId(), DraggingAllAspectsToDantianMinigame::new);
		stageMiniGames.put(WuxiaRealms.ESSENCE_VOID_NEBULA_STAGE.getId(), DraggingAllAspectsToDantianMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_MORTAL_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_FEELING_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_SENSE_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_PERCEPTION_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_AWARENESS_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_CONSCIOUSNESS_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_OBSERVATION_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_UNDERSTANDING_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_BARON_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_VISCOUNT_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_COUNT_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_MARQUESS_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_DUKE_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_GRAND_DUKE_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_ARCHDUKE_STAGE.getId(), DivineMinigame::new);
		stageMiniGames.put(WuxiaRealms.DIVINE_KING_STAGE.getId(), DivineMinigame::new);
	}

	private int guiTop = 0;

	private int guiLeft = 0;

	public void clearChildren() {
		this.children().clear();
		this.renderables.clear();
	}

	@Override
	protected void init() {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var systems = new System[]{System.ESSENCE, System.DIVINE, System.BODY}; //give priority to essence
		for (var system : systems) {
			var systemData = cultivation.getSystemData(system);
			if (!systemData.techniqueData.modifier.isValidTechnique()) continue;
			this.system = system;
			var supplier = stageMiniGames.get(systemData.currentStage);
			if (supplier == null) continue;
			this.minigame = supplier.get();
			break;
		}
		var scaledResX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		var scaledResY = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		this.guiTop = (scaledResY - 170) / 2;
		this.guiLeft = (scaledResX - 200) / 2;
		if (this.minigame == null) return;
		this.minigame.init(this);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		renderBackground(guiGraphics);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(this.guiLeft, this.guiTop, 0);
		guiGraphics.blit(MEDITATE_SCREEN, 0, 0, 0, 0, 200, 170);
		for (var system : System.values()) {
			int texX = 0;
			if (system == this.system) texX = 63;
			guiGraphics.blit(MEDITATE_SCREEN, 5 + 63 * system.ordinal(), 6, texX, 170, 63, 14);
		}
		if (this.canBreakThrough) {
			guiGraphics.blit(MEDITATE_SCREEN, 69, 170, 0, 170, 63, 14);
		}
		if (this.canRebirth) {
			guiGraphics.blit(MEDITATE_SCREEN, 135, 170, 0, 170, 63, 14);
		}
		this.renderLabels(guiGraphics, mouseX, mouseY, partialTicks);
		if (this.minigame == null) {
			guiGraphics.pose().popPose();
			return;
		}
		minigame.render(guiGraphics, mouseX - this.guiLeft, mouseY - this.guiTop, partialTicks);
		guiGraphics.pose().popPose();
		super.render(guiGraphics, mouseX, mouseY, partialTicks); // renderable widgets, keep that here
		this.minigame.renderTooltips(guiGraphics, mouseX, mouseY);
	}

	public int getGuiTop() {
		return guiTop;
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		for (var system : System.values()) {
			var message = Component.translatable("wuxiacraft.system." + system.name().toLowerCase());
			int messageWidth = this.font.width(message);
			guiGraphics.drawString(this.font, message, (int) (5 + 63 * system.ordinal() + (63 - messageWidth) / 2f), 9, 0xFFAA00, true);
		}
		if (this.canBreakThrough) {
			var component = Component.translatable("wuxiacraft.gui.breakthrough");
			var width = this.font.width(component);
			guiGraphics.drawString(this.font, component, (int) (100 - width / 2f), 172, 0xFFAA00);
		}
		if (this.canRebirth) {
			var component = Component.translatable("wuxiacraft.gui.rebirth");
			var width = this.font.width(component);
			guiGraphics.drawString(this.font, component, (int) (165 - width / 2f), 172, 0xC644FF);
		}
	}

	public void addChild(AbstractWidget widget) {
		this.addRenderableWidget(widget);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		var superResult = super.mouseClicked(mouseX, mouseY, button);
		var player = Minecraft.getInstance().player;
		if (player == null) return superResult;
		var cultivation = Cultivation.get(player);
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			if (MathUtil.inBounds(mouseX - this.guiLeft, mouseY - this.guiTop, 5 + 63 * system.ordinal(), 6, 63, 18)) {
				if (!systemData.techniqueData.modifier.isValidTechnique()) break;
				var minigame = stageMiniGames.get(systemData.currentStage);
				if (minigame == null) break;
				this.system = system;
				this.canBreakThrough = false;
				this.canRebirth = false;
				this.minigame.close(this);
				var newMinigame = minigame.get();
				newMinigame.init(this);
				this.minigame = newMinigame;
				break;
			}
		}
		if (this.canBreakThrough) {
			if (MathUtil.inBounds(mouseX - this.guiLeft, mouseY - this.guiTop, 69, 170, 63, 14)) {
				var stage = cultivation.getSystemData(this.system).getStage();
				WuxiaPacketHandler.INSTANCE.sendToServer(new StartTribulationMessage(stage.numberOfLightningStrikes, stage.lightningStrength, stage.lightningStrengthGrowth, this.system));
				this.onClose();
				return true;
			}
		}
		if (this.canRebirth) {
			if (MathUtil.inBounds(mouseX - this.guiLeft, mouseY - this.guiTop, 135, 170, 63, 14)) {
				WuxiaPacketHandler.INSTANCE.sendToServer(new AttemptRebirthMessage());
				this.onClose();
				return true;
			}
		}
		if (this.minigame == null) return superResult;
		return superResult || minigame.onMouseClick(mouseX - this.guiLeft, mouseY - this.guiTop, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		var superResult = super.mouseReleased(mouseX - this.guiLeft, mouseY - this.guiTop, button);
		if (this.minigame == null) return superResult;
		return superResult || minigame.onMouseRelease(mouseX, mouseY, button);
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
		for (var child : this.children()) {
			child.mouseMoved(mouseX, mouseY);
		}
		if (this.minigame == null) return;
		minigame.onMouseMove(mouseX - this.guiLeft, mouseY - this.guiTop);
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
		var returnValue = super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
		return returnValue;
	}

	@Override
	public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
		return super.mouseScrolled(pMouseX, pMouseY, pDelta);
	}

	@Override
	public void tick() {
		super.tick();
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		int iR = 0;
		for (System systems : System.values()) {
			var systemData = cultivation.getSystemData(systems);
			var stage = cultivation.getSystemData(systems).getStage();
			if (systemData.currentStage.equals(stage.nextStage) && cultivation.getStat(systems, PlayerSystemStat.CULTIVATION_BASE)
					.compareTo(cultivation.getStat(systems, PlayerSystemStat.MAX_CULTIVATION_BASE)) >= 0) {
				iR++;
				} else break;
		}
		if (iR >=3) {
			int iS = 0;
			var aspectData = cultivation.getAspects();
			List<TechniqueAspect> neededAspects = new LinkedList<TechniqueAspect>(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValues().stream().toList());
			neededAspects.remove(WuxiaTechniqueAspects.UNKNOWN.get());
			neededAspects.remove(WuxiaTechniqueAspects.EMPTY.get());
			neededAspects.remove(WuxiaTechniqueAspects.DEVOURING.get());
			neededAspects.remove(WuxiaTechniqueAspects.CONSUMPTION.get());
			neededAspects.remove(WuxiaTechniqueAspects.GLUTTONY.get());
			neededAspects.remove(WuxiaTechniqueAspects.BEELZEBUB.get());
			neededAspects.remove(WuxiaTechniqueAspects.DIAMOND_CONSTRUCT.get());
			if(cultivation.getRebirths() < 1) {
				neededAspects.remove(WuxiaTechniqueAspects.ASHES_OF_REBIRTH.get());
				neededAspects.remove(WuxiaTechniqueAspects.REKINDLED_SPARK.get());
				neededAspects.remove(WuxiaTechniqueAspects.IGNITION.get());
				neededAspects.remove(WuxiaTechniqueAspects.EMBER_OF_REKINDLING.get());
				neededAspects.remove(WuxiaTechniqueAspects.CINDER_OF_RENEWAL.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPIRITUAL_RECONSTRUCTION.get());
			}
			if(cultivation.getRebirths() < 2) {
				neededAspects.remove(WuxiaTechniqueAspects.INFERNO.get());
				neededAspects.remove(WuxiaTechniqueAspects.RENEWAL.get());
				neededAspects.remove(WuxiaTechniqueAspects.RESURRECTION.get());
				neededAspects.remove(WuxiaTechniqueAspects.FLAME_OF_PURIFICATION.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPARK_OF_AWAKENING.get());
				neededAspects.remove(WuxiaTechniqueAspects.SPIRIT_OF_RESTORATION.get());
			}
			if(cultivation.getRebirths() < 3) {
				neededAspects.remove(WuxiaTechniqueAspects.ASCENT.get());
				neededAspects.remove(WuxiaTechniqueAspects.TRANSCENDENCE.get());
				neededAspects.remove(WuxiaTechniqueAspects.THE_ETERNAL_CYCLE.get());
				neededAspects.remove(WuxiaTechniqueAspects.ESSENCE_OF_REBIRTH.get());
			}
			for (var knownAspectLocation : cultivation.getAspects().getKnownAspects().stream().toList()) {
				var knownAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(knownAspectLocation);
				if (!neededAspects.contains(knownAspect)) continue;
				var currentCheckpoint = knownAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(knownAspectLocation));
				if (currentCheckpoint == knownAspect.checkpoints.getLast()) {
					iS++;
				} 
			}
			if(iS >= neededAspects.size())
			this.canRebirth = true;
		}
		var systemData = cultivation.getSystemData(this.system);
		var stage = systemData.getStage();
		var expectedMinigame = stageMiniGames.get(systemData.currentStage);
		if (expectedMinigame != null) {
			if (!expectedMinigame.get().getClass().isInstance(this.minigame)) {
				var newMinigame = expectedMinigame.get();
				newMinigame.init(this);
				if (this.minigame != null) this.minigame.close(this);
				this.minigame = newMinigame;
			}
		} else {
			this.minigame = null;
		}
		if (this.minigame == null) return;
		this.minigame.tick();
		if(!cultivation.isTribulating())
		if (this.system != System.BODY || (stage.nextStage != null && !systemData.currentStage.equals(stage.nextStage)))
		this.canBreakThrough = cultivation.getStat(system, PlayerSystemStat.CULTIVATION_BASE)
				.compareTo(cultivation.getStat(system, PlayerSystemStat.MAX_CULTIVATION_BASE)) >= 0;
	}

	@Override
	public void onClose() {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var animationState = ClientAnimationState.get(player);
		var cultivation = Cultivation.get(player);
		animationState.setMeditating(false);
		WuxiaPacketHandler.INSTANCE.sendToServer(new BroadcastAnimationChangeRequestMessage(animationState, cultivation.isCombat()));
		if (this.minigame != null) {
			this.minigame.close(this);
		}
		super.onClose();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
