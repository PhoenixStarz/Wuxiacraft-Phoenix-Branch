package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.HashSet;

public class Element implements StatsContainer {

	/**
	 * The skill aspects the cultivator com learn from this aspect
	 */
	public final HashMap<ResourceLocation, BigDecimal> skillAspects;

	/**
	 * All elements that benefit from this one
	 */
	private final HashSet<ResourceLocation> begets;

	private final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> elementalStats = new HashMap<>();

	private final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	/**
	 * All elements that are obstructed by this one
	 */
	private final HashSet<ResourceLocation> suppresses;

	private final HashMap<System, HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>>> systemElementalStats = new HashMap<>();

	/**
	 * Stats that are going to add to the system specific stats
	 */
	private final HashMap<PlayerSystemStat, BigDecimal> systemStatModifier;

	private final HashMap<System, HashMap<PlayerSystemStat, BigDecimal>> systemStats = new HashMap<>();

	/**
	 * Default constructor of element
	 */
	public Element() {
		this.begets = new HashSet<>();
		this.suppresses = new HashSet<>();
		this.systemStatModifier = new HashMap<>();
		this.skillAspects = new HashMap<>();
		this.setStat(PlayerStat.MAX_HEALTH, new BigDecimal("0.02"));
		this.setStat(PlayerStat.STRENGTH, new BigDecimal("0.009"));
		this.setStat(PlayerStat.AGILITY, new BigDecimal("0.0001"));
		this.setStat(PlayerStat.HEALTH_REGEN, new BigDecimal("0.00001"));
		this.setStat(PlayerStat.HEALTH_REGEN_COST, new BigDecimal("0.0000125"));
		this.setStat(PlayerStat.EXERCISE_COST, new BigDecimal("0.00005"));
		this.setStat(PlayerStat.EXERCISE_CONVERSION, new BigDecimal("0.00004"));
		this.setStat(PlayerStat.MAX_BARRIER, new BigDecimal("0.02"));
		this.setStat(System.ESSENCE, PlayerSystemStat.MAX_ENERGY, new BigDecimal("0.012"));
		this.setStat(System.ESSENCE, PlayerSystemStat.ENERGY_REGEN, new BigDecimal("0.000004"));
		this.setStat(PlayerElementalStat.RESISTANCE, new ResourceLocation(WuxiaCraft.MOD_ID, "physical"), new BigDecimal("0.004"));
	}

	public Element addSkill(ResourceLocation aspectLocation, BigDecimal value) {
		this.skillAspects.put(aspectLocation, value);
		return this;
	}

	/**
	 * Adds a begetter element at the construction
	 *
	 * @param element The element to be added
	 * @return This element
	 */
	public Element begets(ResourceLocation element) {
		begets.add(element);
		return this;
	}

	/**
	 * Returns whether element argument benefits from this one
	 *
	 * @param element the element to check against
	 * @return true if element is benefited from this one
	 */
	public boolean begetsElement(ResourceLocation element) {
		return begets.contains(element);
	}

	/**
	 * Calculates the final foundation stat value
	 *
	 * @param stat       the stat to be queried
	 * @param foundation the foundation amount
	 * @return the stat value based on the foundation
	 */
	public BigDecimal getFoundationStatValue(PlayerStat stat, BigDecimal foundation) {
		return calculateStatValue(foundation, this.getStat(stat));
	}

	public static BigDecimal calculateStatValue(BigDecimal foundation, BigDecimal modifier) {
		var mc = new MathContext(10);
		//modifier * anything = 0 so return 0
		if (modifier.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
		return modifier.multiply(foundation.sqrt(mc));
	}

	/**
	 * Calculates the final foundation stat value
	 *
	 * @param stat       the stat to be queried
	 * @param foundation the foundation amount
	 * @return the stat value based on the foundation
	 */
	public BigDecimal getFoundationStatValue(PlayerSystemStat stat, BigDecimal foundation) {
		return calculateStatValue(foundation, this.getStat(System.ESSENCE, stat));
	}

	/**
	 * Calculates the final foundation stat value
	 *
	 * @param stat            the stat to be queried
	 * @param elementLocation the element of the stat
	 * @param foundation      the foundation amount
	 * @return the stat value based on the foundation
	 */
	public BigDecimal getFoundationStatValue(PlayerElementalStat stat, ResourceLocation elementLocation, BigDecimal foundation) {
		return calculateStatValue(foundation, this.getStat(stat, elementLocation));
	}

	/**
	 * @return This element's name
	 */
	public String getName() {
		var registryKey = WuxiaRegistries.ELEMENTS.get().getKey(this);
		if (registryKey == null) return null;
		return registryKey.getPath();
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

	/**
	 * Adds an obstructed element at the construction
	 *
	 * @param element The element to be added
	 * @return This element
	 */
	public Element suppresses(ResourceLocation element) {
		suppresses.add(element);
		return this;
	}

	/**
	 * Returns whether element argument is obstructed by this one
	 *
	 * @param element the element to check against
	 * @return true if element is obstructed by this one
	 */
	public boolean suppressesElement(ResourceLocation element) {
		return suppresses.contains(element);
	}

}
