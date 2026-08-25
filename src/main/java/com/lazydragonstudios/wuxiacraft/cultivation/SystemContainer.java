package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.*;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.TechniqueContainer;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMobEffects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class SystemContainer {

	/**
	 * The Cultivation system this data belongs to
	 */
	public final System system;

	/**
	 * The current stage id for this cultivation
	 */
	public ResourceLocation currentStage;

	/**
	 * Holds all specific stats of a system
	 */
	protected final HashMap<PlayerSystemStat, BigDecimal> systemStats;

	/**
	 * Holds all specific stats of a system for each element
	 */
	protected final HashMap<PlayerSystemElementalStat, HashMap<ResourceLocation, BigDecimal>> systemElementalStats;

	/**
	 * Holds all the technique data
	 */
	public final TechniqueContainer techniqueData;

	/**
	 * The constructor for this system cultivation stats
	 *
	 * @param system the system this belongs to
	 */
	public SystemContainer(System system) {
		this.system = system;
		this.currentStage = system.defaultStage;
		this.systemStats = new HashMap<>();
		this.systemElementalStats = new HashMap<>();
		techniqueData = new TechniqueContainer(this.system);
		for (var stat : PlayerSystemStat.values()) {
			this.systemStats.put(stat, stat.defaultValue);
			if (stat == PlayerSystemStat.ENERGY) {
				if (this.system == System.DIVINE) {
					this.systemStats.put(stat, new BigDecimal("10"));
				} else if (this.system == System.BODY) {
					this.systemStats.put(stat, new BigDecimal("7"));
				}
			}
		}
	}

	public void addCultivationBase(Player player, ICultivation cultivation, BigDecimal amount, HashMap<ResourceLocation, BigDecimal> elementHash) {
		if (system == System.ESSENCE)
		cultivation.getSystemData(System.DIVINE).consumeEnergy(amount.multiply(new BigDecimal("0.3")));
		//all initialized data so that orders can change around
		var elements = this.techniqueData.modifier.elements;
		amount = this.handleCultivationBaseModifiers(player, cultivation, amount);
		//Adds foundation and comprehension
		for (var elementLocation : elements.keySet()) {
			BigDecimal modifier = BigDecimal.ONE;
			if (elementHash.keySet().contains(elementLocation)) modifier = elementHash.get(elementLocation);
			cultivation.addStat(system, elementLocation, PlayerSystemElementalStat.FOUNDATION, BigDecimal.valueOf(elements.get(elementLocation) * 0.1).multiply(amount).multiply(modifier));
			cultivation.addStat(elementLocation, PlayerElementalStat.COMPREHENSION, BigDecimal.valueOf(elements.get(elementLocation)).multiply(modifier));
		}
		this.handleAspectProficiencyGain(player, cultivation, amount, elementHash);
		//applies spiritual resonance
		if (system == System.ESSENCE && player.hasEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get())) {
			var instance = player.getEffect(WuxiaMobEffects.SPIRITUAL_RESONANCE.get());
			if (instance != null) {
				var amplifier = instance.getAmplifier();
				//amount = amount * (1 + (2 ^ amplifier))
				amount = amount.multiply(BigDecimal.ONE.add(new BigDecimal("2").pow(amplifier)));
			}
		}
		var cultSpeed = cultivation.getStat(system, PlayerSystemStat.CULTIVATION_SPEED);
		amount = amount.add(cultSpeed);
		//adds the base
		cultivation.addStat(system, PlayerSystemStat.CULTIVATION_BASE, amount);
	}

	//Adds aspect proficiency
	public void handleAspectProficiencyGain(Player player, ICultivation cultivation, BigDecimal amount, HashMap<ResourceLocation, BigDecimal> elementHash) {
		var grid = this.techniqueData.grid.getGrid();
		var aspects = cultivation.getAspects();
		//Applies Enlightenment
		if (player.hasEffect(WuxiaMobEffects.ENLIGHTENMENT.get())) {
			var instance = player.getEffect(WuxiaMobEffects.ENLIGHTENMENT.get());
			if (instance != null) {
				int amplifier = instance.getAmplifier();
				//amount = amount * (1.05 ^ amplifier+1))
				amount = amount.multiply(new BigDecimal(1.05).pow(amplifier+1));
			}
		}
		for (var aspectLocation : grid.values()) {
			var aspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspectLocation);
			BigDecimal modifier = BigDecimal.ONE;
			for (var elementKey : elementHash.keySet()) {
				if (aspect instanceof ElementalGenerator apsectE && apsectE.element.equals(elementKey)) modifier = elementHash.get(elementKey);
				else if (aspect instanceof ElementalConverter apsectE && apsectE.element.equals(elementKey)) modifier = elementHash.get(elementKey);
				else if (aspect instanceof ElementalConsumer apsectE && apsectE.element.equals(elementKey)) modifier = elementHash.get(elementKey);				
			}
			aspects.addAspectProficiency(aspectLocation, amount.multiply(modifier), cultivation);
		}
		this.techniqueData.grid.fixProficiencies(aspects);
	}

	//applies config modifers
	public BigDecimal handleCultivationBaseModifiers(Player player, ICultivation cultivation, BigDecimal amount) {
		amount = amount.multiply(BigDecimal.valueOf(WuxiaConfigs.CULTIVATION_SPEED_MULTIPLIER.get()));
		Map<ResourceKey<Level>, Double> multiplierMap = WuxiaConfigs.getDimensionMultipliers();
		ResourceKey<Level> currentDim = player.level().dimension();
		if (multiplierMap.containsKey(currentDim)) {
    		amount = amount.multiply(BigDecimal.valueOf(multiplierMap.get(currentDim)));
		}
		String AFKS = WuxiaConfigs.AFK_SYSTEM.get();
		int AFKtimer = cultivation.getStat(PlayerStat.CULTPOINT).intValue();
		BigDecimal AFKmulti = BigDecimal.ZERO;
		int cheeseburger = 0; //<-- just a little thing to stop config mistypes from stoping cultivation
		if (AFKS.equals("enabled") || AFKS.equals("enabled+") || AFKS.equals("detrimental")) {
			if (AFKtimer >= 2000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 4000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 6000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 8000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 10000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			cheeseburger++;
		}
		if (AFKS.equals("enabled") || AFKS.equals("enabled+") || AFKS.equals("beneficial") || AFKS.equals("beneficial+")) {
			if (AFKtimer >= 11000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 12000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 13000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 14000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			if (AFKtimer >= 15000) AFKmulti = AFKmulti.add(new BigDecimal("0.2"));
			cheeseburger++;
		}
		if (AFKS.equals("enabled+") || AFKS.equals("beneficial+")) {
			if (AFKtimer > 15000) AFKmulti = AFKmulti.add(BigDecimal.valueOf((AFKtimer-15000)/10000));
		}
		if (cheeseburger == 0) AFKmulti = BigDecimal.ONE;
		amount = amount.multiply(AFKmulti);
		cultivation.setStat(PlayerStat.CULTPOINT, cultivation.getStat(PlayerStat.CULTPOINT).subtract(BigDecimal.TEN).max(BigDecimal.ZERO));
		return amount;
	}

	@Nonnull
	BigDecimal getStat(PlayerSystemStat stat) {
		return this.systemStats.getOrDefault(stat, BigDecimal.ZERO);
	}

	@Nonnull
	BigDecimal getStat(PlayerSystemElementalStat stat, ResourceLocation elementLocation) {
		return this.systemElementalStats.getOrDefault(stat, new HashMap<>()).getOrDefault(elementLocation, BigDecimal.ZERO);
	}

	/**
	 * Gets the value of the player stats in this system by getting it from stage, foundation and technique modifier
	 * This is not saved because this should just be used for the cultivation class to store its value
	 *
	 * @param stat the stat to be queried
	 * @return the stat value with all modifiers
	 */
	@Nonnull
	public BigDecimal getStat(PlayerStat stat) {
		BigDecimal statValue = this.getStage().getStat(stat);
		//this looks like statsValue = statsValue * (1 + techniqueModifier)
		statValue = statValue.multiply(BigDecimal.ONE.add(this.techniqueData.modifier.getStat(stat)));
		if (this.getStage() instanceof EssenceCultivationStage essenceStage && !essenceStage.isCanHaveBarrier()) {
			if (stat == PlayerStat.BARRIER || stat == PlayerStat.MAX_BARRIER) return BigDecimal.ZERO;
		}
		if (!this.systemElementalStats.containsKey(PlayerSystemElementalStat.FOUNDATION)) return statValue;
		for (var elementLocation : this.systemElementalStats.get(PlayerSystemElementalStat.FOUNDATION).keySet()) {
			if (!this.systemElementalStats.get(PlayerSystemElementalStat.FOUNDATION).containsKey(elementLocation)) continue;
			var element = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
			if (element == null) continue;
			var foundation = this.systemElementalStats.get(PlayerSystemElementalStat.FOUNDATION).get(elementLocation);
			statValue = statValue.add(element.getFoundationStatValue(stat, foundation));
		}
		return statValue;
	}

	/**
	 * Gets the value of the player stats in this system by getting it from stage, foundation and technique modifier
	 * This is not saved because this should just be used for the cultivation class to store its value
	 *
	 * @param stat            the stat to be queried
	 * @param elementLocation the element of the stat
	 * @return the stat value with all modifiers
	 */
	@Nonnull
	BigDecimal getStat(PlayerElementalStat stat, ResourceLocation elementLocation) {
		BigDecimal statValue = this.getStage().getStat(stat, elementLocation);
		//this looks like statsValue = statsValue * (1 + techniqueModifier)
		statValue = statValue.multiply(BigDecimal.ONE.add(this.techniqueData.modifier.getStat(stat, elementLocation)));
		if (!this.systemElementalStats.containsKey(PlayerSystemElementalStat.FOUNDATION)) return statValue;
		for (var foundationElement : this.systemElementalStats.get(PlayerSystemElementalStat.FOUNDATION).keySet()) {
			var element = WuxiaRegistries.ELEMENTS.get().getValue(foundationElement);
			if (element == null) continue;
			var foundation = this.systemElementalStats.get(PlayerSystemElementalStat.FOUNDATION).get(foundationElement);
			statValue = statValue.add(element.getFoundationStatValue(stat, elementLocation, foundation));
		}
		return statValue;
	}

	/**
	 * @return the current cultivation realm this cultivation is at
	 */
	public CultivationRealm getRealm() {
		return WuxiaRegistries.CULTIVATION_REALMS.get().getValue(this.getStage().realm);
	}

	/**
	 * @return the current cultivation stage this cultivation is at
	 */
	public CultivationStage getStage() {
		return WuxiaRegistries.CULTIVATION_STAGES.get().getValue(this.currentStage);
	}

	void setStat(PlayerSystemStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.systemStats.put(stat, value.max(BigDecimal.ZERO).setScale(6, RoundingMode.HALF_UP));

	}

	void setStat(ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.systemElementalStats.putIfAbsent(stat, new HashMap<>());
		this.systemElementalStats.get(stat).put(element, value.max(BigDecimal.ZERO).setScale(6, RoundingMode.HALF_UP));
	}

	void addStat(PlayerSystemStat stat, BigDecimal value) {
		this.setStat(stat, this.getStat(stat).add(value));
		if (stat == PlayerSystemStat.CULTIVATION_BASE) {
			//this cult_base = min (max_cult_base, cult_base)
			this.setStat(stat, this.getStat(PlayerSystemStat.MAX_CULTIVATION_BASE)
					.min(this.getStat(PlayerSystemStat.CULTIVATION_BASE)));
		}
	}

	void addStat(ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value) {
		if (stat == PlayerSystemElementalStat.FOUNDATION) this.addFoundation(element, value);
		else this.setStat(element, stat, this.getStat(stat, element).add(value));
	}

	private void addFoundation(ResourceLocation elementLocation, BigDecimal value) {
		var element = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
		if (element == null) return;
		var foundationUsed = value.multiply(new BigDecimal("0.5"));
		for (var foundationElementLocation : this.getElementsForStat(PlayerSystemElementalStat.FOUNDATION)) {
			if (value.compareTo(BigDecimal.ZERO) <= 0) continue; //if value is drained already
			var foundationElement = WuxiaRegistries.ELEMENTS.get().getValue(foundationElementLocation);
			if (foundationElement == null) continue; //if not found element
			BigDecimal foundationValue = this.getStat(PlayerSystemElementalStat.FOUNDATION, foundationElementLocation);
			if (foundationValue.compareTo(BigDecimal.ZERO) <= 0)
				continue; //if foundation is == 0, in case there is no foundation, get stat returns 0 if not found element
			var consumedFoundation = false;
			BigDecimal usedValue = foundationUsed.min(foundationValue);
			if (foundationElement.begetsElement(elementLocation)) {
				value = value.add(usedValue.multiply(BigDecimal.valueOf(2)));
				consumedFoundation = true;
			} else if (foundationElement.suppressesElement(elementLocation)) {
				value = value.subtract(usedValue);
				consumedFoundation = true;
			}
			if (consumedFoundation) {
				this.setStat(foundationElementLocation, PlayerSystemElementalStat.FOUNDATION,
						this.getStat(PlayerSystemElementalStat.FOUNDATION, foundationElementLocation).subtract(usedValue));
			}
		}
		var maxCultivationBase = this.getStat(PlayerSystemStat.MAX_CULTIVATION_BASE).multiply(BigDecimal.valueOf(2));
		var foundationInElement = this.getStat(PlayerSystemElementalStat.FOUNDATION, elementLocation);
		MathContext mc = new MathContext(8, RoundingMode.HALF_UP);
		Double foundationGainSpeed = 1d;
		if (value.compareTo(BigDecimal.ZERO) > 0) {
			if (foundationInElement.compareTo(maxCultivationBase) > 0) {
				foundationGainSpeed = maxCultivationBase.doubleValue() / (foundationInElement.doubleValue()*2d);
			}
		}
		this.setStat(elementLocation, PlayerSystemElementalStat.FOUNDATION,
				foundationInElement.add(value.multiply(new BigDecimal(foundationGainSpeed), mc).max(BigDecimal.ZERO)));
	}

	public boolean hasEnergy(BigDecimal amount) {
		return this.getStat(PlayerSystemStat.ENERGY).compareTo(amount) >= 0;
	}

	public boolean consumeEnergy(BigDecimal amount) {
		if (hasEnergy(amount)) {
			this.systemStats.put(PlayerSystemStat.ENERGY, this.getStat(PlayerSystemStat.ENERGY).subtract(amount).setScale(6, RoundingMode.HALF_DOWN));
			return true;
		}
		return false;
	}

	public void addEnergy(BigDecimal amount) {
		this.addStat(PlayerSystemStat.ENERGY, amount);
	}

	public void calculateStats(ICultivation cultivation) {
		for (var stat : PlayerSystemStat.values()) {
			if (stat.isModifiable) continue;
			var value = stat.defaultValue;
			if (this.system == System.ESSENCE && stat == PlayerSystemStat.ENERGY_REGEN) {
				value = BigDecimal.ZERO;
			}
			var stageValue = BigDecimal.ZERO;
			for (var system : System.values()) {
				var systemData = cultivation.getSystemData(system);
				CultivationStage stage = systemData.getStage();
				stageValue = stageValue.add(stage.getStat(this.system, stat));
			}
			var foundationValue = BigDecimal.ZERO;
			for (var elementLocation : this.getElementsForStat(PlayerSystemElementalStat.FOUNDATION)) {
				var foundation = cultivation.getStat(this.system, elementLocation, PlayerSystemElementalStat.FOUNDATION);
				var element = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
				if (element == null) continue;
				if (foundation.compareTo(BigDecimal.ZERO) <= 0) continue;
				foundationValue = foundationValue.add(element.getFoundationStatValue(stat, foundation));
			}
			var techniqueModifier = this.techniqueData.modifier.getSystemStats().get(this.system).getOrDefault(stat, BigDecimal.ZERO);
			//value= value + stageValue + foundationValue
			value = value.add(stageValue).add(foundationValue);
			if (stat == PlayerSystemStat.CULTIVATION_SPEED) {
				//value = value + techModifier
				value = value.add(techniqueModifier);
			} else {
				//value = value * (1 + techModifier)
				value = value.multiply(BigDecimal.ONE.add(techniqueModifier));
			}
			value = value.max(BigDecimal.ZERO);
			this.systemStats.put(stat, value.setScale(6, RoundingMode.HALF_DOWN));
		}
		for (var elementLocation : WuxiaRegistries.ELEMENTS.get().getKeys()) {
			for (var stat : PlayerSystemElementalStat.values()) {
				if (stat.isModifiable) continue;
				var value = BigDecimal.ZERO;
				var stageValue = this.getStage().getStat(this.system, elementLocation, stat);
				var techniqueModifier = this.techniqueData.modifier.getStat(this.system, elementLocation, stat);
				value = value.add(stageValue).multiply(BigDecimal.ONE.multiply(techniqueModifier));
				value = value.max(BigDecimal.ZERO);
				this.systemElementalStats.putIfAbsent(stat, new HashMap<>());
				this.systemElementalStats.get(stat).put(elementLocation, value.setScale(6, RoundingMode.HALF_DOWN));
			}
		}
	}

	public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putString("current_stage", this.currentStage.toString());
		for (var stat : PlayerSystemStat.values()) {
			if (!stat.isModifiable) continue;
			BigDecimal statValue = this.getStat(stat);
			int scale = statValue.scale();
			statValue = statValue.setScale(Math.min(10, scale), RoundingMode.DOWN);
			tag.putString("stat-" + stat.name().toLowerCase(), statValue.toPlainString());
			this.systemStats.put(stat, statValue);
		}
		var systemElementStatsTag = new CompoundTag();
		for (var stat : this.systemElementalStats.keySet()) {
			var currentStatTag = new CompoundTag();
			for (var elementLocation : this.systemElementalStats.get(stat).keySet()) {
				if (!stat.isModifiable) continue;
				BigDecimal statValue = this.systemElementalStats.get(stat).getOrDefault(elementLocation, BigDecimal.ZERO);
				var scale = statValue.scale();
				statValue = statValue.setScale(Math.min(10, scale), RoundingMode.DOWN);
				currentStatTag.putString("element-" + elementLocation.toString(),
						statValue.toPlainString());
				this.systemElementalStats.get(stat).put(elementLocation, statValue);
			}
			systemElementStatsTag.put("elemental-stat-" + stat.name().toLowerCase(), currentStatTag);
		}
		tag.put("technique-data", this.techniqueData.serialize());
		tag.put("elemental-stats", systemElementStatsTag);
		return tag;
	}

	public void deserialize(CompoundTag tag) {
		if (tag.contains("current_stage")) {
			this.currentStage = new ResourceLocation(tag.getString("current_stage"));
		}
		for (var stat : PlayerSystemStat.values()) {
			if (!stat.isModifiable) continue;
			String statName = "stat-" + stat.name().toLowerCase();
			if (tag.contains(statName)) {
				this.systemStats.put(stat, new BigDecimal(tag.getString(statName)));
			} else {
				this.systemStats.put(stat, stat.defaultValue);
			}
		}
		this.systemElementalStats.clear();
		if (tag.contains("elemental-stats")) {
			var rawElementalStatsTag = tag.get("elemental-stats");
			if (rawElementalStatsTag instanceof CompoundTag elementalStatsTag) {
				for (var stat : PlayerSystemElementalStat.values()) {
					if (elementalStatsTag.contains("elemental-stat-" + stat.name().toLowerCase())) {
						var rawElementalStatTag = elementalStatsTag.get("elemental-stat-" + stat.name().toLowerCase());
						if (rawElementalStatTag instanceof CompoundTag elementalStatTag) {
							for (var elementLocation : WuxiaRegistries.ELEMENTS.get().getKeys()) {
								var tagName = "element-" + elementLocation.toString();
								if (elementalStatTag.contains(tagName)) {
									var valueString = elementalStatTag.getString(tagName);
									var amount = new BigDecimal(valueString);
									this.systemElementalStats.putIfAbsent(stat, new HashMap<>());
									this.systemElementalStats.get(stat).put(elementLocation, amount);
								}
							}
						}
					}
				}
			}
		}
		CompoundTag techDataTag = (CompoundTag) tag.get("technique-data");
		if (techDataTag != null) {
			this.techniqueData.deserialize(techDataTag);
		}
	}

	public Set<PlayerSystemElementalStat> getElementalStats() {
		return this.systemElementalStats.keySet();
	}

	public Set<ResourceLocation> getElementsForStat(PlayerSystemElementalStat stat) {
		return this.systemElementalStats.getOrDefault(stat, new HashMap<>()).keySet();
	}
}
