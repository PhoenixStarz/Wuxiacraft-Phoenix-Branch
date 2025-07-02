package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.util.HashMap;

public class ElementToStatsConsumer extends ElementalConsumer implements StatsContainer {

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> playerSystemStats = new HashMap<>();

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> playerElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> playerSystemElementalStats = new HashMap<>();

	public ElementToStatsConsumer(ResourceLocation element, double cost) {
		super(element, cost);
	}

	@Override
	public void consumed(HashMap<String, Object> metaData, BigDecimal proficiency) {
		var modifier = this.getCurrentCheckpoint(proficiency).modifier();
		for (var stat : this.playerStats.keySet()) {
			metaData.put("stat-" + stat.name().toLowerCase(), this.playerStats.get(stat).multiply(BigDecimal.ONE.add(modifier)));
		}
		for (var stat : this.playerElementalStats.keySet()) {
			for (var elementLocation : this.playerElementalStats.get(stat).keySet()) {
				metaData.put(elementLocation.toString().toLowerCase() + "-stat-" + stat.name().toLowerCase(), this.playerElementalStats.get(stat).get(elementLocation).multiply(BigDecimal.ONE.add(modifier)));
			}
		}
		for (var system : this.playerSystemStats.keySet()) {
			for (var stat : this.playerSystemStats.get(system).keySet()) {
				metaData.put(system.name().toLowerCase() + "-stat-" + stat.name().toLowerCase(), this.playerSystemStats.get(system).get(stat).multiply(BigDecimal.ONE.add(modifier)));
			}
		}
		for (var system : this.playerSystemElementalStats.keySet()) {
			for (var stat : this.playerSystemElementalStats.get(system).keySet()) {
				for (var elementLocation : this.playerSystemElementalStats.get(system).get(stat).keySet()) {
					metaData.put(system.name().toLowerCase() + "-" + elementLocation.toString().toLowerCase() + "-stat-" + stat.name().toLowerCase(), this.playerSystemElementalStats.get(system).get(stat).get(elementLocation).multiply(BigDecimal.ONE.add(modifier)));
				}
			}
		}
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

	@Override
	public void notConsumed(HashMap<String, Object> metaData) {
	}

}
