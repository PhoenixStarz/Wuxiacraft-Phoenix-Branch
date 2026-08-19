package com.lazydragonstudios.wuxiacraft.client.gui.minigames;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.gui.MeditateScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaButton;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.DivineCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.DivineCultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.networking.MeditateMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.networking.TeleportToDivineDimensionMessage;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class DivineMinigame implements Minigame {

	private Font font;
	private MeditateScreen medScreen;

	public static final ResourceLocation MEDITATE_SCREEN = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/cultivation_minigame_screen.png");
	private static final ResourceLocation MINIGAME_TEXTURE = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/minigames/mortal_essence_minigame.png");
	private static final float defaultOuterCircleRadius = 60f;
	private static final float innerCircleRadius = 15;

	private final LinkedList<Strand> strands = new LinkedList<>();

	private final LinkedList<Strand> selectedStrands = new LinkedList<>();

	private boolean isGrabbed = false;

	private float outerCircleRadius = defaultOuterCircleRadius;

	private float hoveringCircleRadius = defaultOuterCircleRadius;

	private boolean grabbedCircle = false;

	private boolean canTeleport = false;
	private boolean canTeleportOthers = false;

	//the size is actually the tex coordinates
	private final Rectangle dantian = new Rectangle(96, 104, 60, 5);

	@Override
	public void init(MeditateScreen screen) {
		this.font = screen.getMinecraft().font;
		medScreen = screen;
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var divineData = cultivation.getSystemData(System.DIVINE);
		if (!divineData.techniqueData.modifier.isValidTechnique()) return;
	}

	@Override
	public boolean onMouseClick(double x, double y, int button) {
		if (this.canTeleport) {
			if (MathUtil.inBounds(x, y, 4, 25, 63, 14)) {
				WuxiaPacketHandler.INSTANCE.sendToServer(new TeleportToDivineDimensionMessage(false));
				this.close(medScreen);
				return true;
			}
		}
		if (this.canTeleportOthers) {
			if (MathUtil.inBounds(x, y, 132, 25, 63, 14)) {
				WuxiaPacketHandler.INSTANCE.sendToServer(new TeleportToDivineDimensionMessage(true));
				this.close(medScreen);
				return true;
			}
		}
		if (this.selectedStrands.isEmpty() && isInCircleBorder(x, y, dantian.x + 4, dantian.y + 4, outerCircleRadius, 3f)) {
			this.grabbedCircle = true;
			return true;
		} else 
		this.isGrabbed = true;
		return false;
	}

	@Override
	public boolean onMouseRelease(double x, double y, int button) {
		this.isGrabbed = false;
		if (this.selectedStrands.size() > 0) {
			this.selectedStrands.clear();
		}
		if (this.grabbedCircle) {
			this.grabbedCircle = false;
			this.outerCircleRadius = this.hoveringCircleRadius;
		}
		return false;
	}

	@Override
	public void onMouseMove(double x, double y) {
		for (var strand : this.selectedStrands) {
			strand.x = x;
			strand.y = y;
		}
		if (isGrabbed) {
			for (var strand : this.strands) {
				if (strand.inBounds(x, y) && !this.selectedStrands.contains(strand)) {
					this.selectedStrands.add(strand);
				}
			}
		}
		else if (this.grabbedCircle) {
			var dx = x - dantian.x;
			var dy = y - dantian.y;
			hoveringCircleRadius = (float) Math.min(outerCircleRadius, Math.sqrt(dx * dx + dy * dy));
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var divineData = cultivation.getSystemData(System.DIVINE);
		if (!divineData.techniqueData.modifier.isValidTechnique()) return;
		RenderSystem.setShaderTexture(0, MINIGAME_TEXTURE);
		int imageX = 70;
		int imageY = 66;
		guiGraphics.blit(MINIGAME_TEXTURE, imageX, imageY, 60, 60, 0, 0, 60, 60, 256, 256); //the person cross-legged
		//fill = 60 * cult_base/max_cult_base
		int barFill = BigDecimal.valueOf(60).multiply(
						cultivation.getStat(System.DIVINE, PlayerSystemStat.CULTIVATION_BASE)
								.divide(cultivation.getStat(System.DIVINE, PlayerSystemStat.MAX_CULTIVATION_BASE), RoundingMode.HALF_UP))
				.min(BigDecimal.valueOf(60)).intValue();
		guiGraphics.blit(MINIGAME_TEXTURE, imageX, imageY + 60 - barFill, 60, barFill, 0, 60 + 60 - barFill, 60, barFill, 256, 256); //the person cross-legged fill
		guiGraphics.blit(MINIGAME_TEXTURE, dantian.x, dantian.y, 8, 8, dantian.width, dantian.height, 8, 8, 256, 256);
		drawCircle(guiGraphics, dantian.x + 4, dantian.y + 4, outerCircleRadius, 0xFFEEEE00);
		drawCircle(guiGraphics, dantian.x + 4, dantian.y + 4, innerCircleRadius, 0xFFAA1010);
		if (grabbedCircle) {
			drawCircle(guiGraphics, dantian.x + 4, dantian.y + 4, hoveringCircleRadius, 0xFF10FA10);
		}
		for (var strand : this.strands) {
			strand.render(guiGraphics);
		}
		if (this.canTeleport) {
			guiGraphics.blit(MEDITATE_SCREEN, 4, 25, 0, 170, 63, 14);
		}
		if (this.canTeleportOthers) {
			guiGraphics.blit(MEDITATE_SCREEN, 132, 25, 0, 170, 63, 14);
		}
		renderLabels(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {

	}

	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (this.canTeleport) {
			var component = Component.translatable("wuxiacraft.gui.teleport");
			int width = this.font.width(component);
			guiGraphics.drawString(this.font, component, (int) (35 - width / 2f), 28, 0x58AB6B);
		}
		if (this.canTeleportOthers) {
			var component = Component.translatable("wuxiacraft.gui.teleport_others");
			int width = this.font.width(component);
			guiGraphics.drawString(this.font, component, (int) (163 - width / 2f), 28, 0x58AB6B);
		}
	}

	@Override
	public void tick() {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var divineData = cultivation.getSystemData(System.DIVINE);
		var divineStage = (DivineCultivationStage) divineData.getStage();
		this.canTeleport = false;
		var strandCount = divineData.hasEnergy(cultivation.getStat(System.DIVINE, PlayerSystemStat.MAX_ENERGY).divide(new BigDecimal(4))) ? 5 : 0;
		this.keepCorrectStrandCount(strandCount);
		var markedToRemove = new LinkedList<Strand>();
		for (var strand : this.strands) {
			strand.setGrabbed(this.selectedStrands.contains(strand));
			strand.tick(this.outerCircleRadius);
			if (this.grabbedCircle) {
				if (isOutsideCircle(strand.x, strand.y, dantian.x + 4, dantian.y + 4, hoveringCircleRadius)) {
					WuxiaPacketHandler.INSTANCE.sendToServer(new MeditateMessage(System.DIVINE, false));
					divineData.getStage().cultivationFailure(player);
					markedToRemove.addAll(this.strands);
					this.outerCircleRadius = defaultOuterCircleRadius;
					this.hoveringCircleRadius = defaultOuterCircleRadius;
					this.grabbedCircle = false;
					break;
				}
			}
		}
		if (this.outerCircleRadius < innerCircleRadius) {
			this.outerCircleRadius = defaultOuterCircleRadius;
			WuxiaPacketHandler.INSTANCE.sendToServer(new MeditateMessage(System.DIVINE, true));
			divineData.getStage().cultivate(player);
			markedToRemove.addAll(this.strands);
		}
		for (var toRemove : markedToRemove) {
			this.strands.remove(toRemove);
		}
		if (divineStage.getDimensionSize() > 0)
		this.canTeleport = true;
		this.canTeleportOthers = divineStage.isCanHaveExtraEntities();
	}

	public void keepCorrectStrandCount(int count) {
		if (this.strands.size() < count) {
			for (int remaining = this.strands.size(); remaining < count; remaining++) {
				this.strands.add(new Strand());
			}
		} else if (this.strands.size() > count) {
			for (int overflow = this.strands.size(); overflow > count; overflow--) {
				this.strands.remove(this.strands.size() - 1);
			}
		}
	}

	private static void drawCircle(GuiGraphics guiGraphics, float x, float y, float radius, int color) {
		var prevShader = RenderSystem.getShader();
		var tesselator = Tesselator.getInstance();
		var buffer = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
		RenderSystem.disableCull();
		RenderSystem.setShaderTexture(0, WuxiaButton.WHITE); // to resolve issue with disabling textures
		int guiScale = (int) (Minecraft.getInstance().getWindow().getGuiScale());
		RenderSystem.lineWidth(guiScale * 3f);
		buffer.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR_NORMAL);
		int circleSections = 64;
		for (int i = 0; i <= circleSections; i++) {
			var angle = 2 * Math.PI * i / circleSections;
			buffer.vertex(guiGraphics.pose().last().pose(),
							x + radius * (float) Math.cos(angle),
							y + radius * (float) Math.sin(angle),
							0f)
					.color(color)
					.normal((float) Math.cos(angle-Math.PI/2), (float) Math.sin(angle-Math.PI/2), 0f)
					.endVertex();
		}
		tesselator.end();
		RenderSystem.enableCull();
		RenderSystem.setShader(() -> prevShader);
	}

	private static boolean isInCircleBorder(double x, double y, double circleX, double circleY, double circleRadius, double circleBorder) {
		var dx = x - circleX;
		var dy = y - circleY;
		var dRadius = Math.sqrt(dx * dx + dy * dy);
		return dRadius >= circleRadius - circleBorder / 2 && dRadius <= circleRadius + circleBorder / 2;
	}

	private static boolean isOutsideCircle(double x, double y, double circleX, double circleY, double circleRadius) {
		var dx = x - circleX;
		var dy = y - circleY;
		var dRadius = Math.sqrt(dx * dx + dy * dy);
		return dRadius > circleRadius;
	}

	@Override
	public void close(MeditateScreen screen) {
		Minigame.super.close(screen);
		screen.clearChildren();
	}

	private static class Strand {
		private static final int CENTER_X = 100;
		private static final int CENTER_Y = 108;
		private static final int MAX_RADIUS = 50;
		private double x;
		private double y;
		private double movX;
		private double movY;
		private boolean grabbed;

		Strand() {
			var v2 = new Vec2((float) Math.random() * 2f - 1f, (float) Math.random() * 2f - 1f);
			v2 = v2.normalized();
			v2 = v2.scale(MAX_RADIUS);
			this.x = CENTER_X + v2.x;
			this.y = CENTER_Y + v2.y;
			this.movX = (-0.5 + Math.random())*0.1;
			this.movY = (-0.5 + Math.random())*0.1;
		}

		void tick(double outerCircleRadius) {
			if (this.grabbed) return;
			this.x += movX;
			this.y += movY;
			if (DivineMinigame.isOutsideCircle(this.x, this.y, CENTER_X, CENTER_Y, outerCircleRadius)) {
				this.movX *= -1;
				this.movY *= -1;
			}
		}

		boolean inBounds(double mouseX, double mouseY) {
			return MathUtil.inBounds(mouseX, mouseY, this.x - 3, this.y - 3, 5, 5);
		}

		void render(GuiGraphics guiGraphics) {
			guiGraphics.pose().pushPose();
			guiGraphics.pose().translate(this.x, this.y, 0);
			guiGraphics.blit(MINIGAME_TEXTURE, -3, -3, 5, 5, 60, 0, 5, 5, 256, 256);
			guiGraphics.pose().popPose();
		}

		public void setGrabbed(boolean grabbed) {
			this.grabbed = grabbed;
		}

	}

}
