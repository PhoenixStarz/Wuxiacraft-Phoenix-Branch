package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WuxiaAspectWidget extends AbstractWidget {

	public ResourceLocation aspect;

	private BiConsumer<Double, Double> onClicked;

	private BiConsumer<Double, Double> onDragged;

	private BiConsumer<Double, Double> onRelease;

	public WuxiaAspectWidget(int x, int y, ResourceLocation aspect) {
		super(x, y, 32, 32, Component.nullToEmpty(aspect.getPath()));
		this.aspect = aspect;
		onClicked = (mx, my) -> {
		};
		onDragged = (mx, my) -> {
		};
		onRelease = (mx, my) -> {
		};
	}

	@Override
	public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.enableBlend();
		guiGraphics.blit(this.getTextureLocation(),
				this.getX(), this.getY(),
				32, 32,
				0, 0,
				32, 32,
				32, 32
		);
		RenderSystem.disableScissor();
		if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getWidth())) {
			this.renderToolTip(guiGraphics, mouseX, mouseY);
		}
		RenderSystem.disableBlend();
		GlStateManager._enableScissorTest();
	}

	public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (!MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY(), this.width, this.height)) return;
		var techAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(this.aspect);
		var skillAspect = WuxiaRegistries.SKILL_ASPECT.get().getValue(this.aspect);
		if (techAspect != null) techAspect.renderTooltip(guiGraphics, mouseX, mouseY);
		if (skillAspect != null) skillAspect.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	public ResourceLocation getTextureLocation() {
		var techAspect = this.getTechniqueAspect();
		if (techAspect != null) {
			return techAspect.getTextureLocation();
		}
		var skillAspect = this.getSkillAspectType();
		if (skillAspect != null) {
			return new ResourceLocation(this.aspect.getNamespace(), "textures/skills/" + this.aspect.getPath() + ".png");
		}
		return new ResourceLocation(WuxiaCraft.MOD_ID, "textures/aspects/empty.png");
	}

	@Nullable
	public TechniqueAspect getTechniqueAspect() {
		return WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(this.aspect);
	}

	@Nullable
	public SkillAspectType getSkillAspectType() {
		return WuxiaRegistries.SKILL_ASPECT.get().getValue(this.aspect);
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		this.onClicked.accept(mouseX, mouseY);
	}

	@Override
	public void onRelease(double mouseX, double mouseY) {
		this.onRelease.accept(mouseX, mouseY);
	}

	@Override
	protected void onDrag(double mouseX, double mouseY, double mouseDeltaX, double mouseDeltaY) {
		this.onDragged.accept(mouseX, mouseY);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}

	public void setOnClicked(BiConsumer<Double, Double> onClicked) {
		this.onClicked = onClicked;
	}

	public void setOnDragged(BiConsumer<Double, Double> onDragged) {
		this.onDragged = onDragged;
	}

	public void setOnRelease(BiConsumer<Double, Double> onRelease) {
		this.onRelease = onRelease;
	}
}
