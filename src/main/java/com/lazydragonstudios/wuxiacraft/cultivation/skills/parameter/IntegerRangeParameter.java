package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import com.lazydragonstudios.wuxiacraft.util.MathUtil;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

import java.util.List;
import java.util.function.Consumer;

public class IntegerRangeParameter extends IntegerParameter {

	private Integer min;

	private Integer max;

	public IntegerRangeParameter(String name, Integer min, Integer max) {
		super(name, min);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validateValue(Integer value) {
		return super.validateValue(value) && MathUtil.between(value, this.min, this.max);
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	@Override
	public List<AbstractWidget> getWidgets(Consumer<Integer> changeListener) {
		var widgets = this.getLabelWidget();
		widgets.add(new ForgeSlider(0, 0, 200, 20, Component.empty(), Component.empty(), this.getMin().doubleValue(), this.getMax().doubleValue(), value.doubleValue(), true) {

			@Override
			protected void applyValue() {
				changeListener.accept((int) (this.minValue + this.value * (this.maxValue - this.minValue)));
			}
		});
		return widgets;
	}
}
