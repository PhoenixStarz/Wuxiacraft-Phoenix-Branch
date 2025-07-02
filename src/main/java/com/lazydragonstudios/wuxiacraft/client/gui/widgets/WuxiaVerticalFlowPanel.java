package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import net.minecraft.network.chat.Component;

public class WuxiaVerticalFlowPanel extends WuxiaFlowPanel {
	public WuxiaVerticalFlowPanel(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	/**
	 * rearranges one item per line no questions asked
	 */
	@Override
	public void rearrangeItems() {
		int currentLeftPos = margin;
		int currentTopPos = margin;
		int currentLineHeight = 0;
		this.contentWidth = 0;
		for (var widget : this.children) {
			widget.setX(currentLeftPos);
			widget.setY(currentTopPos);
			currentLineHeight = widget.getHeight();
			currentTopPos += currentLineHeight + margin;
			this.contentWidth = Math.max(this.contentWidth, widget.getX() + widget.getWidth());
			this.contentHeight = Math.max(this.contentHeight, widget.getY() + widget.getHeight());
		}
		this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + (this.contentWidth > this.width ? scrollBarWidth : 0));
		this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + (this.contentHeight > this.height ? scrollBarHeight : 0));
	}
}
