package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaTextField;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Consumer;

public class NumberParameter extends SkillParameter<BigDecimal> {

	public NumberParameter(String name, BigDecimal defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public boolean validateValue(BigDecimal value) {
		return value != null;
	}

	@Override
	public CompoundTag encode() {
		CompoundTag tag = new CompoundTag();
		tag.putString("value", value.setScale(Math.min(4, value.scale()), RoundingMode.HALF_DOWN).toPlainString());
		return tag;
	}

	@Override
	public void decode(CompoundTag tag) {
		this.value = new BigDecimal(tag.getString("value"));
	}

	@Override
	public void updateValue(Object v) {
		this.setValue((BigDecimal) v);
	}

	@Override
	public List<AbstractWidget> getWidgets(Consumer<BigDecimal> changeListener) {
		var widgets = this.getLabelWidget();
		WuxiaTextField textBox = new WuxiaTextField(0, 0, 200, 20);
		textBox.editBox.setValue("" + value);
		textBox.editBox.setResponder((newValue) -> changeListener.accept(new BigDecimal(newValue)));
		widgets.add(textBox);
		return widgets;
	}
}
