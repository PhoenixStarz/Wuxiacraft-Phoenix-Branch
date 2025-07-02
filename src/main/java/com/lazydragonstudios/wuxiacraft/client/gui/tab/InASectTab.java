package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaButton;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaScrollPanel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTexturedButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.function.Supplier;

public class InASectTab extends IntrospectionTab {

	private WuxiaScrollPanel tabPanel;

	private final HashMap<String, Supplier<SectSubTab>> subTabSuppliers = new HashMap<>();

	private final HashMap<String, WuxiaTexturedButton> subTabButtons = new HashMap<>();

	private SectSubTab selectedTab;

	public InASectTab(String name, Point icon) {
		super(name, icon);
	}

		@Override
		public void init(IntrospectionScreen screen) {
			super.init(screen);
			this.tabPanel = new WuxiaScrollPanel(33, 33, 200, 200, Component.empty());
			this.subTabSuppliers.put("dashboard", SectDashboardSubTab::new);
			this.selectedTab = this.subTabSuppliers.get("dashboard").get();
			this.selectedTab.init(tabPanel);
		}

		@Override
	public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if(this.selectedTab != null) {
			this.selectedTab.renderBg(guiGraphics, mouseX, mouseY);
		}
	}

	@Override
	public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

	}

	//  tabs -> dashboard / members / ranks / formation power

  //  dashboard -> invites sent / leader name / self position

	// members -> other members list / if admin -> ranks and deletion

	// ranks -> create custom ranks inside like elder and shit / probably member management permission

	// formation power -> just a stats list of the formation stats of the sect

}