package com.lazydragonstudios.wuxiacraft.client.gui.tab;

import com.lazydragonstudios.wuxiacraft.client.gui.IntrospectionScreen;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.*;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.SkillParameter;
import com.lazydragonstudios.wuxiacraft.networking.RequestSkillSaveMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class SkillsTab extends IntrospectionTab {

	@Nullable
	private IntrospectionScreen screen;

	private WuxiaFlowPanel skillAspectsPanel;

	private WuxiaScrollPanel skillChainPanel;

	private WuxiaFlowPanel skillStatsPanel;

	private WuxiaSkillComposer skillComposer;

	private WuxiaButton saveBtn;

	private final LinkedList<WuxiaTexturedButton> skillsSelectionButtons = new LinkedList<>();

	private ResourceLocation draggingAspect = null;

	private int selectedSkill = 0;

	public SkillsTab(String name) {
		super(name, new Point(64, 68));
	}

	@Override
	public void init(IntrospectionScreen screen) {
		this.screen = screen;
		skillAspectsPanel = new WuxiaFlowPanel(36, 74, 116, 200, Component.empty());
		skillChainPanel = new WuxiaScrollPanel(36 + 116, 74, 100, 200, Component.empty());
		skillStatsPanel = new WuxiaFlowPanel(36 + 116 + 100, 74, 210, 200, Component.empty());
		skillStatsPanel.setMargin(5);
		screen.addRenderableWidget(skillAspectsPanel);
		screen.addRenderableWidget(skillChainPanel);
		screen.addRenderableWidget(skillStatsPanel);
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		var skillSlot0 = cultivation.getSkills().getSkillAt(this.selectedSkill);
		this.skillComposer = new WuxiaSkillComposer(0, 0, Component.empty(), skillSlot0);
		this.skillComposer.onClick = onComposerClick;
		this.skillComposer.onRelease = onComposerRelease;
		skillChainPanel.addChild(this.skillComposer);
		for (var skill : cultivation.getSkills().knownSkills) {
			var aspectWidget = new WuxiaAspectWidget(0, 0, skill);
			aspectWidget.setOnClicked(onAspectClick(skill));
			aspectWidget.setOnRelease(onAspectRelease());
			skillAspectsPanel.addChild(aspectWidget);
		}
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			var btn = new WuxiaTexturedButton(36 + i * 38, 36, 38, 38,
					Component.literal("" + (i + 1)),
					() -> this.changeSkill(finalI), new ResourceLocation[]{WuxiaButton.UI_CONTROLS}, new Rectangle[]{new Rectangle(116, 0, 256, 256)});
			skillsSelectionButtons.add(btn);
			screen.addRenderableWidget(btn);
		}
		this.saveBtn = new WuxiaButton(32 + 116 + 100, 72, 200, 20, Component.translatable("wuxiacraft.button.save"), () -> {
			var skillTag = this.skillComposer.getSkill().serialize();
			WuxiaPacketHandler.INSTANCE.sendToServer(new RequestSkillSaveMessage(this.selectedSkill, skillTag));
			cultivation.getSkills().setSkillAt(this.selectedSkill, this.skillComposer.getSkill());
		});
		screen.addRenderableWidget(saveBtn);
		this.refreshWidgetsForSkillChain();
	}

	private BiConsumer<Double, Double> onAspectClick(ResourceLocation aspectLocation) {
		return (mx, my) -> {
			this.draggingAspect = aspectLocation;
		};
	}

	private BiConsumer<Double, Double> onAspectRelease() {
		return (mx, my) -> {
			this.draggingAspect = null;
		};
	}

	private final MouseInputPredicate onComposerClick = (mx, my, mb) -> {
		if (mb == 1) {
			var pos = this.skillComposer.getLinkAtPosition(mx, my);
			if (pos == -1) return false;
			this.skillComposer.removeSkillAtPosition(pos);
			this.refreshWidgetsForSkillChain();
			return true;
		}
		return false;
	};

	private final MouseInputPredicate onComposerRelease = (mx, my, mb) -> {
		var pos = this.skillComposer.getLinkAtPosition(mx, my);
		if (pos == -1) return false;
		this.skillComposer.addSkillToPosition(this.draggingAspect, pos);
		this.draggingAspect = null;
		this.refreshWidgetsForSkillChain();
		return true;
	};

	@Override
	public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		var mc = Minecraft.getInstance();
		int totalXSpace = mc.getWindow().getGuiScaledWidth();
		int totalYSpace = mc.getWindow().getGuiScaledHeight();
		int freeYSpace = totalYSpace - 72;
		int freeXSpace = totalXSpace - 36;
		int stretchedSpace = freeXSpace - 210 - 116;
		skillAspectsPanel.setHeight(freeYSpace);

		skillChainPanel.setHeight(freeYSpace);
		skillChainPanel.setWidth(stretchedSpace);

		skillStatsPanel.setHeight(freeYSpace - 26);
		skillStatsPanel.setX(36 + 116 + stretchedSpace);

		this.saveBtn.setY(totalYSpace - 26);
		this.saveBtn.setX(totalXSpace - 205);

		for (var btn : this.skillsSelectionButtons) {
			if (MathUtil.inBounds(mouseX, mouseY, btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight())) {
				btn.setTex(new Rectangle[]{new Rectangle(154, 0, 256, 256)});
			} else {
				btn.setTex(new Rectangle[]{new Rectangle(116, 0, 256, 256)});
			}
		}

		if (this.draggingAspect != null) {
			var textureLocation = new ResourceLocation(this.draggingAspect.getNamespace(), "textures/skills/" + this.draggingAspect.getPath() + ".png");
			guiGraphics.pose().pushPose();
			guiGraphics.pose().translate(mouseX, mouseY, 0);
			guiGraphics.blit(textureLocation, -16, -16, 32, 32, 0, 0, 32, 32, 32, 32);
			guiGraphics.pose().popPose();
		}
	}

	@Override
	public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

	}

	public void changeSkill(int skill) {
		if (skill < 0 || skill > 10) return;
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		this.selectedSkill = skill;
		var skillSlot = cultivation.getSkills().getSkillAt(this.selectedSkill);
		this.skillComposer.changeSkill(skillSlot);
		this.refreshWidgetsForSkillChain();
	}

	public void refreshWidgetsForSkillChain() {
		this.skillStatsPanel.clearChildren();
		var widgets = new LinkedList<AbstractWidget>();

		var parameters = new LinkedList<SkillParameter<?>>();
		this.skillComposer.getSkill().getSkillChain().forEach(aspect -> parameters.addAll(aspect.getSkillParameters().values()));
		for (var parameter : parameters) {
			if(parameter == null) continue;
			widgets.addAll(parameter.getWidgets(parameter::updateValue));
		}
		if (this.screen != null) {
			widgets.forEach(widget -> this.skillStatsPanel.addChild(widget));
		}
	}
}
