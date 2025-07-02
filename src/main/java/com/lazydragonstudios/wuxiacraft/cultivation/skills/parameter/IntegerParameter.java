package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTextField;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.function.Consumer;

public class IntegerParameter extends SkillParameter<Integer> {

	public IntegerParameter(String name, Integer defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public boolean validateValue(Integer value) {
		return value != null;
	}

	@Override
	public CompoundTag encode() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("value", value);
		return tag;
	}

	@Override
	public void decode(CompoundTag tag) {
		this.value = tag.getInt("value");
	}

	@Override
	public void updateValue(Object v) {
		this.setValue((Integer) v);
	}

	@Override
	public List<AbstractWidget> getWidgets(Consumer<Integer> changeListener) {
		var widgets = this.getLabelWidget();
		WuxiaTextField testBox = new WuxiaTextField(0, 0, 200, 20);
		testBox.editBox.setValue("" + value);
		//noinspection unchecked
		testBox.editBox.setResponder(v -> changeListener.accept(Integer.parseInt(v)));
		widgets.add(testBox);
		return widgets;
	}
}
