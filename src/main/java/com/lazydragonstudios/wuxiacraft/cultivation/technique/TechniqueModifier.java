package com.lazydragonstudios.wuxiacraft.cultivation.technique;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.WeaponElementalGenerator;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

public class TechniqueModifier implements StatsContainer {

	public final HashMap<ResourceLocation, Double> elements = new HashMap<>();

	public final HashSet<ResourceLocation> skills = new HashSet<>();

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> elementalStats = new HashMap<>();

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> systemElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> systemStats = new HashMap<>();

	private final HashMap<WeaponElementalGenerator.WeaponType, BigDecimal> weaponStats = new HashMap<>();

	public boolean validTechnique;

	public TechniqueModifier() {
		for (var system : System.values()) {
			this.getSystemStats().put(system, new HashMap<>());
		}
		validTechnique = false;
	}

	public void add(TechniqueModifier tMod) {
		for (var stat : tMod.getPlayerStats().keySet()) {
			this.getPlayerStats().put(stat, this.getPlayerStats().getOrDefault(stat, BigDecimal.ZERO).add(tMod.getPlayerStats().get(stat)));
		}
		for (var stat : tMod.getElementalStats().keySet()) {
			var elementalStats = this.getElementalStats().putIfAbsent(stat, new HashMap<>());
			for (var elementLocation : tMod.getElementalStats().get(stat).keySet()) {
				elementalStats.put(elementLocation,
						elementalStats.getOrDefault(elementLocation, BigDecimal.ZERO).add(tMod.getElementalStats().get(stat).get(elementLocation)));
			}
		}
		for (var system : System.values()) {
			var systemStats = this.getSystemStats().putIfAbsent(system, new HashMap<>());
			for (var stat : tMod.getSystemStats().getOrDefault(system, new HashMap<>()).keySet()) {
				systemStats.put(stat, systemStats.getOrDefault(stat, BigDecimal.ZERO).add(tMod.getSystemStats().get(system).get(stat)));
			}
			for (var stat : tMod.getSystemElementalStats().getOrDefault(system, new HashMap<>()).keySet()) {
				this.getSystemElementalStats().putIfAbsent(system, new HashMap<>());
				var elementalStats = this.getSystemElementalStats().get(system).putIfAbsent(stat, new HashMap<>());
				for (var elementLocation : tMod.getSystemElementalStats().getOrDefault(system, new HashMap<>()).get(stat).keySet()) {
					elementalStats.put(elementLocation, elementalStats.getOrDefault(elementLocation, BigDecimal.ZERO).add(tMod.getSystemElementalStats().getOrDefault(system, new HashMap<>()).get(stat).get(elementLocation)));
				}
			}
		}
		for (var type : tMod.getWeaponStats().keySet()) {
			this.getWeaponStats().put(type, this.getWeaponStats().getOrDefault(type, BigDecimal.ZERO).add(tMod.getWeaponStats().get(type)));
		}
		for (var key : tMod.elements.keySet()) {
			this.elements.put(key, this.elements.getOrDefault(key, 0d) + tMod.elements.get(key));
		}
		this.skills.addAll(tMod.skills);
		this.validTechnique = this.validTechnique || tMod.validTechnique;
	}

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

	public HashMap<WeaponElementalGenerator.WeaponType, BigDecimal> getWeaponStats() {
		return weaponStats;
	}

	/*
			public CompoundTag serialize() {
				var tag = new CompoundTag();
				var playerStatsTag = new ListTag();
				var playerSystemStatsTag = new CompoundTag();
				var elementsTag = new ListTag();
				for (var stat : this.stats.keySet()) {
					var statTag = new CompoundTag();
					statTag.putString("stat-name", stat.name());
					statTag.putString("stat-value", this.stats.get(stat).toPlainString());
					playerStatsTag.add(statTag);
				}
				for (var system : System.values()) {
					var systemStatsTag = new ListTag();
					for (var stat : this.systemStats.get(system).keySet()) {
						var statTag = new CompoundTag();
						statTag.putString("stat-name", stat.name());
						statTag.putString("stat-value", this.systemStats.get(system).get(stat).toPlainString());
						systemStatsTag.add(statTag);
					}
					playerSystemStatsTag.put(system.name().toLowerCase() + "-stat-list", systemStatsTag);
				}
				for (var element : this.elements.keySet()) {
					var elementTag = new CompoundTag();
					elementTag.putString("element-name", element.toString());
					elementTag.putDouble("element-value", this.elements.get(element));
					elementsTag.add(elementTag);
				}
				tag.put("stats-list", playerStatsTag);
				tag.put("elements-list", elementsTag);
				return tag;
			}

			public void deserialize(CompoundTag tag) {
				ListTag playerStatsTag = (ListTag) tag.get("stats-list");
				CompoundTag playerSystemStatsTag = (CompoundTag) tag.get("system-stats-list");
				ListTag elementsTag = (ListTag) tag.get("elements-list");
				if (playerStatsTag != null) {
					for (var rawStatTag : playerStatsTag) {
						if (rawStatTag instanceof CompoundTag statTag) {
							PlayerStat stat = PlayerStat.valueOf(statTag.getString("stat-name"));
							BigDecimal value = new BigDecimal(statTag.getString("stat-value"));
							this.stats.put(stat, value);
						}
					}
				}
				if (playerSystemStatsTag != null) {
					for (var system : System.values()) {
						var systemStatsList = (ListTag) playerSystemStatsTag.get(system.name().toLowerCase() + "-stat-list");
						if (systemStatsList == null) continue;
						for (var rawStatTag : systemStatsList) {
							if (rawStatTag instanceof CompoundTag statTag) {
								PlayerSystemStat stat = PlayerSystemStat.valueOf(statTag.getString("stat-name"));
								BigDecimal value = new BigDecimal(statTag.getString("stat-value"));
								this.systemStats.get(system).put(stat, value);
							}
						}
					}
				}
				if (elementsTag != null) {
					for (var rawElementTag : elementsTag) {
						if (rawElementTag instanceof CompoundTag elementTag) {
							ResourceLocation element = new ResourceLocation(elementTag.getString("element-name"));
							double value = elementTag.getDouble("element-value");
							this.elements.put(element, value);
						}
					}
				}
			}
		*/
	public boolean isValidTechnique() {
		return validTechnique;
	}

	public void setValidTechnique(boolean validTechnique) {
		this.validTechnique = validTechnique;
	}

	public void subtract(TechniqueModifier tMod) {
		for (var stat : tMod.getPlayerStats().keySet()) {
			this.getPlayerStats().put(stat, this.getPlayerStats().getOrDefault(stat, BigDecimal.ZERO).subtract(tMod.getPlayerStats().get(stat)));
		}
		for (var system : System.values()) {
			var systemStats = this.getSystemStats().get(system);
			if (systemStats == null) continue;
			for (var stat : tMod.getSystemStats().get(system).keySet()) {
				systemStats.put(stat, systemStats.getOrDefault(stat, BigDecimal.ZERO).subtract(tMod.getSystemStats().get(system).get(stat)));
			}
		}
		for (var key : tMod.elements.keySet()) {
			this.elements.put(key, this.elements.getOrDefault(key, 0d) - tMod.elements.get(key));
		}
	}
}
