package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class WuxiaTextField extends AbstractWidget {
	public EditBox editBox;

	public WuxiaTextField(int x, int y, int width, int height) {
		super(x, y, width, height, Component.empty());
		this.editBox = new EditBox(Minecraft.getInstance().font, x + 5, y + 5, width - 10, height - 10, Component.empty());
		this.editBox.setBordered(false);
	}


	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		int texPosX = 30;
		int texPosY = 0;
		int nineSliceHeight = 5;
		int nineSliceWidth = 5;
		if (this.editBox.isFocused()) {
			texPosX = 45;
		}
		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, WuxiaButton.UI_CONTROLS);
		//corners first
		//top left
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX(), this.getY(), //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX, texPosY, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//top right
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth, this.getY(), //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth * 2, texPosY, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom left
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX(), this.getY() + this.height - nineSliceHeight, //position in screen
				nineSliceWidth, nineSliceHeight, // size in screen
				texPosX, texPosY + nineSliceHeight * 2, // position in texture
				nineSliceWidth, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom right
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
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
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					xPos, this.getY(), //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, this.getY(), //position in screen
				horizontalRemainingFillSpace, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth, texPosY, // position in texture
				horizontalRemainingFillSpace, nineSliceHeight, //size in texture
				256, 256); //image size
		//bottom
		for (int i = 0; i < horizontalFillSteps; i++) {
			int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					xPos, this.getY() + this.height - nineSliceHeight, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight * 2, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, this.getY() + this.height - nineSliceHeight, //position in screen
				horizontalRemainingFillSpace, nineSliceHeight, // size in screen
				texPosX + nineSliceWidth, texPosY + nineSliceHeight * 2, // position in texture
				horizontalRemainingFillSpace, nineSliceHeight, //size in texture
				256, 256); //image size
		//left
		for (int i = 0; i < verticalFillSteps; i++) {
			int yPos = this.getY() + nineSliceHeight + i * nineSliceHeight;
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX(), yPos, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX(), this.getY() + this.height - nineSliceHeight - verticalRemainingFillSpace, //position in screen
				nineSliceWidth, verticalRemainingFillSpace, // size in screen
				texPosX, texPosY + nineSliceHeight, // position in texture
				nineSliceWidth, verticalRemainingFillSpace, //size in texture
				256, 256); //image size
		//right
		for (int i = 0; i < verticalFillSteps; i++) {
			int yPos = this.getY() + nineSliceHeight + i * nineSliceHeight;
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + this.width - nineSliceWidth, yPos, //position in screen
					nineSliceWidth, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth * 2, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
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
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						xPos, yPos, //position in screen
						nineSliceWidth, nineSliceHeight, // size in screen
						texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
						nineSliceWidth, nineSliceHeight, //size in texture
						256, 256); //image size
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, yPos, //position in screen
					horizontalRemainingFillSpace, nineSliceHeight, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
					horizontalRemainingFillSpace, nineSliceHeight, //size in texture
					256, 256); //image size
		}
		int yPos = this.getY() + this.height - nineSliceHeight - verticalRemainingFillSpace;
		for (int i = 0; i < horizontalFillSteps; i++) {
			int xPos = this.getX() + nineSliceWidth + i * nineSliceWidth;
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					xPos, yPos, //position in screen
					nineSliceWidth, verticalRemainingFillSpace, // size in screen
					texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
					nineSliceWidth, verticalRemainingFillSpace, //size in texture
					256, 256); //image size
		}
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + this.width - nineSliceWidth - horizontalRemainingFillSpace, yPos, //position in screen
				horizontalRemainingFillSpace, verticalRemainingFillSpace, // size in screen
				texPosX + nineSliceWidth, texPosY + nineSliceHeight, // position in texture
				horizontalRemainingFillSpace, verticalRemainingFillSpace, //size in texture
				256, 256); //image size
		RenderSystem.disableBlend();
		this.editBox.render(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void setX(int pX) {
		super.setX(pX);
		this.editBox.setX(pX + 6);
	}

	@Override
	public void setY(int pY) {
		super.setY(pY);
		this.editBox.setY(pY + 6);
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY(), this.width, this.height)) {
			this.editBox.setFocused(true);
			if (MathUtil.inBounds(mouseX, mouseY, this.editBox.getX(), this.editBox.getY(), this.editBox.getWidth(), this.editBox.getHeight())) {
				return this.editBox.mouseClicked(mouseX, mouseY, button);
			} else {
				return this.editBox.mouseClicked(this.getX() + 6, this.getY() + 6, button);
			}
		} else {
			this.editBox.setFocused(false);
			return this.editBox.mouseClicked(this.getX(), this.getY(), button);
		}
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.editBox.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char character, int keyCode) {
		return this.editBox.charTyped(character, keyCode);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

	}
}
