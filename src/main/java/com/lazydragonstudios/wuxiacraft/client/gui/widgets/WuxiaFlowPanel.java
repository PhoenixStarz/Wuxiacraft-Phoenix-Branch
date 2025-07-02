package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class WuxiaFlowPanel extends WuxiaScrollPanel {

	/**
	 * this is the margin between items and the borders as well
	 */
	public int margin = 10;

	/**
	 * this holds the height of each line, and we get the height by querying the line top position (y)
	 */
	private HashMap<Integer, Integer> lineTopAndHeight = new HashMap<>();

	public WuxiaFlowPanel(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	@Override
	public void addChild(@Nonnull AbstractWidget child) {
		this.children.add(child);
		this.rearrangeItems();
	}

	@Override
	public void setHeight(int value) {
		super.setHeight(value);
		this.rearrangeItems();
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		this.rearrangeItems();
		;
	}

	/**
	 * rearranges items in lines and makes items top align in the line
	 */
	protected void rearrangeItems() {
		int currentLeftPos = 0;
		int currentTopPos = margin;
		int currentLineHeight = 0;
		this.contentWidth = 0;
		for (var widget : this.children) {
			if (currentLeftPos + widget.getWidth() > this.width - this.scrollBarWidth) {
				currentLeftPos = 0;
				currentTopPos += currentLineHeight + margin;
			}
			widget.setX(currentLeftPos + margin);
			widget.setY(currentTopPos);
			currentLeftPos = widget.getX() + widget.getWidth();
			currentLineHeight = Math.max(currentLineHeight, widget.getHeight());
			this.contentWidth = Math.max(this.contentWidth, widget.getX() + widget.getWidth());
			this.contentHeight = Math.max(this.contentHeight, widget.getY() + widget.getHeight());
		}
		this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + this.contentWidth > this.width ? scrollBarWidth : 0);
		this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + this.contentHeight > this.height ? scrollBarHeight : 0);
	}
}
