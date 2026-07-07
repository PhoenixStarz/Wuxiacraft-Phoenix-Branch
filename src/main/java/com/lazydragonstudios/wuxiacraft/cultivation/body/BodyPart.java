package com.lazydragonstudios.wuxiacraft.cultivation.body;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;

public class BodyPart implements StatsContainer, BodyStatsContainer {

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> elementalStats = new HashMap<>();

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> systemElementalStats = new HashMap<>();

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> systemStats = new HashMap<>();

	private ResourceLocation elementalAffinity;

	/**
	 * General location of this body part
	 */
	private BodyPartGroup group;

	private ResourceLocation isRelatedToPart;

	/**
	 * The type of part this is
	 */
	private BodyPartType type;

	public BodyPart(BodyPartGroup group, BodyPartType type) {
		this.group = group;
		this.type = type;
	}
	
	public BodyPartGroup getGroup() {
		return this.group;
	}	
	
	public BodyPartType getType() {
		return this.type;
	}
	public ResourceLocation getElementalAffinity() {
		return elementalAffinity;
	}

	public BodyPart setElementalAffinity(ResourceLocation elementalAffinity) {
		this.elementalAffinity = elementalAffinity;
		return this;
	}

	public ResourceLocation getIsRelatedToPart() {
		return isRelatedToPart;
	}

	public BodyPart setIsRelatedToPart(ResourceLocation isRelatedToPart) {
		this.isRelatedToPart = isRelatedToPart;
		return this;
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

	@NotNull
	@Override
	public BigDecimal getStat(PlayerStat stat) {
		return StatsContainer.super.getStat(stat).add(this.type.getStat(stat));
	}

	@NotNull
	@Override
	public BigDecimal getStat(PlayerElementalStat stat, ResourceLocation elementLocation) {
		return StatsContainer.super.getStat(stat, elementLocation).add(this.type.getStat(stat, elementLocation));
	}

	@NotNull
	@Override
	public BigDecimal getStat(System system, PlayerSystemStat stat) {
		return StatsContainer.super.getStat(system, stat).add(this.type.getStat(system, stat));
	}

	@NotNull
	@Override
	public BigDecimal getStat(System system, ResourceLocation elementLocation, PlayerSystemElementalStat stat) {
		return StatsContainer.super.getStat(system, elementLocation, stat).add(this.type.getStat(system, elementLocation, stat));
	}
}
