package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;

public class FormationStatsContainer implements StatsContainer {

	private @Nullable BlockPos formationActive;

	private double range;

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> playerElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> playerSystemStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> playerSystemElementalStats = new HashMap<>();

	public @Nullable BlockPos getFormationActive() {
		return formationActive;
	}

	public void setFormationActive(@Nullable BlockPos formationActive) {
		this.formationActive = formationActive;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public boolean isWithingRange(double x, double y, double z) {
		if (this.getFormationActive() == null) return false;
		var distSqr = this.getFormationActive().distToCenterSqr(x, y, z);
		var radius = this.getRange();
		return distSqr <= (radius * radius);
	}

	@Override
	public HashMap<PlayerStat, BigDecimal> getPlayerStats() {
		return playerStats;
	}

	@Override
	public HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> getElementalStats() {
		return playerElementalStats;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> getSystemStats() {
		return playerSystemStats;
	}

	@Override
	public HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> getSystemElementalStats() {
		return playerSystemElementalStats;
	}

	public void clear() {
		this.playerStats.clear();
		this.playerElementalStats.clear();
		this.playerSystemStats.clear();
		this.playerSystemElementalStats.clear();
	}

	public void copyFrom(FormationStatsContainer stats) {
		clear();
		this.playerStats.putAll(stats.playerStats);
		this.playerElementalStats.putAll(stats.playerElementalStats);
		this.playerSystemStats.putAll(stats.playerSystemStats);
		this.playerSystemElementalStats.putAll(stats.playerSystemElementalStats);
	}
}
