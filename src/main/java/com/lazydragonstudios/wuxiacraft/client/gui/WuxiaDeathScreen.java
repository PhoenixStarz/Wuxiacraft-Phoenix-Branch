package com.lazydragonstudios.wuxiacraft.client.gui;

import com.google.common.collect.Lists;
import com.lazydragonstudios.wuxiacraft.networking.RequestRTPOnDeathMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WuxiaDeathScreen extends Screen {

	private int delayTicker;

	private final Component causeOfDeath;

	private final boolean hardcore;

	private Component deathScore;

	private final List<Button> exitButtons = Lists.newArrayList();

	public WuxiaDeathScreen(@Nullable Component causeOfDeath, boolean hardcore) {
		super(Component.translatable(hardcore ? "deathScreen.title.hardcore" : "deathScreen.title"));
		this.causeOfDeath = causeOfDeath;
		this.hardcore = hardcore;
	}

	@SuppressWarnings("ConstantConditions")
	protected void init() {
		this.delayTicker = 0;
		this.exitButtons.clear();
		//this is the first btn
		this.exitButtons.add(this.addRenderableWidget(Button.builder(this.hardcore ? Component.translatable("deathScreen.spectate") : Component.translatable("deathScreen.respawn"), (p_95930_) -> {
			this.minecraft.player.respawn();
			this.minecraft.player.setHealth(20.0f); // workaround, for some reason, when player hp is <= 0 null screens become death screens
			this.minecraft.setScreen(null);
			p_95930_.active = false;
		}).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build()));
		int btnHeight = 96;
		//this is the random tp btn
		if (!this.hardcore) {
			this.exitButtons.add(this.addRenderableWidget(Button.builder(Component.translatable("wuxiacraft.gui.death.random_teleport.name"), (p_95925_) -> {
				WuxiaPacketHandler.INSTANCE.sendToServer(new RequestRTPOnDeathMessage(true));
				this.minecraft.player.respawn();
				this.minecraft.setScreen(null);
			}).bounds(this.width / 2 - 100, this.height / 4 + btnHeight, 200, 20).build()));
			btnHeight += 24;
		}
		//this is the exit btn (exit confirm)
		this.exitButtons.add(this.addRenderableWidget(Button.builder(Component.translatable("deathScreen.titleScreen"), (p_262871_) -> {
			this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::handleExitToTitleScreen, true);
		}).bounds(this.width / 2 - 100, this.height / 4 + btnHeight, 200, 20).build()));

		for (Button button : this.exitButtons) {
			button.active = false;
		}

		this.deathScore = (Component.translatable("deathScreen.score")).append(": ").append((Component.literal(Integer.toString(this.minecraft.player.getScore()))).withStyle(ChatFormatting.YELLOW));
	}

	private void handleExitToTitleScreen() {
		if (this.hardcore) {
			this.exitToTitleScreen();
		} else {
			ConfirmScreen confirmscreen = new DeathScreen.TitleConfirmScreen((p_262870_) -> {
				if (p_262870_) {
					this.exitToTitleScreen();
				} else {
					this.minecraft.player.respawn();
					this.minecraft.setScreen((Screen) null);
				}

			}, Component.translatable("deathScreen.quit.confirm"), CommonComponents.EMPTY, Component.translatable("deathScreen.titleScreen"), Component.translatable("deathScreen.respawn"));
			this.minecraft.setScreen(confirmscreen);
			confirmscreen.setDelay(20);
		}
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}

	@SuppressWarnings("ConstantConditions")
	private void confirmResult(boolean p_95932_) {
		if (p_95932_) {
			this.exitToTitleScreen();
		} else {
			this.minecraft.player.respawn();
			this.minecraft.setScreen(null);
		}

	}

	@SuppressWarnings("ConstantConditions")
	private void exitToTitleScreen() {
		if (this.minecraft.level != null) {
			this.minecraft.level.disconnect();
		}

		this.minecraft.clearLevel(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
		this.minecraft.setScreen(new TitleScreen());
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		guiGraphics.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
		guiGraphics.pose().pushPose();
		guiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2 / 2, 30, 16777215);
		guiGraphics.pose().popPose();
		if (this.causeOfDeath != null) {
			guiGraphics.drawCenteredString(this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
		}

		guiGraphics.drawCenteredString(this.font, this.deathScore, this.width / 2, 100, 16777215);
		if (this.causeOfDeath != null && pMouseY > 85 && pMouseY < 85 + 9) {
			Style style = this.getClickedComponentStyleAt(pMouseX);
			guiGraphics.renderComponentHoverEffect(this.font, style, pMouseX, pMouseY);
		}

		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
	}

	@SuppressWarnings("ConstantConditions")
	@Nullable
	private Style getClickedComponentStyleAt(int p_95918_) {
		if (this.causeOfDeath == null) {
			return null;
		} else {
			int i = this.minecraft.font.width(this.causeOfDeath);
			int j = this.width / 2 - i / 2;
			int k = this.width / 2 + i / 2;
			return p_95918_ >= j && p_95918_ <= k ? this.minecraft.font.getSplitter().componentStyleAtWidth(this.causeOfDeath, p_95918_ - j) : null;
		}
	}

	@Override
	public boolean mouseClicked(double p_95914_, double p_95915_, int p_95916_) {
		if (this.causeOfDeath != null && p_95915_ > 85.0D && p_95915_ < (double) (85 + 9)) {
			Style style = this.getClickedComponentStyleAt((int) p_95914_);
			if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
				this.handleComponentClicked(style);
				return false;
			}
		}

		return super.mouseClicked(p_95914_, p_95915_, p_95916_);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		++this.delayTicker;
		if (this.delayTicker == 20) {
			for (Button button : this.exitButtons) {
				button.active = true;
			}
		}

	}
}
