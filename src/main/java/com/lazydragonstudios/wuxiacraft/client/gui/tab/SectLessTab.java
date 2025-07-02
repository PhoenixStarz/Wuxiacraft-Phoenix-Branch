package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.*;
import com.lazydragonstudios.wuxiacraft.networking.InteractSectInviteMessage;
import com.lazydragonstudios.wuxiacraft.networking.RequestSectInvitesMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.sect.Sect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class SectLessTab extends IntrospectionTab {

	public static final HashMap<UUID, Sect> SECT_INVITES = new HashMap<>();

	public static boolean updateSectInvites = false;

	public WuxiaFlowPanel invitesPanel;

	public WuxiaFlowPanel sectCreationPanel;

	public WuxiaFlowPanel selectedSectStatsPanel;

	public WuxiaLabelBox createSectLabel;

	public WuxiaScrollPanel actualInvitesPanel;

	public WuxiaTextField sectNameBox;

	public WuxiaButton createSectButton;

	public WuxiaLabelBox invitesReceivedLabel;

	public SectLessTab(String name, Point icon) {
		super(name, icon);
	}

	@Override
	public void init(IntrospectionScreen screen) {
		sectCreationPanel = new WuxiaFlowPanel(36, 36, 210, 500, Component.empty());
		createSectLabel = new WuxiaLabelBox(0, 0, 200, Component.translatable("wuxiacraft.gui.create_sect_title"));
		sectNameBox = new WuxiaTextField(0, 0, 200, 20);
		createSectButton = new WuxiaButton(0, 0, 200, 20, Component.translatable("wuxiacraft.gui.create_sect_btn"), () -> {
		});
		invitesPanel = new WuxiaFlowPanel(36, 36, 210, 500, Component.empty());
		selectedSectStatsPanel = new WuxiaFlowPanel(36, 36, 200, 500, Component.empty());
		invitesReceivedLabel = new WuxiaLabelBox(0, 0, 200, Component.translatable("wuxiacraft.gui.invites_received"));
		actualInvitesPanel = new WuxiaScrollPanel(0, 0, 200, 500, Component.empty());
		sectCreationPanel.setMargin(5);
		invitesPanel.setMargin(5);
		sectCreationPanel.addChild(createSectLabel);
		sectCreationPanel.addChild(sectNameBox);
		sectCreationPanel.addChild(createSectButton);
		invitesPanel.addChild(invitesReceivedLabel);
		invitesPanel.addChild(actualInvitesPanel);
		screen.addRenderableWidget(sectCreationPanel);
		screen.addRenderableWidget(invitesPanel);
		screen.addRenderableWidget(selectedSectStatsPanel);

		WuxiaPacketHandler.INSTANCE.sendToServer(new RequestSectInvitesMessage());
	}

	private static void onCreateSectPress() {

	}

	private static WuxiaTexturedButton createAcceptButton(int x, int y, UUID sectId) {
		return new WuxiaTexturedButton(x, y, 15, 15, Component.empty(),
				() -> {
					WuxiaPacketHandler.INSTANCE.sendToServer(new InteractSectInviteMessage(sectId, true));
					//TODO add code to switch to the inSectTab
				},
				new ResourceLocation[]{WuxiaButton.UI_CONTROLS, WuxiaButton.UI_CONTROLS},
				new Rectangle[]{new Rectangle(0, 0, 256, 256), new Rectangle(0, 39, 256, 256)});
	}

	private static WuxiaTexturedButton createRejectButton(int x, int y, UUID sectId) {
		return new WuxiaTexturedButton(x, y, 15, 15, Component.empty(),
				() -> {
					WuxiaPacketHandler.INSTANCE.sendToServer(new InteractSectInviteMessage(sectId, false));
					SECT_INVITES.remove(sectId);
					updateSectInvites = true;
				},
				new ResourceLocation[]{WuxiaButton.UI_CONTROLS, WuxiaButton.UI_CONTROLS},
				new Rectangle[]{new Rectangle(0, 0, 256, 256), new Rectangle(15, 39, 256, 256)});
	}

	private void refreshInvitesPanel() {
		this.actualInvitesPanel.clearChildren();
		int pos = 0;
		for (var invite : SECT_INVITES.values()) {
			this.actualInvitesPanel.addChild(createAcceptButton(3, 3 + pos * 18, invite.getSectId()));
			this.actualInvitesPanel.addChild(createRejectButton(18, 3 + pos * 18, invite.getSectId()));
			this.actualInvitesPanel.addChild(new WuxiaLabelBox(36, 6 + pos * 18, 200, Component.literal(invite.getSectName())));
			pos++;
		}
	}

	@Override
	public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var mc = Minecraft.getInstance();
		int totalXSpace = mc.getWindow().getGuiScaledWidth();
		int totalYSpace = mc.getWindow().getGuiScaledHeight();
		int freeYSpace = totalYSpace - 36;
		int freeXSpace = totalXSpace - 36;
		int stretchedSpace = freeXSpace - 210 - 210;

		sectCreationPanel.setX(36);
		sectCreationPanel.setHeight(freeYSpace);

		invitesPanel.setHeight(freeYSpace);
		invitesPanel.setX(sectCreationPanel.getX() + sectCreationPanel.getWidth());
		invitesPanel.setWidth(stretchedSpace);
		actualInvitesPanel.setWidth(invitesPanel.getWidth() - 6);
		actualInvitesPanel.setHeight(invitesPanel.getHeight() - 23);

		selectedSectStatsPanel.setHeight(freeYSpace);
		selectedSectStatsPanel.setX(invitesPanel.getX() + invitesPanel.getWidth());

		this.refreshInvitesPanel();
		if (updateSectInvites) {
			this.refreshInvitesPanel();
			updateSectInvites = false;
		}

	}

	@Override
	public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

	}
}
