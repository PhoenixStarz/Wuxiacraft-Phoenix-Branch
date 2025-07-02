package com.lazydragonstudios.wuxiacraft.cultivation.body;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.util.HashMap;

public class BodyPartType implements StatsContainer {

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> elementalStats = new HashMap<>();

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> systemElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> systemStats = new HashMap<>();

	@Override
	public HashMap<PlayerStat, BigDecimal> getPlayerStats() {
		return this.playerStats;
	}

	@Override
	public HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> getElementalStats() {
		return this.elementalStats;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> getSystemStats() {
		return this.systemStats;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> getSystemElementalStats() {
		return this.systemElementalStats;
	}
}
