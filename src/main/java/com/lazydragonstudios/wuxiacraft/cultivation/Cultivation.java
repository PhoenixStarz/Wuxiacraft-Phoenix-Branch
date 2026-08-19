package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.capabilities.CultivationProvider;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.AspectContainer;
import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.UUID;

public class Cultivation implements ICultivation {

	/**
	 * Gets a cultivation for a player, or a default cultivation instance if not found any
	 *
	 * @param target A player to retrieve the cultivation from
	 * @return A cultivation instance
	 */
	public static ICultivation get(Player target) {
		var cultOpt = target.getCapability(CultivationProvider.CULTIVATION_PROVIDER).resolve();
		return cultOpt.orElseGet(Cultivation::new);
	}

	public Cultivation() {
		this.systemCultivation = new HashMap<>();
		this.playerStats = new HashMap<>();
		this.playerElementalStats = new HashMap<>();
		this.formationStats = new FormationStatsContainer();
		for (var stat : PlayerStat.values()) {
			this.playerStats.put(stat, stat.defaultValue);
		}
		for (var system : System.values()) {
			SystemContainer systemData;
			if (system == System.BODY) {
				systemData = new BodyCultivationContainer();
			} else 
			if (system == System.DIVINE) {
				systemData = new DivineCultivationContainer();
			} else {
				systemData = new SystemContainer(system);
			}
			this.systemCultivation.put(system, systemData);
		}
		this.aspects = new AspectContainer();
		this.skills = new SkillContainer();
		this.exercising = false;
		this.combat = false;
		this.isDivineSense = false;
		this.ToD = 0L;
		this.rebirths = 0;
		this.demonicStage = 0;
		this.tribulating = false;
    	this.tribulation = new Tribulation(1, 1, 1, System.ESSENCE);
		this.formationCore = null;
		this.barrierFormationCore = null;
		this.extraHealthFromAttributes = 0.0;
		this.masterDiscipleContainer = new MasterDiscipleContainer();
	}

	/**
	 * Stats added when inside a formation
	 */
	private final FormationStatsContainer formationStats;

	/**
	 * A storage unit for the master disciple relationship
	 */
	private final MasterDiscipleContainer masterDiscipleContainer;

	private final HashMap<ResourceLocation, HashMap<PlayerElementalStat, BigDecimal>> playerElementalStats;

	/**
	 * Player specific stats
	 */
	private final HashMap<PlayerStat, BigDecimal> playerStats;

	/**
	 * Known Aspects and proficiency
	 */
	public AspectContainer aspects;

	/**
	 * whether the player is in combat mode
	 */
	public boolean combat;

	/**
	 * whether the player tribulating
	 */
	public boolean tribulating;

	/**
	 * the skill data for this character
	 */
	public SkillContainer skills;

	/**
	 * The cultivation information for each system
	 */
	public HashMap<System, SystemContainer> systemCultivation;

	/**
	 * A fraction of the power being outputted
	 */
	private double agilityRegulator;

	/**
	 * this is for the server and client to convert body energy into essence energy
	 */
	private boolean exercising;

	/**
	 * this formation core block position
	 */
	@Nullable
	private BlockPos formationCore;

	/**
	 * formation stored in players Formation Barrier Badge
	 */
	@Nullable
	private BlockPos barrierFormationCore;

	/**
	 * is divine sense on
	 */
	private boolean isDivineSense;

	/**
	 * A fraction of the speed being used
	 */
	private double strengthRegulator;

	/**
	 * this is for sync with the client and probably vice versa
	 * a substitute for this could've been entity.ticksAlive
	 * but that is not among us anymore
	 */
	private int tickTimer;
	
	private int cultTimer;

	/**
	 * time of day/ game time
	 */
	private long ToD;

	/**
	 * amount of rebirths
	 */
	private int rebirths;

	/**
	 * stage of demonic corruption
	 */
	private int demonicStage;

	/**
	 * Tribulation stuff
	 */	
	private boolean isTribulating = false;

	public Tribulation tribulation;


