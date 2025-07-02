package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

public class NumberRangeParameter extends NumberParameter {

	private BigDecimal min;

	private BigDecimal max;

	public NumberRangeParameter(String name, BigDecimal min, BigDecimal max) {
		super(name, min);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validateValue(BigDecimal value) {
		return super.validateValue(value) && value.compareTo(this.getMin()) >= 0 && value.compareTo(this.getMax()) <= 0;
	}

	public BigDecimal getMin() {
		return min;
	}

	public BigDecimal getMax() {
		return max;
	}
	@Override
	public List<AbstractWidget> getWidgets(Consumer<BigDecimal> changeListener) {
		var widgets = this.getLabelWidget();
		widgets.add(new ForgeSlider(0, 0, 200, 20, Component.empty(), Component.empty(), this.getMin().doubleValue(), this.getMax().doubleValue(), value.doubleValue(), 0.01D, 2, true) {

			@Override
			protected void applyValue() {
				changeListener.accept(BigDecimal.valueOf(this.value));
			}
		});
		return widgets;
	}
}
