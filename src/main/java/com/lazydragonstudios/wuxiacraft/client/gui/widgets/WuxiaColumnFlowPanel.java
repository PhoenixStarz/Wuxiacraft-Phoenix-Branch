package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import net.minecraft.network.chat.Component;

public class WuxiaColumnFlowPanel extends WuxiaFlowPanel {
	public WuxiaColumnFlowPanel(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	/**
	 * rearranges items in columns, adding new columns as items reach bottom
	 */
	@Override
	public void rearrangeItems() {
		int currentLeftPos = margin;
		int currentTopPos = margin;
		int currentLineHeight = 0;
		int currentColumnWidth = 0;
		this.contentWidth = 0;
		for (var widget : this.children) {
			if(currentTopPos + widget.getHeight() > this.height - this.scrollBarHeight) {
				currentLeftPos += currentColumnWidth+margin;
				currentColumnWidth = 0;
				currentTopPos = margin;
			}
			widget.setX(currentLeftPos);
			widget.setY(currentTopPos);
			currentLineHeight = widget.getHeight();
			currentTopPos += currentLineHeight + margin;
			currentColumnWidth = Math.max(currentColumnWidth, widget.getWidth());
			this.contentWidth = Math.max(this.contentWidth, widget.getX() + widget.getWidth());
			this.contentHeight = Math.max(this.contentHeight, widget.getY() + widget.getHeight());
		}
		this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + this.contentWidth > this.width ? scrollBarWidth : 0);
		this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + this.contentHeight > this.height ? scrollBarHeight : 0);
	}
}