	/**
	 * An internal variable updated every tick to check if within formation range to add stats
	 */
	private boolean withinFormationRange;

	/**
	 * An extra health modifier to change player's max hp based on other mods max hp
	 */
	private double extraHealthFromAttributes;

	@Nullable
	private UUID sectId;

	@Nullable
	@Override
	public ResourceLocation getBodyTransformation() {
		return ((BodyCultivationContainer) this.getSystemData(System.BODY)).getBodyTransformation();
	}

	public void setBodyTransformation(ResourceLocation bodyTransformation) {
		var bodyData = (BodyCultivationContainer) this.getSystemData(System.BODY);
		bodyData.setDisplayTransformation(bodyTransformation);
	}

	@Override
	public void setStat(PlayerStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.playerStats.put(stat, value.max(BigDecimal.ZERO).setScale(6, RoundingMode.HALF_UP));
	}

	@Override
	public void setStat(ResourceLocation element, PlayerElementalStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.playerElementalStats.putIfAbsent(element, new HashMap<>());
		this.playerElementalStats.get(element).put(stat, value.max(BigDecimal.ZERO).setScale(6, RoundingMode.HALF_UP));
	}

	@Override
	public void setFormation(@Nullable BlockPos blockPos) {
		this.formationCore = blockPos;
	}

	@Override
	public void setBarrierFormation(@Nullable BlockPos blockPos) {
		this.barrierFormationCore = blockPos;
	}

	@Override
	public void setStat(System system, PlayerSystemStat stat, BigDecimal value) {
		this.getSystemData(system).setStat(stat, value);
	}

	@Override
	@Nullable
	public BlockPos getFormation() {
		return this.formationCore;
	}
	
	@Override
	@Nullable
	public BlockPos getBarrierFormation() {
		return this.barrierFormationCore;
	}

	@Override
	public void setStat(System system, ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value) {
		this.getSystemData(system).setStat(element, stat, value);
	}

	@Override
	public void addStat(PlayerStat stat, BigDecimal value) {
		this.setStat(stat, this.getStat(stat).add(value));
	}

	@Override
	public void addStat(ResourceLocation element, PlayerElementalStat stat, BigDecimal value) {
		this.setStat(element, stat, this.getStat(element, stat).add(value));
	}

	@Override
	public void addStat(System system, PlayerSystemStat stat, BigDecimal value) {
		this.getSystemData(system).addStat(stat, value);
	}

	@Override
	public void addStat(System system, ResourceLocation element, PlayerSystemElementalStat stat, BigDecimal value) {
		this.getSystemData(system).addStat(element, stat, value);
	}

	@Override
	@Nullable
	public UUID getSectId() {
		return sectId;
	}

	@Override
	public void setSectId(@Nullable UUID sectId) {
		this.sectId = sectId;
	}

	@Override
	public SystemContainer getSystemData(System system) {
		return systemCultivation.get(system);
	}

	@Override
	public double getExtraHealthFromAttributes() {
		return extraHealthFromAttributes;
	}

	@Override
	public void setExtraHealthFromAttributes(double extraHealthFromAttributes) {
		this.extraHealthFromAttributes = extraHealthFromAttributes;
	}

	@Override
	public void addCultivationBase(Player player, System system, BigDecimal amount) {
		CultivatingEvent event = new CultivatingEvent(player, system, amount);
		if (MinecraftForge.EVENT_BUS.post(event)) return;
		var systemData = this.getSystemData(system);
		systemData.addCultivationBase(player, this, event.getAmount(), event.getElement());
	}

	@Override
	public HashMap<ResourceLocation, HashMap<PlayerElementalStat, BigDecimal>> getElementalStats() {
		return this.playerElementalStats;
	}

