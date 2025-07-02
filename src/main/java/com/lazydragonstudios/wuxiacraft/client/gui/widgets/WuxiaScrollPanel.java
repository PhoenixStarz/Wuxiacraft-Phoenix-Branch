package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.joml.Vector3f;
import org.joml.Vector4f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;

//TODO probably add compatibility to negative space widgets

/**
 * A basic scroll panel that has basic scrolling controls
 * This does not support negative values yet, so I'll discard every widget on negative space
 */
@ParametersAreNonnullByDefault
public class WuxiaScrollPanel extends AbstractWidget {

	public enum OverflowType {
		AUTO,
		HIDDEN,
		SCROLL_X,
		SCROLL_Y,
		SCROLL
	}

	protected LinkedList<AbstractWidget> children = new LinkedList<>();

	protected int scrollBarHeight = 10;

	protected int scrollBarWidth = 10;

	/**
	 * this is planned for negative space widgets
	 * can't figure out the logic for widget's content width yet for negative space
	 */
	protected int contentStartingX = 0;

	/**
	 * same as above but vertical
	 */
	protected int contentStartingY = 0;

	/**
	 * Starting from 0
	 * If content does not start from 0 that might be intentional
	 */
	protected int contentWidth = 0;

	/**
	 * Starting from 0
	 * If content does not start from 0 that might be intentional
	 */
	protected int contentHeight = 0;

	/**
	 * this is how much we can scroll the scroll bar
	 * probably this goes from 0 to (this.contentWidth-this.width)
	 */
	protected int totalScrollWidth = 0;

	/**
	 * this is how much we can scroll the scroll bar
	 * probably this goes from 0 to (this.contentHeight-this.height)
	 */
	protected int totalScrollHeight = 0;

	/**
	 * this should be pretty obvious
	 */
	public int currentScrollY = 0;

	/**
	 * this as well
	 */
	public int currentScrollX = 0;

	public OverflowType overflow = OverflowType.AUTO;

