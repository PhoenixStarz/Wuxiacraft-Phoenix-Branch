package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.LinkedList;

@ParametersAreNonnullByDefault
public class WuxiaLabelBox extends AbstractWidget {

	private LinkedList<LinkedList<String>> words;

	public WuxiaLabelBox(int x, int y, int width, Component message) {
		super(x, y, width, 10, message);
		setMessage(message);
	}

	@Override
	public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_) {
		return false;
	}

	@Override
	public void setMessage(Component p_93667_) {
		String messageString = this.getMessage().getString();
		LinkedList<String> paragraphs = new LinkedList<>(Arrays.stream(messageString.split("\n")).toList());
		words = new LinkedList<>();
		for (var p : paragraphs) {
			var pWords = new LinkedList<>(Arrays.stream(p.split(" ")).toList());
			words.add(pWords);
		}
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		var font = Minecraft.getInstance().font;
		var spaceWidth = font.width(" ");
		var currentTopPos = this.getY() - font.lineHeight - 1;
		for (var paragraph : words) {
			currentTopPos += font.lineHeight + 1;
			var currentLeftPos = 0;
			for (var word : paragraph) {
				int wordWidth = font.width(word);
				if (currentLeftPos != 0 && currentLeftPos + wordWidth > this.width) {
					currentTopPos += font.lineHeight + 1;
					currentLeftPos = 0;
				}
				guiGraphics.drawString(Minecraft.getInstance().font, word, this.getX() + currentLeftPos, currentTopPos, 0xFFAA00);
				currentLeftPos += wordWidth + spaceWidth;
			}
		}
		//just so we can get a hold of the current height of this, perhaps for the future make something that depends on this
		this.setHeight(currentTopPos + font.lineHeight);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}
}
