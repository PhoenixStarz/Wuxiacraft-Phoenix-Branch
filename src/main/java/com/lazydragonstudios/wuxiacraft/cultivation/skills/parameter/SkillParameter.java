package com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter;

import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public abstract class SkillParameter<T> implements Comparable<SkillParameter<?>> {

	protected final String name;

	protected final T defaultValue;

	protected T value;

	public SkillParameter(String name, T defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public String getName() {
		return name;
	}

	public abstract void updateValue(Object v);

	public abstract boolean validateValue(T value);

	public abstract CompoundTag encode();

	public abstract void decode(CompoundTag tag);

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int compareTo(@NotNull SkillParameter o) {
		return this.name.compareTo(o.name);
	}

	@OnlyIn(Dist.CLIENT)
	public abstract List<AbstractWidget> getWidgets(Consumer<T> changeListener);

	public final List<AbstractWidget> getLabelWidget() {
		var widgets = new LinkedList<AbstractWidget>();
		widgets.add(new WuxiaLabel(0, 0, Component.translatable("wuxiacraft.skill.parameter." + this.name).append(":"), 0xFFAA00));
		return widgets;
	}
}
