package com.lazydragonstudios.wuxiacraft.cultivation;

public class CultivationRealm {

	/**
	 * the Name of this realm
	 */
	public final String name;

	/**
	 * The cultivation system this realm belongs to
	 */
	public final System system;

	public CultivationRealm(String name, System system) {
		this.name = name;
		this.system = system;
	}
}
