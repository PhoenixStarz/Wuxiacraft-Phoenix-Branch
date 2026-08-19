package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.util.HashMap;

public abstract class ElementalConverter extends TechniqueAspect {

	/**
	 * the amount of elemental cultivation base that is going to become raw cultivation base
	 */
	public double amount;

	/**
	 * the element to be converted from
	 */
	public ResourceLocation element;

	public ElementalConverter(double amount, ResourceLocation element) {
		this.amount = amount;
		this.element = element;
	}

	/**
	 * It'll remove element value from metadata to apply this effects
	 * @see com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.ElementalConverter#convert
	 * @param metaData the current modifiers when accepting this
	 * @param proficiency the proficiency of this aspect
	 */
	@Override
	public void accept(HashMap<String, Object> metaData, BigDecimal proficiency) {
		super.accept(metaData, proficiency);
		String elementBase = "element-base-" + element.getPath();
		if (!metaData.containsKey(elementBase)) return;
		double elementBaseAmount = (double) metaData.get(elementBase);
		var modifier = this.getCurrentCheckpoint(proficiency).modifier().doubleValue();
		var converted = Math.min(elementBaseAmount, this.amount * (1 + modifier));
		elementBaseAmount -= converted;
		if (elementBaseAmount == 0) metaData.remove(elementBase);
		else metaData.put(elementBase, elementBaseAmount);
		convert(converted, metaData, proficiency);
	}

	/**
	 * After the element is converted into accept, it'll allow subclasses to do stuff with the converted amount
	 * @param converted the converted amount (limited by the amount of this aspect)
	 * @param metaData the current modifiers when accepting this
	 * @param proficiency the proficiency of this aspect
	 */
	public abstract void convert(double converted, HashMap<String, Object> metaData, BigDecimal proficiency);

	/**
	 * It checks if the aspect can be forward connected to this aspect
	 *
	 * @param aspect aspect to be connected to
	 * @return whether this can be connected or not
	 */
	@Override
	public boolean canConnect(TechniqueAspect aspect) {
		if (aspect instanceof ElementalConsumer con) {
			return con.element.equals(this.element);
		}
		if (aspect instanceof ElementalConverter con) {
			return con.element.equals(this.element);
		}
		return false;
	}
}
