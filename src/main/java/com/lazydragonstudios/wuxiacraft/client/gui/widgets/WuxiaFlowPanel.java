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
	}

	/**
	 * rearranges items in lines and makes items top align in the line
	 */
	protected void rearrangeItems() {
		int availableWidth = this.width;
		int currentLeftPos = margin;
		int currentTopPos = margin;
		int currentLineHeight = 0;
		this.contentWidth = 0;
		this.contentHeight = 0;
		layoutItems(availableWidth);
		boolean needsVerticalScrollBar = this.contentHeight > this.height;

		if (needsVerticalScrollBar) {
			availableWidth = Math.max(0, this.width - this.scrollBarWidth);
			this.contentWidth = 0;
			this.contentHeight = 0;
			layoutItems(availableWidth);
		}

		boolean needsHorizontalScrollBar = this.contentWidth > this.width;
		this.totalScrollWidth = Math.max(0,this.contentWidth - this.width);
		this.totalScrollHeight = Math.max(0,this.contentHeight - this.height);
	}

	private void layoutItems(int availableWidth) {
		int currentLeftPos = margin;
		int currentTopPos = margin;
		int currentLineHeight = 0;

		for (AbstractWidget widget : this.children) {
			if (currentLeftPos + widget.getWidth() > availableWidth - margin) {
				currentLeftPos = margin;
				currentTopPos += currentLineHeight + margin;
				currentLineHeight = 0;
			}
			widget.setX(currentLeftPos);
			widget.setY(currentTopPos);
			currentLeftPos += widget.getWidth() + margin;
			currentLineHeight = Math.max(currentLineHeight, widget.getHeight());
			this.contentWidth = Math.max(this.contentWidth, widget.getX() + widget.getWidth() + margin);
			this.contentHeight = Math.max(this.contentHeight, widget.getY() + widget.getHeight() + margin);
		}
	}

}