	public WuxiaScrollPanel(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	public OverflowType getOverflow() {
		return overflow;
	}

	public void setOverflow(OverflowType overflow) {
		this.overflow = overflow;
	}

	/**
	 * Renders all children widgets.
	 * Using OpenGl Scissor Test, much better than using Stencil Test
	 * Using guiGraphics translate to move content
	 *
	 * @param guiGraphics  the rendering pose stack
	 * @param mouseX       the current mouse X
	 * @param mouseY       the current mouse Y
	 * @param partialTicks the partial tick
	 */
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;

		Vector3f windowCoordinatesInit = getWindowCoordinates(this.getX(), this.getY());
		Vector3f windowCoordinatesEnd = getWindowCoordinates(this.getX() + innerWidth, this.getY() + innerHeight);
		Vector3f windowCoordinatesSize = new Vector3f(windowCoordinatesEnd.x() - windowCoordinatesInit.x(), windowCoordinatesInit.y() - windowCoordinatesEnd.y(), 0f);

		RenderSystem.enableScissor((int) windowCoordinatesInit.x(), (int) windowCoordinatesEnd.y(), (int) windowCoordinatesSize.x(), (int) windowCoordinatesSize.y());
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(this.getX() - this.currentScrollX, this.getY() - this.currentScrollY, 0);
		for (var widget : this.children) {
			widget.render(guiGraphics, mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(), partialTicks);
		}
		guiGraphics.pose().popPose();
		RenderSystem.disableScissor();
		RenderSystem.enableBlend();
		if (isShowingHorizontalScroll()) {
			//bg bar
			int horizontalBarRenderSteps = innerWidth / scrollBarWidth;
			int horizontalBarRemainderFill = innerWidth % scrollBarWidth;
			for (int i = 0; i < horizontalBarRenderSteps; i++) {
				int xPos = this.getX() + i * scrollBarWidth;
				//scroll track bg
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						xPos, this.getY() + innerHeight, //in screen position
						10, 10, //in screen width
						80, 15, //tex position
						10, 10, //texture width
						256, 256 //image size
				);
				//scroll track bg
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						this.getX() + innerWidth - horizontalBarRemainderFill, this.getY() + innerHeight, //in screen position
						horizontalBarRemainderFill, 10, //in screen width
						80, 15, //tex position
						horizontalBarRemainderFill, 10, //texture width
						256, 256 //image size
				);
			}
			//button left
			int texX = 0;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				texX = 10;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX(), this.getY() + innerHeight, //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//arrow left
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX(), this.getY() + innerHeight, //in screen position
					10, 10, //in screen width
					50, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//button right
			texX = 0;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth - scrollBarWidth, this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				texX = 10;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth - scrollBarWidth, this.getY() + innerHeight, //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//arrow right
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth - scrollBarWidth, this.getY() + innerHeight, //in screen position
					10, 10, //in screen width
					30, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//scroll indicator
			int indPos = getHorizontalScrollIndicatorPosition();
			texX = 60;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + scrollBarWidth + indPos, this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				texX = 70;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + scrollBarWidth + indPos, this.getY() + innerHeight, //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
		}
		if (isShowingVerticalScroll()) {
			//bg bar
			int verticalBarRenderSteps = innerHeight / scrollBarHeight;
			int verticalBarRemainderFill = innerHeight % scrollBarHeight;
			for (int i = 0; i < verticalBarRenderSteps; i++) {
				int yPos = this.getY() + i * scrollBarHeight;
				//scroll track bg
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						this.getX() + innerWidth, yPos, //in screen position
						10, 10, //in screen width
						90, 15, //tex position
						10, 10, //texture width
						256, 256 //image size
				);
				//scroll track bg
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						this.getX() + innerWidth, this.getY() + innerHeight - verticalBarRemainderFill, //in screen position
						10, verticalBarRemainderFill, //in screen width
						90, 15, //tex position
						10, verticalBarRemainderFill, //texture width
						256, 256 //image size
				);
			}
			//button top
			int texX = 0;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY(), scrollBarWidth, scrollBarHeight)) {
				texX = 10;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth, this.getY(), //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//arrow top
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth, this.getY(), //in screen position
					10, 10, //in screen width
					20, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//button bottom
			texX = 0;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY() + innerHeight - scrollBarHeight, scrollBarWidth, scrollBarHeight)) {
				texX = 10;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth, this.getY() + innerHeight - scrollBarHeight, //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//arrow bottom
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth, this.getY() + innerHeight - scrollBarHeight, //in screen position
					10, 10, //in screen width
					40, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
			//scroll indicator
			int indPos = getVerticalScrollIndicatorPosition();
			texX = 60;
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY() + scrollBarHeight + indPos, scrollBarWidth, scrollBarHeight)) {
				texX = 70;
			}
			guiGraphics.blit(WuxiaButton.UI_CONTROLS,
					this.getX() + innerWidth, this.getY() + scrollBarHeight + indPos, //in screen position
					10, 10, //in screen width
					texX, 15, //tex position
					10, 10, //texture width
					256, 256 //image size
			);
		}
		RenderSystem.disableBlend();
	}

	public void addChild(AbstractWidget child) {
		this.children.add(child);
		if (child.getX() + child.getWidth() > this.contentWidth) {
			this.contentWidth = child.getX() + child.getWidth();
			this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + scrollBarWidth);
		}
		if (child.getY() + child.getWidth() > this.contentHeight) {
			this.contentHeight = child.getY() + child.getHeight();
			this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + scrollBarHeight);
		}
	}

	public void recalculateContentSpace() {
		this.contentWidth = 0;
		this.contentHeight = 0;
		this.totalScrollHeight = 0;
		this.totalScrollWidth = 0;
		for (var child : children) {
			if (child.getX() + child.getWidth() > this.contentWidth) {
				this.contentWidth = child.getX() + child.getWidth();
				this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + this.contentWidth > this.width ? scrollBarWidth : 0);
			}
			if (child.getY() + child.getWidth() > this.contentHeight) {
				this.contentHeight = child.getY() + child.getHeight();
				this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + this.contentHeight > this.height ? scrollBarHeight : 0);
			}
		}
	}

	public int getChildrenCount() {
		return this.children.size();
	}

	public void clearChildren() {
		this.children.clear();
		recalculateContentSpace();
	}

	@Override
	public void setHeight(int value) {
		super.setHeight(value);
		this.totalScrollHeight = Math.max(0, this.contentHeight - this.height + scrollBarHeight);
		this.currentScrollY = (int) MathUtil.clamp(this.currentScrollY, 0, this.totalScrollHeight);
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		this.totalScrollWidth = Math.max(0, this.contentWidth - this.width + scrollBarWidth);
		this.currentScrollX = (int) MathUtil.clamp(this.currentScrollX, 0, this.totalScrollWidth);
	}

	public int getHorizontalScrollIndicatorPosition() {
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		return (int) (((float) innerWidth - (float) scrollBarWidth * 3) * ((float) currentScrollX / (float) totalScrollWidth));
	}

	public int getVerticalScrollIndicatorPosition() {
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;
		return (int) (((float) innerHeight - (float) scrollBarHeight * 3) * ((float) currentScrollY / (float) totalScrollHeight));
	}

	public boolean isShowingHorizontalScroll() {
		if (this.overflow == OverflowType.HIDDEN) return false;
		if (this.overflow == OverflowType.SCROLL_X) return true;
		if (this.overflow == OverflowType.SCROLL) return true;
		if (this.overflow == OverflowType.AUTO) {
			return this.width < this.contentWidth;
		}
		return false;
	}

	public boolean isShowingVerticalScroll() {
		if (this.overflow == OverflowType.HIDDEN) return false;
		if (this.overflow == OverflowType.SCROLL_Y) return true;
		if (this.overflow == OverflowType.SCROLL) return true;
		if (this.overflow == OverflowType.AUTO) {
			return this.height < this.contentHeight;
		}
		return false;
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}

	private int scrollIndicatorGrabbed;

	private boolean isHorizontalScrollIndicatorGrabbed = false;

	private boolean isVerticalScrollIndicatorGrabbed = false;

	@Override
	public void onClick(double mouseX, double mouseY) {
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;
		if (isShowingHorizontalScroll()) {
			//button left
			if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				this.currentScrollX = Math.max(0, this.currentScrollX - this.totalScrollWidth / 10);
			}
			//button right
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth - scrollBarWidth, this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				this.currentScrollX = Math.min(this.totalScrollWidth, this.currentScrollX + this.totalScrollWidth / 10);
			}
			//scroll indicator
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + scrollBarWidth + getHorizontalScrollIndicatorPosition(), this.getY() + innerHeight, scrollBarWidth, scrollBarHeight)) {
				scrollIndicatorGrabbed = (int) mouseX - (this.getX() + scrollBarWidth + getHorizontalScrollIndicatorPosition());
				isHorizontalScrollIndicatorGrabbed = true;
			}
		}
		if (isShowingVerticalScroll()) {
			//button top
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY(), scrollBarWidth, scrollBarHeight)) {
				this.currentScrollY = Math.max(0, this.currentScrollY - this.totalScrollHeight / 10);
			}
			//button bottom
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY() + innerHeight - scrollBarHeight, scrollBarWidth, scrollBarHeight)) {
				this.currentScrollY = Math.min(this.totalScrollHeight, this.currentScrollY + this.totalScrollHeight / 10);
			}
			//scroll indicator
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY() + this.scrollBarHeight + getVerticalScrollIndicatorPosition(), scrollBarWidth, scrollBarHeight)) {
				scrollIndicatorGrabbed = (int) mouseY - (this.getY() + this.scrollBarHeight + getVerticalScrollIndicatorPosition());
				isVerticalScrollIndicatorGrabbed = true;
			}
		}
	}

	@Override
	public void onRelease(double mouseX, double mouseY) {
		isHorizontalScrollIndicatorGrabbed = false;
		isVerticalScrollIndicatorGrabbed = false;
	}

	@Override
	protected void onDrag(double mouseX, double mouseY, double mouseDeltaX, double mouseDeltaY) {
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;
		if (isShowingHorizontalScroll()) {
			if (isHorizontalScrollIndicatorGrabbed) {
				double spaceToDislocate = (innerWidth - scrollBarWidth * 3);
				double positionInScrollBar = mouseX - this.getX() - this.scrollBarWidth - scrollIndicatorGrabbed;
				positionInScrollBar = MathUtil.clamp(positionInScrollBar, 0, spaceToDislocate);
				this.currentScrollX = (int) ((positionInScrollBar / spaceToDislocate) * (double) this.totalScrollWidth);
			}
		}
		if (isShowingVerticalScroll()) {
			if (isVerticalScrollIndicatorGrabbed) {
				double spaceToDislocate = (innerHeight - scrollBarHeight * 3);
				double positionInScrollBar = mouseY - this.getY() - this.scrollBarHeight - scrollIndicatorGrabbed;
				positionInScrollBar = MathUtil.clamp(positionInScrollBar, 0, spaceToDislocate);
				this.currentScrollY = (int) ((positionInScrollBar / spaceToDislocate) * (double) this.totalScrollHeight);
			}
		}
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollCount) {
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;
		boolean somethingClicked = false;
		for (var widget : this.children) {
			if (MathUtil.inBounds(mouseX, mouseY, this.getX() - this.currentScrollX + widget.getX(), this.getY() - this.currentScrollY + widget.getY(), widget.getWidth(), widget.getHeight())) {
				somethingClicked = somethingClicked || widget.mouseScrolled(mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(), scrollCount);
			}
		}
		if (!somethingClicked) {
			this.currentScrollY = (int) MathUtil.clamp(this.currentScrollY + this.totalScrollHeight * scrollCount * (-0.07), 0, this.totalScrollHeight);
		}
		//horizontal scroll bar
		if (MathUtil.inBounds(mouseX, mouseY, this.getX(), this.getY() + innerHeight, innerWidth, scrollBarHeight)) {
			this.currentScrollX = (int) MathUtil.clamp(this.currentScrollX + this.totalScrollWidth * scrollCount * (-0.07), 0, this.totalScrollWidth);
		}
		//vertical scroll bar
		if (MathUtil.inBounds(mouseX, mouseY, this.getX() + innerWidth, this.getY(), scrollBarWidth, innerHeight)) {
			this.currentScrollY = (int) MathUtil.clamp(this.currentScrollY + this.totalScrollHeight * scrollCount * (-0.07), 0, this.totalScrollHeight);
		}
		return somethingClicked || super.mouseScrolled(mouseX, mouseY, scrollCount);
	}

	public double[] innerPartGrabbed = new double[]{0, 0, 0, 0};

	public boolean isInnerPartGrabbed = false;

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean somethingClicked = false;
		for (var widget : this.children) {
			somethingClicked = somethingClicked || widget.mouseClicked(mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(), button);
		}
		if (!somethingClicked && this.isValidClickButton(button) && this.active && this.visible) {
			isInnerPartGrabbed = true;
			innerPartGrabbed = new double[]{mouseX, mouseY, this.currentScrollX, this.currentScrollY};
			boolean flag = this.clicked(mouseX, mouseY);
			if (flag) {
				this.playDownSound(Minecraft.getInstance().getSoundManager());
				this.onClick(mouseX, mouseY);
				return true;
			}
		}
		return somethingClicked;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDeltaX, double mouseDeltaY) {
		boolean somethingClicked = false;
		for (var widget : this.children) {
			if (MathUtil.inBounds(mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(), widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight())) {
				somethingClicked =
						widget.mouseDragged(mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(),
								button,
								mouseDeltaX + this.currentScrollX - this.getX(), mouseDeltaY + this.currentScrollY - this.getY()) || somethingClicked;
			}
		}
		if (!somethingClicked && this.isValidClickButton(button)) {
			var deltaX = mouseX - innerPartGrabbed[0];
			var deltaY = mouseY - innerPartGrabbed[1];

			this.currentScrollX = (int) MathUtil.clamp(innerPartGrabbed[2] - deltaX, 0, this.totalScrollWidth);
			this.currentScrollY = (int) MathUtil.clamp(innerPartGrabbed[3] - deltaY, 0, this.totalScrollHeight);
			this.onDrag(mouseX, mouseY, mouseDeltaX, mouseDeltaY);
			return true;
		}
		return somethingClicked;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		isInnerPartGrabbed = false;
		int scrollBarWidth = 10; //the number of pixels the scroll bar is going to have horizontally
		int scrollBarHeight = 10; // the number of pixels the scroll bar is going to have vertically
		int innerWidth = isShowingVerticalScroll() ? this.width - scrollBarWidth : this.width;
		int innerHeight = isShowingHorizontalScroll() ? this.height - scrollBarHeight : this.height;
		boolean somethingClicked = false;
		for (var widget : this.children) {
			somethingClicked = somethingClicked || widget.mouseReleased(mouseX + this.currentScrollX - this.getX(), mouseY + this.currentScrollY - this.getY(), button);
		}
		if (this.isValidClickButton(button)) {
			this.onRelease(mouseX, mouseY);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		for (var child : this.children) {
			child.keyPressed(pKeyCode, pScanCode, pModifiers);
		}
		return super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}

	@Override
	public boolean charTyped(char pCodePoint, int pModifiers) {
		for (var child : this.children) {
			child.charTyped(pCodePoint, pModifiers);
		}
		return super.charTyped(pCodePoint, pModifiers);
	}

	public static Vector3f getWindowCoordinates(int x, int y) {
		Vector4f pos = new Vector4f(x, y, 0f, 1f);
		pos.mul(RenderSystem.getModelViewMatrix());
		pos.mul(RenderSystem.getProjectionMatrix());
		Vector3f ndc = new Vector3f(pos.x() / pos.w(), pos.y() / pos.w(), pos.z() / pos.w());
		float windowWidth = Minecraft.getInstance().getWindow().getWidth();
		float windowHeight = Minecraft.getInstance().getWindow().getHeight();
		return new Vector3f((windowWidth / 2f) * (1f + ndc.x()), (windowHeight / 2f) * (1f + ndc.y()), 0);
	}
}
