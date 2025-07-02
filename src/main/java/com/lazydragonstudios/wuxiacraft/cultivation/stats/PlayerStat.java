package com.lazydragonstudios.wuxiacraft.cultivation.stats;

import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;

import java.awt.*;
import java.math.BigDecimal;

public enum PlayerStat {
	HEALTH(new BigDecimal("20.0"), true, 2),
	MAX_HEALTH(new BigDecimal("20.0"), false, 2),
	STRENGTH(new BigDecimal("0.0"), false, 2),
	AGILITY(new BigDecimal("0.0"), false, 3),
	HEALTH_REGEN(new BigDecimal("0.01"), false, 4),
	HEALTH_REGEN_COST(new BigDecimal("0.03"), false, 4),
	EXERCISE_COST(new BigDecimal("0.02"), false, 4),
	EXERCISE_CONVERSION(new BigDecimal("0.01"), false, 4),
	BARRIER(new BigDecimal("0.0"), true, 2),
	MAX_BARRIER(new BigDecimal("0.0"), false, 2),
	BARRIER_REGEN(new BigDecimal("0.0"), false, 4),
	BARRIER_REGEN_COST(new BigDecimal("0.0"), false, 2),
	BARRIER_REGEN_COOLDOWN(new BigDecimal("0.0"), true, 0),
	DETECTION_RANGE(new BigDecimal("10.00"), false, 2),
	DETECTION_STRENGTH(new BigDecimal("0.00"), false, 2),
	DETECTION_RESISTANCE(new BigDecimal("0.00"), false, 2),
	LIVES(new BigDecimal(3), true, 0),

	HUNGER_REGEN(BigDecimal.ZERO, false, 2),
	HUNGER_REGEN_COST(BigDecimal.ZERO, false, 2)
	;

	/**
	 * the default value of the stat
	 */
	public final BigDecimal defaultValue;

	/**
	 * this means that the value of this stat can be modified from outside Cultivation class
	 * Also means this value is stored, but not calculated in every alteration
	 */
	public final boolean isModifiable;

	/**
	 * Value of scale to be displayed in character sheet.
	 */
	public final int displayScale;

	PlayerStat(BigDecimal defaultValue, boolean isModifiable, int displayScale) {
		this.defaultValue = defaultValue;
		this.isModifiable = isModifiable;
		this.displayScale = displayScale;
	}
}
