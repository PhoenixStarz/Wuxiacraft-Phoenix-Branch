package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.BodyTransformationAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.ElementToStatsConsumer;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BodyCultivationContainer extends SystemContainer {

	/**
	 * Cache for stats. It won't be recalculated each time it's requested, only during recalculate ticks
	 */
	protected final HashMap<PlayerStat, BigDecimal> playerStats = new HashMap<>();

	/**
	 * Cache for stats. It won't be recalculated each time it's requested, only during recalculate ticks
	 */
	//<stat, <element, amount>>
	protected final HashMap<PlayerElementalStat, HashMap<ResourceLocation, BigDecimal>> playerElementalStats = new HashMap<>();

	// <body part, amount>
	private final HashMap<ResourceLocation, BigDecimal> bodyPartsForging = new HashMap<>();

	/**
	 * This is the element that is actually forged
	 */
	//<body part, element>
	private final HashMap<ResourceLocation, ResourceLocation> bodyPartsElements = new HashMap<>();

	//This is what is selected to forge
	//<body part, element>
	private final HashMap<ResourceLocation, ResourceLocation> selectedElementByBodyPart = new HashMap<>();

	/**
	 * The entity type the renderer is registered under. This is calculated
	 */
	@Nullable
	private ResourceLocation bodyTransformation = null;

	/**
	 * The entity type the renderer is registered under. This is set from the server to the client to allow others to see it
	 * Since the bodyTransformation one is calculated, that will be set to null all the time.
	 * This one will be set once for every animation change.
	 */
	@Nullable
	ResourceLocation displayBodyTransformation = null;

	/**
	 * Creates a system container only for body as it should
	 */
	public BodyCultivationContainer() {
		super(System.BODY);
	}

	public void forgePart(ResourceLocation bodyPartLocation, ResourceLocation elementLocation, BigDecimal amount) {
		var partElementLocation = getBodyPartElementLocation(bodyPartLocation);
		var opposingElement = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
		if (opposingElement == null) opposingElement = WuxiaElements.PHYSICAL.get();
		var forgedAmount = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);

		MathContext mc = new MathContext(8, RoundingMode.HALF_UP);
		var maxCultivationBase = this.getStat(PlayerSystemStat.MAX_CULTIVATION_BASE).multiply(new BigDecimal("0.4"), mc);
		// mCB^2 / ( f^2 + mCB^2 - f*mCB) == A very nice bell curve that tops at 4/3 and stretches out based on cultivation base
		var forgeSpeed = maxCultivationBase.pow(2).divide(forgedAmount.pow(2, mc).add(maxCultivationBase.pow(2, mc)).subtract(forgedAmount.multiply(maxCultivationBase, mc)), mc);
		forgedAmount = forgedAmount.multiply(forgeSpeed, mc);
		if (!partElementLocation.equals(elementLocation)) {
			if (forgedAmount.compareTo(BigDecimal.TEN) < 0) {
				this.bodyPartsElements.put(bodyPartLocation, elementLocation);
				this.bodyPartsForging.put(bodyPartLocation, forgedAmount.add(amount).setScale(6, RoundingMode.HALF_DOWN));
			} else if (opposingElement.suppressesElement(partElementLocation)) {
				this.bodyPartsForging.put(bodyPartLocation, forgedAmount.subtract(amount.multiply(BigDecimal.valueOf(2))).setScale(6, RoundingMode.HALF_DOWN));
			} else if (opposingElement.begetsElement(partElementLocation)) {
				this.bodyPartsForging.put(bodyPartLocation, forgedAmount.add(amount.multiply(BigDecimal.valueOf(0.5))).setScale(6, RoundingMode.HALF_DOWN));
			} else {
				this.bodyPartsForging.put(bodyPartLocation, forgedAmount.subtract(amount).setScale(6, RoundingMode.HALF_DOWN));
			}
		} else {
			this.bodyPartsForging.put(bodyPartLocation, forgedAmount.add(amount).setScale(6, RoundingMode.HALF_DOWN));
		}
	}

	public BigDecimal getForgedAmountByPart(ResourceLocation selectedBodyPart) {
		return this.bodyPartsForging.getOrDefault(selectedBodyPart, BigDecimal.ZERO);
	}

	@Nullable
	public ResourceLocation getForgedElementByPart(ResourceLocation selectedBodyPart) {
		return this.bodyPartsElements.get(selectedBodyPart);
	}

	public ResourceLocation getSelectedElementByPart(ResourceLocation bodyPart) {
		return this.selectedElementByBodyPart.get(bodyPart);
	}

	public void selectElementToPart(ResourceLocation bodyPartLocation, ResourceLocation elementLocation) {
		this.selectedElementByBodyPart.put(bodyPartLocation, elementLocation);
	}

	@Nullable
	public ResourceLocation getBodyTransformation() {
		return bodyTransformation;
	}

	@Nonnull
	@Override
	public BigDecimal getStat(PlayerStat stat) {
			BigDecimal statValue = this.getStage().getStat(stat);
			BigDecimal statValue2 = this.playerStats.getOrDefault(stat, BigDecimal.ZERO);
			var statValue3 = statValue.add(statValue2);
		return statValue3;
	}

	@Nonnull
	@Override
	public BigDecimal getStat(PlayerSystemElementalStat stat, ResourceLocation elementLocation) {
		return this.systemElementalStats.getOrDefault(stat, new HashMap<>()).getOrDefault(elementLocation, BigDecimal.ZERO);
	}

	@Nonnull
	@Override
	public BigDecimal getStat(PlayerSystemStat stat) {
		if (stat == PlayerSystemStat.CULTIVATION_BASE) {
			var base = BigDecimal.ZERO;
			for (var forgedAmount : this.bodyPartsForging.values()) {
				base = base.add(forgedAmount);
			}
			return base;
		}
		return super.getStat(stat);
	}

	@Nonnull
	@Override
	public BigDecimal getStat(PlayerElementalStat stat, ResourceLocation elementLocation) {
		return super.getStat(stat, elementLocation);
	}

	@Override
	public void calculateStats(ICultivation cultivation) {
		this.playerStats.clear();
		for (var stat : PlayerSystemStat.values()) {
			if (stat.isModifiable) continue;
			this.systemStats.put(stat, stat.defaultValue);
		}
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			CultivationStage stage = systemData.getStage();
			for (var stat : PlayerSystemStat.values()) {
				var stageValue = stage.getStat(System.BODY, stat);
				var currentStatAmount = this.systemStats.getOrDefault(stat, BigDecimal.ZERO);
				currentStatAmount = currentStatAmount.add(currentStatAmount.multiply(this.techniqueData.modifier.getStat(system, stat)));
				this.systemStats.put(stat, currentStatAmount.add(stageValue).setScale(6, RoundingMode.HALF_DOWN));
			}
		}
		this.playerElementalStats.clear();
		for (var bodyPartLocation : WuxiaRegistries.BODY_PART.get().getKeys()) {
			BigDecimal amountForged = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);
			var bodyPart = WuxiaRegistries.BODY_PART.get().getValue(bodyPartLocation);
			var bodyPartElementLocation = getBodyPartElementLocation(bodyPartLocation);
			if (bodyPart == null) continue;
			var relatedPartLocation = bodyPart.getIsRelatedToPart();
			if (relatedPartLocation != null) {
				var relatedForgedAmount = this.bodyPartsForging.getOrDefault(relatedPartLocation, BigDecimal.ZERO);
				var relatedPartElementLocation = getBodyPartElementLocation(relatedPartLocation);
				var relatedPartElement = WuxiaRegistries.ELEMENTS.get().getValue(relatedPartElementLocation);
				if (relatedPartElement == null) relatedPartElement = WuxiaElements.PHYSICAL.get();
				if (relatedPartElement.suppressesElement(bodyPartElementLocation)) {
					amountForged = BigDecimal.ZERO.max(amountForged.subtract(relatedForgedAmount));
				} else if (relatedPartElement.begetsElement(bodyPartElementLocation)) {
					amountForged = amountForged.add(relatedForgedAmount.multiply(new BigDecimal("2")));
				} else {
					amountForged = amountForged.add(relatedForgedAmount);
				}
			}
			if (bodyPart.getElementalAffinity() != null && bodyPart.getElementalAffinity().equals(bodyPartElementLocation)) {
				amountForged = amountForged.multiply(new BigDecimal("1.5"));
			}
			var forgingLimit = bodyPart.getStat(BodyStat.FORGING_LIMIT);
			var outputStatMultiplier = amountForged.divide(forgingLimit, RoundingMode.HALF_UP);
			var element = WuxiaRegistries.ELEMENTS.get().getValue(bodyPartElementLocation);
			if (element == null) element = WuxiaElements.PHYSICAL.get();
			for (var stat : PlayerStat.values()) {
				if (stat.isModifiable) continue;
				var currentStatAmount = this.playerStats.getOrDefault(stat, BigDecimal.ZERO);
				var bodyPartBaseStatAmount = bodyPart.getStat(stat);
				var elementBaseStatAmount = element.getStat(stat);
				currentStatAmount = currentStatAmount.add(bodyPartBaseStatAmount.add(elementBaseStatAmount).multiply(outputStatMultiplier));
				this.playerStats.put(stat, currentStatAmount.setScale(6, RoundingMode.HALF_DOWN));
			}
			for (var stat : PlayerElementalStat.values()) {
				if (stat.isModifiable) continue;
				var statElements = bodyPart.getSavedStats(stat);
				for (var elementLocation : statElements) {
					var currentStatAmount = this.playerElementalStats.getOrDefault(stat, new HashMap<>()).getOrDefault(elementLocation, BigDecimal.ZERO);
					var bodyPartBaseStatAmount = bodyPart.getStat(stat, elementLocation);
					var elementBaseStatAmount = element.getStat(stat, elementLocation);
					currentStatAmount = currentStatAmount.add(bodyPartBaseStatAmount.add(elementBaseStatAmount).multiply(outputStatMultiplier));
					this.playerElementalStats.putIfAbsent(stat, new HashMap<>());
					this.playerElementalStats.get(stat).put(elementLocation, currentStatAmount.setScale(6, RoundingMode.HALF_DOWN));
				}
			}
			for (var stat : PlayerSystemStat.values()) {
				if (stat.isModifiable) continue;
				var currentStatAmount = this.systemStats.getOrDefault(stat, BigDecimal.ZERO);
				var bodyPartBaseStatAmount = bodyPart.getStat(system, stat);
				var elementBaseStatAmount = element.getStat(system, stat);
				currentStatAmount = currentStatAmount.add(bodyPartBaseStatAmount.add(elementBaseStatAmount).multiply(outputStatMultiplier));
				this.systemStats.put(stat, currentStatAmount.setScale(6, RoundingMode.HALF_DOWN));
			}
			for (var stat : PlayerSystemElementalStat.values()) {
				if (stat.isModifiable) continue;
				var systemElementalStats = bodyPart.getSavedStats(System.BODY, stat);
				for (var elementLocation : systemElementalStats) {
					var currentStatAmount = this.systemElementalStats.getOrDefault(stat, new HashMap<>()).getOrDefault(elementLocation, BigDecimal.ZERO);
					var bodyPartBaseStatAmount = bodyPart.getStat(System.BODY, elementLocation, stat);
					var elementBaseStatAmount = element.getStat(system, elementLocation, stat);
					currentStatAmount = currentStatAmount.add(bodyPartBaseStatAmount.add(elementBaseStatAmount).multiply(outputStatMultiplier));
					this.systemElementalStats.putIfAbsent(stat, new HashMap<>());
					this.systemElementalStats.get(stat).put(elementLocation, currentStatAmount.setScale(6, RoundingMode.HALF_DOWN));
				}
			}
		}
		for (var stat : this.playerStats.keySet()) {
			var currentStatAmount = this.playerStats.getOrDefault(stat, BigDecimal.ZERO);
			currentStatAmount = currentStatAmount.add(currentStatAmount.multiply(this.techniqueData.modifier.getStat(stat)));
			this.playerStats.getOrDefault(stat, currentStatAmount);
		}
		for (var systemStat : this.systemStats.keySet()) {
			var currentStatAmount = this.systemStats.getOrDefault(systemStat, BigDecimal.ZERO);
			currentStatAmount = currentStatAmount.add(currentStatAmount.multiply(this.techniqueData.modifier.getStat(system, systemStat)));
			this.systemStats.put(systemStat, currentStatAmount);
		}
		for (var stat : this.playerElementalStats.keySet()) {
			for (var elementLocation : this.playerElementalStats.get(stat).keySet()) {
				var currentStatAmount = this.playerElementalStats.get(stat).getOrDefault(elementLocation, BigDecimal.ZERO);
				currentStatAmount = currentStatAmount.add(currentStatAmount.multiply(this.techniqueData.modifier.getStat(stat, elementLocation)));
				this.playerElementalStats.get(stat).put(elementLocation, currentStatAmount);
			}
		}
		for (var stat : this.systemElementalStats.keySet()) {
			for (var elementLocation : this.systemElementalStats.get(stat).keySet()) {
				var currentStatAmount = this.systemElementalStats.get(stat).getOrDefault(elementLocation, BigDecimal.ZERO);
				currentStatAmount = currentStatAmount.add(currentStatAmount.multiply(this.techniqueData.modifier.getStat(system, elementLocation, stat)));
				this.systemElementalStats.get(stat).put(elementLocation, currentStatAmount);
			}
		}
		var aspectData = cultivation.getAspects();
		this.bodyTransformation = null;
		this.displayBodyTransformation = null;
		var knownTransformationAspects = aspectData.getKnownAspects().stream()
				.filter(aspectLocation -> TechniqueUtil.getTransformationAspects().contains(aspectLocation))
				.sorted(Comparator.comparing(aspectData::getAspectProficiency).reversed()).toList();
		if (knownTransformationAspects.isEmpty()) return;
		var transformationAspectLocation = knownTransformationAspects.get(0);
		var transformationAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(transformationAspectLocation);
		if (transformationAspect == null) return;
		var checkpoint = transformationAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(transformationAspectLocation));
		if (!(checkpoint instanceof BodyTransformationAspect.TransformationCheckpoint transformationCheckpoint)) return;
		this.bodyTransformation = transformationCheckpoint.getTransformationLocation();
		this.displayBodyTransformation = transformationCheckpoint.getTransformationLocation();
	}

	@Override
	public CompoundTag serialize() {
		var containerTag = super.serialize();
		for (var bodyPart : this.bodyPartsForging.keySet()) {
			var amount = this.bodyPartsForging.get(bodyPart);
			containerTag.putString("forging-" + bodyPart.toString(), amount.setScale(6, RoundingMode.HALF_DOWN).setScale(6, RoundingMode.HALF_DOWN).toPlainString());
		}
		for (var bodyPart : this.bodyPartsElements.keySet()) {
			var elementLocation = this.bodyPartsElements.get(bodyPart);
			containerTag.putString("element-" + bodyPart.toString(), elementLocation.toString());
		}
		for (var bodyPart : this.selectedElementByBodyPart.keySet()) {
			var elementLocation = this.selectedElementByBodyPart.get(bodyPart);
			containerTag.putString("selected-element-" + bodyPart.toString(), elementLocation.toString());
		}
		for (var stat : this.playerStats.keySet()) {
			if (!stat.isModifiable) continue;
			BigDecimal statValue = this.playerStats.get(stat);
			int scale = statValue.scale();
			statValue = statValue.setScale(Math.min(10, scale), RoundingMode.DOWN);
			containerTag.putString("stat-" + stat.name().toLowerCase(), statValue.toPlainString());
			this.playerStats.put(stat, statValue);
		}
		return containerTag;
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
		super.deserialize(tag);
		this.bodyPartsForging.clear();
		this.bodyPartsElements.clear();
		this.selectedElementByBodyPart.clear();
		for (var bodyPartLocation : WuxiaRegistries.BODY_PART.get().getKeys()) {
			var forgingTagName = "forging-" + bodyPartLocation;
			var elementTagName = "element-" + bodyPartLocation;
			var selectedElementTagName = "selected-element-" + bodyPartLocation;
			if (tag.contains(forgingTagName)) {
				if (tag.getString(forgingTagName).isEmpty()) tag.putString(forgingTagName, "0.0");
				this.bodyPartsForging.put(bodyPartLocation, new BigDecimal(tag.getString(forgingTagName)).setScale(6, RoundingMode.HALF_DOWN));
			}
			if (tag.contains(elementTagName)) {
				this.bodyPartsElements.put(bodyPartLocation, new ResourceLocation(tag.getString(elementTagName)));
			}
			if (tag.contains(selectedElementTagName)) {
				this.selectedElementByBodyPart.put(bodyPartLocation, new ResourceLocation(tag.getString(selectedElementTagName)));
			}
		}
	}

	private ResourceLocation getBodyPartElementLocation(ResourceLocation bodyPartLocation) {
		return this.bodyPartsElements.getOrDefault(bodyPartLocation, WuxiaElements.PHYSICAL.getId());
	}

	public void setDisplayTransformation(@Nullable ResourceLocation transformation) {
		this.displayBodyTransformation = transformation;
	}

	@Nullable
	public ResourceLocation getDisplayBodyTransformation() {
		return displayBodyTransformation;
	}

	public HashSet<ResourceLocation> unlockedParts() {
		var partLocations = new HashSet<ResourceLocation>();
		var stage = (BodyCultivationStage) this.getStage();
		while (stage != null) {
			partLocations.addAll(stage.getUnlockedParts());
			stage = (BodyCultivationStage) WuxiaRegistries.CULTIVATION_STAGES.get().getValue(stage.previousStage);
		}
		return partLocations;
	}

	public HashSet<ResourceLocation> getAllBodyPartsWithElement(ResourceLocation elementLocation) {
		var parts = new HashSet<ResourceLocation>();
		for (var part : this.selectedElementByBodyPart.keySet()) {
			if (this.selectedElementByBodyPart.get(part).equals(elementLocation)) {
				parts.add(part);
			}
		}
		return parts;
	}

	@Override
	public void addCultivationBase(Player player, ICultivation cultivation, BigDecimal amount) {
		var elements = this.techniqueData.modifier.elements;
		var sumOfAllElements = BigDecimal.ZERO;
		var grid = this.techniqueData.grid.getGrid();
		var aspects = cultivation.getAspects();
		var partsToCultivateByElement = new HashMap<ResourceLocation, HashSet<ResourceLocation>>();
		for (var elementLocation : elements.keySet()) {
			cultivation.addStat(elementLocation, PlayerElementalStat.COMPREHENSION, BigDecimal.valueOf(elements.get(elementLocation)).multiply(new BigDecimal("0.01")));
			partsToCultivateByElement.put(elementLocation, this.getAllBodyPartsWithElement(elementLocation));
			sumOfAllElements = sumOfAllElements.add(BigDecimal.valueOf(elements.get(elementLocation)));
		}

		for (var aspectLocation : grid.values()) {
			aspects.addAspectProficiency(aspectLocation, amount, cultivation);
		}
		this.techniqueData.grid.fixProficiencies(aspects);

		var cultSpeed = cultivation.getStat(system, PlayerSystemStat.CULTIVATION_SPEED);
		amount = amount.multiply(BigDecimal.ONE.add(cultSpeed).multiply(BigDecimal.valueOf(WuxiaConfigs.CULTIVATION_SPEED_MULTIPLIER.get())));
		Map<ResourceKey<Level>, Double> multiplierMap = WuxiaConfigs.getDimensionMultipliers();
		ResourceKey<Level> currentDim = player.level().dimension();
		if (multiplierMap.containsKey(currentDim)) {
    		amount = amount.multiply(BigDecimal.valueOf(multiplierMap.get(currentDim)));
		}
		String AFKS = WuxiaConfigs.AFK_SYSTEM.get();
		var AFKtimer = cultivation.getStat(PlayerStat.CULTPOINT).intValue();
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
		if (cheeseburger == 0) AFKmulti = AFKmulti.add(BigDecimal.ONE);
		amount = amount.multiply(AFKmulti);
		cultivation.setStat(PlayerStat.CULTPOINT, cultivation.getStat(PlayerStat.CULTPOINT).subtract(BigDecimal.TEN));
		if (sumOfAllElements.compareTo(BigDecimal.ZERO) <= 0) return;
		for (var elementLocation : partsToCultivateByElement.keySet()) {
			var elementAmount = BigDecimal.valueOf(elements.get(elementLocation));
			var elementWeight = elementAmount.divide(sumOfAllElements, RoundingMode.HALF_UP);
			var elementPartsCount = partsToCultivateByElement.get(elementLocation).size();
			if (elementPartsCount == 0) continue;
			var amountToEachPart = amount.multiply(elementWeight).divide(BigDecimal.valueOf(elementPartsCount), RoundingMode.HALF_UP).setScale(8, RoundingMode.HALF_DOWN);
			for (var bodyPartToForge : partsToCultivateByElement.get(elementLocation)) {
				this.forgePart(bodyPartToForge, elementLocation, amountToEachPart);
			}
		}
	}
}
