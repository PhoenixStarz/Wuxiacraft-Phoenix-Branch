package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class WuxiaTexturedButton extends WuxiaButton {

	private ResourceLocation[] textureLocation;

	/**
	 * x, y, -> uv in texture
	 * width, height -> max u, max v
	 */
	private Rectangle[] tex;

	public WuxiaTexturedButton(int x, int y, int width, int height, Component title, Runnable onClicked, ResourceLocation[] textureLocation, Rectangle[] tex) {
		super(x, y, width, height, title, onClicked);
		this.textureLocation = textureLocation;
		this.tex = tex;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		if (textureLocation.length != tex.length) return;
		for (int i = 0; i < textureLocation.length; i++) {
			var texLocation = textureLocation[i];
			var tex = this.tex[i];
			RenderSystem.enableBlend();
			guiGraphics.blit(texLocation, this.getX(), this.getY(), this.width, this.height, tex.x, tex.y, this.width, this.height, tex.width, tex.height);
		}
		var font = Minecraft.getInstance().font;
		//GuiComponent.drawCenteredString(poseStack, font, this.getMessage(), this.getX() + this.width / 2+1, this.getY() + (this.height - font.lineHeight) / 2+1, 0x101010);
		guiGraphics.drawCenteredString(font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - font.lineHeight) / 2, 0xFFFFFF);
	}

	public void setTex(Rectangle[] tex) {
		this.tex = tex;
	}
}
