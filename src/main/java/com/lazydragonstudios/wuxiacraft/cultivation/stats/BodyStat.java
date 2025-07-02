package com.lazydragonstudios.wuxiacraft.cultivation.stats;

public enum BodyStat {
	FORGING_LIMIT(false),
	;

	public final boolean isModifiable;

	BodyStat(boolean isModifiable) {
		this.isModifiable = isModifiable;
	}
}