	@Override
	public boolean attemptBreakthrough(System system) {
		var systemData = this.getSystemData(system);
		var initialStage = systemData.currentStage;
		var cultBase = this.getStat(system, PlayerSystemStat.CULTIVATION_BASE);
		var maxCultBase = this.getStat(system, PlayerSystemStat.MAX_CULTIVATION_BASE);
		if (cultBase.compareTo(maxCultBase) < 0) return false;
		var stage = systemData.getStage();
		if (stage.nextStage == null) return false;
		systemData.currentStage = stage.nextStage;
		systemData.setStat(PlayerSystemStat.CULTIVATION_BASE, BigDecimal.ZERO);
		this.setStat(PlayerStat.LIVES, this.getStat(PlayerStat.LIVES).add(BigDecimal.ONE).min(this.getStat(PlayerStat.MAX_LIVES)));
		return !systemData.currentStage.equals(initialStage);
	}

	@Override
	public boolean attemptRebirth() {
		ICultivation newCultivation = new Cultivation();
		newCultivation.setRebirths(this.getRebirths()+1);
		this.deserialize(newCultivation.serialize());
		return newCultivation.getRebirths() == this.getRebirths();
	}

	@Override
	public void calculateStats() {
		for (var system : System.values()) {
			var systemData = this.getSystemData(system);
			systemData.calculateStats(this);
		}
		for (var stat : PlayerStat.values()) {
			if (stat.isModifiable) continue;
			var statValue = stat.defaultValue;
			for (var system : System.values()) {
				statValue = statValue.add(this.getSystemData(system).getStat(stat));
			}
			if(stat == PlayerStat.MAX_HEALTH) {
				statValue = statValue.add(BigDecimal.valueOf(extraHealthFromAttributes));
			} else
			if(stat == PlayerStat.REBIRTHS) {
				statValue = new BigDecimal(this.getRebirths());
			} else
			if(stat == PlayerStat.MAX_LIVES) {
				statValue = statValue.add(new BigDecimal(this.getRebirths()));
			}
			statValue = statValue.max(BigDecimal.ZERO);
			this.playerStats.put(stat, statValue.setScale(6, RoundingMode.HALF_DOWN));
		}
		for (var elementLocation : WuxiaRegistries.ELEMENTS.get().getKeys()) {
			for (var stat : PlayerElementalStat.values()) {
				//small cleaning, if the value is zero on the stat value then remove from the memory
				if (this.playerElementalStats.containsKey(elementLocation)) {
					if (this.playerElementalStats.get(elementLocation).getOrDefault(stat, BigDecimal.ZERO).compareTo(BigDecimal.ZERO) <= 0) {
						this.playerElementalStats.get(elementLocation).remove(stat);
					}
				}
				if (stat.isModifiable) continue;
				var statValue = BigDecimal.ZERO;
				for (var system : System.values()) {
					var systemData = this.getSystemData(system);
					statValue = statValue.add(systemData.getStat(stat, elementLocation));
				}
				if (statValue.compareTo(BigDecimal.ZERO) > 0) {
					this.playerElementalStats.putIfAbsent(elementLocation, new HashMap<>());
					this.playerElementalStats.get(elementLocation).put(stat, statValue.setScale(6, RoundingMode.HALF_DOWN));
				}
			}
		}
		this.skills.knownSkills.clear();
		for (var elementLocation : this.playerElementalStats.keySet()) {
			var element = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
			if (element == null) continue;
			if (this.playerElementalStats.get(elementLocation).containsKey(PlayerElementalStat.COMPREHENSION)) {
				var comprehension = this.playerElementalStats.get(elementLocation).get(PlayerElementalStat.COMPREHENSION);
				for (var skillAspect : element.skillAspects.keySet()) {
					var comprehensionRequired = element.skillAspects.get(skillAspect);
					if (comprehension.compareTo(comprehensionRequired) < 0) continue;
					this.skills.knownSkills.add(skillAspect);
				}
			}
		}
		for (var system : System.values()) {
			var systemData = this.getSystemData(system);
			this.skills.knownSkills.addAll(systemData.techniqueData.modifier.skills);
			this.skills.knownSkills.addAll(systemData.getStage().getSkillsAspects());
		}
	}

