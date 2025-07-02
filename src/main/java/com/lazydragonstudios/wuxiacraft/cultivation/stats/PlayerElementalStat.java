package com.lazydragonstudios.wuxiacraft.cultivation.stats;

public enum PlayerElementalStat {
	COMPREHENSION(true, 0),
	RESISTANCE(false, 2),
	PIERCE(false, 2);

	public final boolean isModifiable;

	/**
	 * Value of scale to be displayed in character sheet.
	 */
	public final int displayScale;

	PlayerElementalStat(boolean isModifiable, int displayScale) {
		this.isModifiable = isModifiable;
		this.displayScale = displayScale;
	}
}
