package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaFlowPanel;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ElementParameter extends SkillParameter<ResourceLocation> {

	public ElementParameter(String name, ResourceLocation defaultValue) {
		super(name, defaultValue);
	}

	private final LinkedList<ResourceLocation> elementOptions = new LinkedList<>();

	public void addElementOption(ResourceLocation elementLocation) {
		if (WuxiaRegistries.ELEMENTS.get().containsKey(elementLocation))
			this.elementOptions.add(elementLocation);
	}

	@Override
	public void updateValue(Object v) {
		this.setValue((ResourceLocation) v);
	}

	@Override
	public boolean validateValue(ResourceLocation value) {
		return WuxiaRegistries.ELEMENTS.get().containsKey(value);
	}

	@Override
	public CompoundTag encode() {
		CompoundTag tag = new CompoundTag();
		tag.putString("value", this.value.toString());
		return tag;
	}

	@Override
	public void decode(CompoundTag tag) {
		this.value = new ResourceLocation(tag.getString("value"));
	}

	@Override
	public List<AbstractWidget> getWidgets(Consumer<ResourceLocation> changeListener) {
		var widgets = this.getLabelWidget();
		var selectionBox = new WuxiaFlowPanel(0, 0, 200, 38, Component.empty());
		return widgets;
	}
}
