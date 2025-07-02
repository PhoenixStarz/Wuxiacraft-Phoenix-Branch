package com.lazydragonstudios.wuxiacraft.client.gui;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.container.InscriberMenu;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.networking.RenameItemInInscriberMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class InscriberScreen extends AbstractContainerScreen<InscriberMenu> {

	public static final ResourceLocation INSCRIBER_SCREEN = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/gui/inscription_table.png");

	private System selectedSystem = System.ESSENCE;

	private EditBox nameBox;

	private static final HashMap<System, Rectangle> buttons = new HashMap<>();

	static {//x, y, texX, texY
		buttons.put(System.BODY, new Rectangle(19, 27, 36, 134));
		buttons.put(System.DIVINE, new Rectangle(39, 27, 52, 134));
		buttons.put(System.ESSENCE, new Rectangle(59, 27, 68, 134));
	}

	private static final Rectangle writeBtn = new Rectangle(103, 23, 84, 134);

	public InscriberScreen(InscriberMenu inscriberMenu, Inventory inventory, Component title) {
		super(inscriberMenu, inventory, title);
		this.width = 201;
		this.height = 134;
	}

	@Override
	protected void init() {
		super.init();
		this.nameBox = new EditBox(this.font, this.leftPos + 19, this.topPos + 5, 102, 10, Component.empty());
		this.nameBox.setBordered(false);
		this.nameBox.setMaxLength(120);
		this.nameBox.setResponder(this::onNameChanged);
		this.addRenderableWidget(this.nameBox);
	}

	private void onNameChanged(String name) {
		WuxiaPacketHandler.INSTANCE.sendToServer(new RenameItemInInscriberMessage(name));
		this.menu.setItemName(name);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		this.renderBackground(guiGraphics);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(this.leftPos, this.topPos, 0);
		guiGraphics.blit(INSCRIBER_SCREEN, 0, 0, 0, 0, 201, 134);
		for (var system : System.values()) {
			Rectangle button = buttons.get(system);
			if (selectedSystem == system) {
				guiGraphics.blit(INSCRIBER_SCREEN, button.x, button.y, 18, 134, 18, 18);
			}
			if (MathUtil.inBounds(mouseX - this.leftPos, mouseY - this.topPos, button.x, button.y, 18, 18)) {
				guiGraphics.blit(INSCRIBER_SCREEN, button.x, button.y, 0, 134, 18, 18);
			}
			guiGraphics.blit(INSCRIBER_SCREEN, button.x + 1, button.y + 1, button.width, button.height, 16, 16);
		}
		if (MathUtil.inBounds(mouseX - this.leftPos, mouseY - this.topPos, writeBtn.x, writeBtn.y, 18, 18)) {
			guiGraphics.blit(INSCRIBER_SCREEN, writeBtn.x, writeBtn.y, 0, 134, 18, 18);
		}
		guiGraphics.blit(INSCRIBER_SCREEN, writeBtn.x + 1, writeBtn.y + 1, writeBtn.width, writeBtn.height, 16, 16);
		guiGraphics.pose().popPose();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		var consumed = false;
		if (this.minecraft == null) return false;
		if (this.minecraft.gameMode == null) return false;
		for (var system : System.values()) {
			var btn = buttons.get(system);
			if (MathUtil.inBounds(mouseX - this.leftPos, mouseY - this.topPos, btn.x, btn.y, 18, 18)) {
				consumed = true;
				this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, system.ordinal());
				this.selectedSystem = system;
			}
		}
		if (MathUtil.inBounds(mouseX - this.leftPos, mouseY - this.topPos, writeBtn.x, writeBtn.y, 18, 18)) {
			this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 3);
		}
		return consumed || super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mx, int my) {
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (this.getFocused() != null && this.getFocused().keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.shouldCloseOnEsc()) {
			this.onClose();
			return true;
		} else if (keyCode == GLFW.GLFW_KEY_TAB) {
			boolean flag = !hasShiftDown();
//			if (!this.changeFocus(flag)) {
//				this.changeFocus(flag);
//			}
		}
		return false;
	}
}