	@Override
	public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		for (var stat : this.playerStats.keySet()) {
			if (!stat.isModifiable) continue;
			BigDecimal statValue = this.playerStats.get(stat);
			int scale = statValue.scale();
			statValue = statValue.setScale(Math.min(10, scale), RoundingMode.DOWN);
			tag.putString("stat-" + stat.name().toLowerCase(), statValue.toPlainString());
			this.playerStats.put(stat, statValue);
		}
		var elementStatsTag = new CompoundTag();
		for (var element : this.playerElementalStats.keySet()) {
			var currentElementStatsTag = new CompoundTag();
			for (var stat : PlayerElementalStat.values()) {
				if (!stat.isModifiable) continue;
				BigDecimal statValue = this.playerElementalStats.get(element).getOrDefault(stat, BigDecimal.ZERO);
				int scale = statValue.scale();
				statValue = statValue.setScale(Math.min(10, scale), RoundingMode.DOWN);
				currentElementStatsTag.putString("stat-" + stat.name().toLowerCase(),
						statValue.toPlainString());
				this.playerElementalStats.get(element).put(stat, statValue);
			}
			elementStatsTag.put("element-stats-" + element, currentElementStatsTag);
		}
		tag.put("elemental-stats", elementStatsTag);
		tag.put("body-data", getSystemData(System.BODY).serialize());
		tag.put("divine-data", getSystemData(System.DIVINE).serialize());
		tag.put("essence-data", getSystemData(System.ESSENCE).serialize());
		tag.put("aspect-data", this.aspects.serialize());
		tag.put("skills-data", this.skills.serialize());
		if (this.formationCore != null) {
			var formationTag = new CompoundTag();
			formationTag.putInt("x", this.formationCore.getX());
			formationTag.putInt("y", this.formationCore.getY());
			formationTag.putInt("z", this.formationCore.getZ());
			tag.put("formation", formationTag);
		}
		if (this.barrierFormationCore != null) {
			var formationTag = new CompoundTag();
			formationTag.putInt("x", this.barrierFormationCore.getX());
			formationTag.putInt("y", this.barrierFormationCore.getY());
			formationTag.putInt("z", this.barrierFormationCore.getZ());
			tag.put("barrier-formation", formationTag);
		}
		tag.put("master-disciple", this.masterDiscipleContainer.serialize());
		tag.putBoolean("combat-mode", this.isCombat());
		tag.putLong("time-of-day", this.getToD());
		tag.putInt("rebirths", this.getRebirths());
		tag.putInt("demonic-stage", this.getDemonicStage());
		tag.putBoolean("tribulating", this.isTribulating());
		tag.put("tribulation-data", this.tribulation.serialize());
		var regulatorsTag = new CompoundTag();
		regulatorsTag.putDouble("strength", this.strengthRegulator);
		regulatorsTag.putDouble("agility", this.agilityRegulator);
		tag.put("regulators", regulatorsTag);
		if(this.sectId != null) tag.putUUID("sect-id", sectId);
		return tag;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		for (var stat : this.playerStats.keySet()) {
			if (!stat.isModifiable) continue;
			if (tag.contains("stat-" + stat.name().toLowerCase())) {
				this.playerStats.put(stat, new BigDecimal(tag.getString("stat-" + stat.name().toLowerCase())));
			} else {
				this.playerStats.put(stat, new BigDecimal("0"));
			}
		}
		this.playerElementalStats.clear();
		if (tag.contains("elemental-stats")) {
			var rawElementalStatsTag = tag.get("elemental-stats");
			if (rawElementalStatsTag instanceof CompoundTag elementalStatsTag) {
				for (var element : WuxiaRegistries.ELEMENTS.get().getKeys()) {
					if (elementalStatsTag.contains("element-stats-" + element)) {
						for (var stat : PlayerElementalStat.values()) {
							if (!stat.isModifiable) continue;
							CompoundTag elementTag = elementalStatsTag.getCompound("element-stats-" + element);
							if (elementTag.contains("stat-" + stat.name().toLowerCase())) {
								var value = elementTag.getString("stat-" + stat.name().toLowerCase());
								this.playerElementalStats.putIfAbsent(element, new HashMap<>());
								this.playerElementalStats.get(element).put(stat, new BigDecimal(value));
							}
						}
					}
				}
			}
		}
		if (tag.contains("body-data")) {
			getSystemData(System.BODY).deserialize(tag.getCompound("body-data"));
		}
		if (tag.contains("divine-data")) {
			getSystemData(System.DIVINE).deserialize(tag.getCompound("divine-data"));
		}
		if (tag.contains("essence-data")) {
			getSystemData(System.ESSENCE).deserialize(tag.getCompound("essence-data"));
		}
		if (tag.contains("aspect-data")) {
			this.aspects.deserialize(tag.getCompound("aspect-data"), this);
		}
		if (tag.contains("skills-data")) {
			this.skills.deserialize(tag.getCompound("skills-data"), this);
		}
		if (tag.contains("formation")) {
			var formationTag = tag.getCompound("formation");
			int x = formationTag.getInt("x");
			int y = formationTag.getInt("y");
			int z = formationTag.getInt("z");
			this.formationCore = new BlockPos(x, y, z);
		} else {
			this.formationCore = null;
		}
		if (tag.contains("barrier-formation")) {
			var formationTag = tag.getCompound("barrier-formation");
			int x = formationTag.getInt("x");
			int y = formationTag.getInt("y");
			int z = formationTag.getInt("z");
			this.barrierFormationCore = new BlockPos(x, y, z);
		} else {
			this.barrierFormationCore = null;
		}
		if (tag.contains("regulators")) {
			var regulatorsTag = tag.getCompound("regulators");
			if (regulatorsTag.contains("strength")) {
				this.strengthRegulator = regulatorsTag.getDouble("strength");
			}
			if (regulatorsTag.contains("agility")) {
				this.agilityRegulator = regulatorsTag.getDouble("agility");
			}
		}
		if (tag.contains("master-disciple")) {
			this.masterDiscipleContainer.deserialize(tag.getCompound("masterDisciple"));
		}
		if (tag.contains("combat-mode")) {
			this.setCombat(tag.getBoolean("combat-mode"));
		}
		if (tag.contains("time-of-day")) {
			this.setToD(tag.getLong("time-of-day"));
		}
		if (tag.contains("rebirths")) {
			this.setRebirths(tag.getInt("rebirths"));
		}
		if (tag.contains("demonic-stage")) {
			this.setDemonicStage(tag.getInt("demonic-stage"));
		}
		if (tag.contains("tribulating")) {
			this.setTribulating(tag.getBoolean("tribulating"));
		}
		if (tag.contains("tribulation-data")) {
			this.tribulation.deserialize(tag.getCompound("tribulation-data"));
		}
		this.sectId = null;
		if(tag.contains("sect-id")) {
			this.sectId = tag.getUUID("sect-id");
		}
		calculateStats();
	}

	@Override
	public boolean isExercising() {
		return exercising;
	}

	@Override
	public void setExercising(boolean exercising) {
		this.exercising = exercising;
	}

	@Override
	public AspectContainer getAspects() {
		return aspects;
	}

	@Override
	public SkillContainer getSkills() {
		return skills;
	}

	@Override
	public boolean isCombat() {
		return combat;
	}

	@Override
	public void setCombat(boolean combat) {
		this.combat = combat;
	}

	@Override
	public boolean isTribulating() {
		return tribulating;
	}
	
	@Override
	public void setTribulating(boolean tribulating) {
		this.tribulating = tribulating;
	}

	@Override
	public Tribulation getTribulation() {
		return tribulation;
	}
	
	@Override
	public void setTribulation(Tribulation tribulation) {
		this.tribulation = tribulation;
	}


	/**
	 * Utility to increment to the tick timer
	 */
	@Override
	public void advanceTimer() {
		this.tickTimer++;
	}

	/**
	 * Utility to reset timer.
	 * Should only be used when a sync message is sent
	 */
	@Override
	public void resetTimer() {
		this.tickTimer = 0;
	}

	/**
	 * @return the time ticker. It's just for not exposing the ticker.
	 */
	@Override
	public int getTimer() {
		return this.tickTimer;
	}
	
	//	//	//	//	//	//	//	//	//	//	//	//
	@Override
	public void advanceCultTimer() {
		this.cultTimer++;
	}

	@Override
	public void resetCultTimer() {
		this.cultTimer = 0;
	}

	@Override
	public int getCultTimer() {
		return this.cultTimer;
	}
	
	@Override
	public void setToD(long amount) {
		this.ToD = amount;
	}

	@Override
	public long getToD() {
		return this.ToD;
	}

	@Override
	public void setRebirths(int amount) {
		this.rebirths = amount;
	}

	@Override
	public int getRebirths() {
		return this.rebirths;
	}

	@Override
	public void setDemonicStage(int amount) {
		this.demonicStage = amount;
	}

	@Override
	public int getDemonicStage() {
		return this.demonicStage;
	}

	@Override
	public boolean isDivineSense() {
		return isDivineSense;
	}

	@Override
	public void setDivineSense(boolean divineSense) {
		isDivineSense = divineSense;
	}

	@Override
	public boolean isWithinFormationRange() {
		return withinFormationRange;
	}

	@Override
	public void setWithinFormationRange(double x, double y, double z) {
		this.withinFormationRange = this.formationStats.isWithingRange(x, y, z);
	}

	@Override
	public FormationStatsContainer getFormationStats() {
		return formationStats;
	}

	@Override
	public BigDecimal getStat(PlayerStat stat) {
		return this.getStat(stat, false);
	}

	@Override
	public BigDecimal getStat(PlayerStat stat, boolean fullValue) {
		var formationStat = !this.isWithinFormationRange() ? BigDecimal.ZERO : formationStats.getStat(stat);
		if (!fullValue) {
			var statValue = this.playerStats.getOrDefault(stat, stat.defaultValue).add(formationStat);
			if (stat == PlayerStat.STRENGTH) {
				return statValue.multiply(BigDecimal.valueOf(this.strengthRegulator));
			} else if (stat == PlayerStat.AGILITY) {
				return statValue.multiply(BigDecimal.valueOf(this.agilityRegulator));
			}
		}
		return this.playerStats.getOrDefault(stat, stat.defaultValue);
	}

	@Override
	public BigDecimal getStat(ResourceLocation elementLocation, PlayerElementalStat stat) {
		var formationStat = !this.isWithinFormationRange() ? BigDecimal.ZERO : formationStats.getStat(stat, elementLocation);
		return this.playerElementalStats.getOrDefault(elementLocation, new HashMap<>()).getOrDefault(stat, BigDecimal.ZERO).add(formationStat);
	}

	@Override
	public BigDecimal getStat(System system, PlayerSystemStat stat) {
		var formationStat = !this.isWithinFormationRange() ? BigDecimal.ZERO : formationStats.getStat(system, stat);
		return this.getSystemData(system).getStat(stat).add(formationStat);
	}

	@Override
	public BigDecimal getStat(System system, ResourceLocation element, PlayerSystemElementalStat stat) {
		var formationStat = !this.isWithinFormationRange() ? BigDecimal.ZERO : formationStats.getStat(system, element, stat);
		return this.getSystemData(system).getStat(stat, element).add(formationStat);
	}

	@Override
	public double getAgilityRegulator() {
		return agilityRegulator;
	}

	@Override
	public void setAgilityRegulator(double agilityRegulator) {
		this.agilityRegulator = agilityRegulator;
	}

	@Override
	public double getStrengthRegulator() {
		return strengthRegulator;
	}

	@Override
	public void setStrengthRegulator(double strengthRegulator) {
		this.strengthRegulator = strengthRegulator;
	}

	@Override
	public MasterDiscipleContainer getMasterDiscipleContainer() {
		return this.masterDiscipleContainer;
	}
}
