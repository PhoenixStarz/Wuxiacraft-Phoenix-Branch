package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WuxiaButton extends AbstractButton {

	public static final ResourceLocation UI_CONTROLS = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/ui_controls.png");

	public static final ResourceLocation WHITE = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/white.png");

	public Runnable onClicked;

	public WuxiaButton(int x, int y, int width, int height, Component title, Runnable onClicked) {
		super(x, y, width, height, title);
		this.onClicked = onClicked;
		this.setColor(1f, 1f, 1f);
	}

	private float red, green, blue;

	public void setColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		int texPosX = 0;
		int texPosY = 0;
		int nineSliceHeight = 5;
		int nineSliceWidth = 5;
		if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY(), this.width, this.height)) {
			texPosX = 15;
		}
		guiGraphics.setColor(this.red, this.green, this.blue, 1f);
		//corners first
		//top left
		guiGraphics.blit(UI_CONTROLS,
				this.getX(), this.getY(), //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX, texPosY, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//top right
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth, this.getY(), //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth * 2, texPosY, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom left
		guiGraphics.blit(UI_CONTROLS,
				this.getX(), this.getY() + this.height - nineSliceHeight, //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX, texPosY + nineSliceHeight * 2, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom right
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth, this.getY() + this.height - nineSliceHeight, //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth * 2, texPosY + nineSliceHeight * 2, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size

		//fillings
		int remainingFillWidth = this.width - nineSliceWidth * 2;
		int remainingFillHeight = this.height - nineSliceHeight * 2;
		int horizontalFillSteps = remainingFillWidth / nineSliceWidth;
		int horizontalRemainingFillSpace = remainingFillWidth % nineSliceWidth;
		int verticalFillSteps = remainingFillHeight / nineSliceHeight;
		int verticalRemainingFillSpace = remainingFillHeight % nineSliceHeight;
		//borders
		//top
		for (int i = 0; i < horizontalFillSteps; i++) {
			int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
			guiGraphics.blit(UI_CONTROLS,
					xPos, this.getY(), //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, this.getY(), //position in screen
				horizontalRemainingFillSpace, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth, texPosY, // position in texture
				horizontalRemainingFillSpace, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom
		for (int i = 0; i < horizontalFillSteps; i++) {
			int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
			guiGraphics.blit(UI_CONTROLS,
					xPos, this.getY() + this.height - nineSliceHeight, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight * 2, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, this.getY() + this.height - nineSliceHeight, //position in screen
				horizontalRemainingFillSpace, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth, texPosY + nineSliceHeight * 2, // position in texture
				horizontalRemainingFillSpace, nineSliceHeight, //size in texture
				256, 256); //image size
		//left
		for (int i = 0; i < verticalFillSteps; i++) {
			int yPos = this.getY() + nineSliceHeight + i * nineSliceHeight;
			guiGraphics.blit(UI_CONTROLS,
					this.getX(), yPos, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(UI_CONTROLS,
				this.getX(), this.getY() + this.height - nineSliceHeight - verticalRemainingFillSpace, //position in screen
				nineSliceWidth, verticalRemainingFillSpace, // size in screen
				texPosX, texPosY + nineSliceHeight, // position in texture
				nineSliceWidth, verticalRemainingFillSpace, //size in texture
				256, 256); //image size
		//right
		for (int i = 0; i < verticalFillSteps; i++) {
			int yPos = this.getY() + nineSliceHeight + i * nineSliceHeight;
			guiGraphics.blit(UI_CONTROLS,
					this.getX() + this.width - nineSliceWidth, yPos, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth * 2, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth, this.getY() + this.height - nineSliceHeight - verticalRemainingFillSpace, //position in screen
				nineSliceWidth, verticalRemainingFillSpace, // size in screen
				texPosX + nineSliceWidth * 2, texPosY + nineSliceHeight, // position in texture
				nineSliceWidth, verticalRemainingFillSpace, //size in texture
				256, 256); //image size

		//middle
		for (int j = 0; j < verticalFillSteps; j++) {
			int yPos = this.getY() + nineSliceHeight + j * nineSliceHeight;
			for (int i = 0; i < horizontalFillSteps; i++) {
				int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
				guiGraphics.blit(UI_CONTROLS,
						xPos, yPos, //position in screen
						nineSliceWidth, nineSliceHeight, // size in screen
						texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
						nineSliceWidth, nineSliceHeight, //size in texture
						256, 256); //image size
			}
			guiGraphics.blit(UI_CONTROLS,
					this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, yPos, //position in screen
					horizontalRemainingFillSpace, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
					horizontalRemainingFillSpace, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		int yPos = this.getY() + this.height - nineSliceHeight - verticalRemainingFillSpace;
		for (int i = 0; i < horizontalFillSteps; i++) {
			int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
			guiGraphics.blit(UI_CONTROLS,
					xPos, yPos, //position in screen
					nineSliceWidth, verticalRemainingFillSpace, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, verticalRemainingFillSpace, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, yPos, //position in screen
				horizontalRemainingFillSpace, verticalRemainingFillSpace, // size in screen
				texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
				horizontalRemainingFillSpace, verticalRemainingFillSpace, //size in texture
				256, 256); //image size

		guiGraphics.setColor(1f, 1f, 1f, 1f);

		var font = Minecraft.getInstance().font;
		guiGraphics.drawCenteredString(font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - font.lineHeight) / 2, 0xFFFFFF);
	}

	@Override
	public void onPress() {
		this.onClicked.run();
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

	}
}
