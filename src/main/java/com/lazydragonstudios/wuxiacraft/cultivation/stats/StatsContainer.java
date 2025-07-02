package com.lazydragonstudios.wuxiacraft.cultivation.stats;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

@SuppressWarnings("unchecked")
public interface StatsContainer {

	default Set<PlayerStat> getSavedStats() {
		return this.getPlayerStats().keySet();
	}

	default Set<PlayerSystemStat> getSavedStats(System system) {
		return this.getSystemStats().getOrDefault(system, new HashMap<>()).keySet();
	}

	default Set<ResourceLocation> getSavedStats(PlayerElementalStat stat) {
		return this.getElementalStats().getOrDefault(stat, new HashMap<>()).keySet();
	}

	default Set<ResourceLocation> getSavedStats(System system, PlayerSystemElementalStat stat) {
		return this.getSystemElementalStats().getOrDefault(system, new HashMap<>()).getOrDefault(stat, new HashMap<>()).keySet();
	}

	@Nonnull
	default BigDecimal getStat(PlayerStat stat) {
		return getPlayerStats().getOrDefault(stat, BigDecimal.ZERO);
	}

	HashMap<PlayerStat, BigDecimal> getPlayerStats();

	@Nonnull
	default BigDecimal getStat(PlayerElementalStat stat, ResourceLocation elementLocation) {
		return this.getElementalStats().getOrDefault(stat, new HashMap<>()).getOrDefault(elementLocation, BigDecimal.ZERO);
	}

	HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> getElementalStats();

	@Nonnull
	default BigDecimal getStat(System system, PlayerSystemStat stat) {
		return this.getSystemStats().getOrDefault(system, new HashMap<>()).getOrDefault(stat, BigDecimal.ZERO);
	}

	HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> getSystemStats();

	@Nonnull
	default BigDecimal getStat(System system, ResourceLocation elementLocation, PlayerSystemElementalStat stat) {
		return this.getSystemElementalStats().getOrDefault(system, new HashMap<>())
				.getOrDefault(elementLocation, new HashMap<>())
				.getOrDefault(stat, BigDecimal.ZERO);
	}

	HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> getSystemElementalStats();

	default <T extends StatsContainer> T setStat(PlayerStat stat, BigDecimal value) {
		if (stat.isModifiable) return (T) this;
		this.getPlayerStats().put(stat, value);
		return (T) this;
	}

	default <T extends StatsContainer> T setStat(System system, PlayerSystemStat stat, BigDecimal value) {
		if (stat.isModifiable) return (T) this;
		this.getSystemStats().putIfAbsent(system, new HashMap<>());
		this.getSystemStats().get(system).put(stat, value);
		return (T) this;
	}

	default <T extends StatsContainer> T setStat(PlayerElementalStat stat, ResourceLocation elementLocation, BigDecimal value) {
		if (stat.isModifiable) return (T) this;
		this.getElementalStats().putIfAbsent(stat, new HashMap<>());
		this.getElementalStats().get(stat).put(elementLocation, value);
		return (T) this;
	}

	default <T extends StatsContainer> T setStat(System system, PlayerSystemElementalStat stat, ResourceLocation elementLocation, BigDecimal value) {
		if (stat.isModifiable) return (T) this;
		this.getSystemElementalStats().putIfAbsent(system, new HashMap<>());
		this.getSystemElementalStats().get(system).putIfAbsent(stat, new HashMap<>());
		this.getSystemElementalStats().get(system).get(stat).put(elementLocation, value);
		return (T) this;
	}

}
