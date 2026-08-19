package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.stats.*;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.*;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMobEffects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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

	public void forgeAllParts(BigDecimal amount) {
		for (var bodyPartLocation : this.unlockedParts()) {
			BigDecimal forgedAmount = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);
			this.bodyPartsForging.put(bodyPartLocation, forgedAmount.add(amount).setScale(6, RoundingMode.HALF_DOWN));
			
			BigDecimal maxCultivationBase = this.getStat(PlayerSystemStat.MAX_CULTIVATION_BASE);
			forgedAmount = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);
			this.bodyPartsForging.put(bodyPartLocation, forgedAmount.min(maxCultivationBase).setScale(6, RoundingMode.HALF_DOWN).max(BigDecimal.ZERO));
		}
	}

	public void forgePart(ResourceLocation bodyPartLocation, ResourceLocation elementLocation, BigDecimal amount) {
		var partElementLocation = getBodyPartElementLocation(bodyPartLocation);
		var opposingElement = WuxiaRegistries.ELEMENTS.get().getValue(elementLocation);
		if (opposingElement == null) opposingElement = WuxiaElements.PHYSICAL.get();
		var forgedAmount = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);

		MathContext mc = new MathContext(8, RoundingMode.HALF_UP);
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
			this.bodyPartsElements.put(bodyPartLocation, elementLocation);
			this.bodyPartsForging.put(bodyPartLocation, forgedAmount.add(amount).setScale(6, RoundingMode.HALF_DOWN));
		}
		forgedAmount = this.bodyPartsForging.getOrDefault(bodyPartLocation, BigDecimal.ZERO);
		var maxCultivationBase = this.getStat(PlayerSystemStat.MAX_CULTIVATION_BASE);
		this.bodyPartsForging.put(bodyPartLocation, forgedAmount.min(maxCultivationBase).setScale(6, RoundingMode.HALF_DOWN).max(BigDecimal.ZERO));
		// added a limiter instead of forge speed 
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
	
	public void removeSelectedElementByBodyPart(ResourceLocation bodyPartLocation) {
		this.selectedElementByBodyPart.remove(bodyPartLocation);
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
			if (this.bodyPartsForging.size() != 0)
			base = base.divide(BigDecimal.valueOf(this.bodyPartsForging.size()), new MathContext(8, RoundingMode.HALF_UP));
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
		var aspectData = cultivation.getAspects();
		var hpBoost = BigDecimal.ONE;
		var strBoost = BigDecimal.ONE;
		var AglBoost = BigDecimal.ONE;
		var regenBoost = BigDecimal.ONE;
		var ECBoost = BigDecimal.ONE;
		var barrierBoost = BigDecimal.ONE;
		var DCStrBoost = BigDecimal.ONE;
		this.bodyTransformation = null;
		this.displayBodyTransformation = null;
		var knownTransformationAspects = aspectData.getKnownAspects().stream()
				.filter(aspectLocation -> TechniqueUtil.getTransformationAspects().contains(aspectLocation))
				.sorted(Comparator.comparing(aspectData::getAspectProficiency).reversed()).toList();
		if (!knownTransformationAspects.isEmpty()) {
			var transformationAspectLocation = knownTransformationAspects.get(0);
			var transformationAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(transformationAspectLocation);
			if (transformationAspect != null) {
				var checkpoint = transformationAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(transformationAspectLocation));
				if (checkpoint instanceof BodyTransformationAspect.TransformationCheckpoint transformationCheckpoint) {
					this.bodyTransformation = transformationCheckpoint.getTransformationLocation();
					this.displayBodyTransformation = transformationCheckpoint.getTransformationLocation();
					var modifier = transformationCheckpoint.modifier();
				}
			}
		}
		for (var transformationAspectLocation : knownTransformationAspects) {
			var transformationAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(transformationAspectLocation);
			if (transformationAspect != null) {
				var modifier = transformationAspect.getCurrentCheckpoint(aspectData.getAspectProficiency(transformationAspectLocation)).modifier();
				if (transformationAspect.equals(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(new ResourceLocation(WuxiaCraft.MOD_ID, "spatial_kitsune_transformation")))) {
					strBoost = strBoost.add(modifier.multiply(new BigDecimal("1")));
					AglBoost = AglBoost.add(modifier.multiply(new BigDecimal("3")));
					ECBoost = ECBoost.add(modifier.multiply(new BigDecimal("0.05")));
					DCStrBoost = DCStrBoost.add(modifier.multiply(new BigDecimal("0.35")));
				} 
				if (transformationAspect.equals(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(new ResourceLocation(WuxiaCraft.MOD_ID, "light_kitsune_transformation")))) {
					strBoost = strBoost.add(modifier.multiply(new BigDecimal("0.6")));
					AglBoost = AglBoost.add(modifier.multiply(new BigDecimal("1.8")));
					DCStrBoost = DCStrBoost.add(modifier.multiply(new BigDecimal("0.2")));
				} 
				if (transformationAspect.equals(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(new ResourceLocation(WuxiaCraft.MOD_ID, "kitsune_transformation")))) {
					strBoost = strBoost.add(modifier.multiply(new BigDecimal("0.1")));
					AglBoost = AglBoost.add(modifier.multiply(new BigDecimal("0.4")));
				}
				
				if (transformationAspect.equals(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(new ResourceLocation(WuxiaCraft.MOD_ID, "azure_dragon_transformation")))) {
					strBoost = strBoost.add(modifier.multiply(new BigDecimal("1.5")));
					hpBoost = hpBoost.add(modifier.multiply(new BigDecimal("0.75")));
					regenBoost = regenBoost.add(modifier.multiply(new BigDecimal("0.2")));
					barrierBoost = barrierBoost.add(modifier.multiply(new BigDecimal("0.4")));
				} 
				if (transformationAspect.equals(WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(new ResourceLocation(WuxiaCraft.MOD_ID, "dragon_transformation")))) {
					strBoost = strBoost.add(modifier.multiply(new BigDecimal("0.75")));
					hpBoost = hpBoost.add(modifier.multiply(new BigDecimal("0.35")));
					regenBoost = regenBoost.add(modifier.multiply(new BigDecimal("0.1")));
				}
			}
		}
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
				int size = WuxiaRegistries.BODY_PART.get().getKeys().size();
				if (stat.equals(PlayerStat.MAX_HEALTH)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(hpBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(hpBoost);
				}
				else if (stat.equals(PlayerStat.STRENGTH)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(strBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(strBoost);
				}
				else if (stat.equals(PlayerStat.AGILITY)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(AglBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(AglBoost);
				}
				else if (stat.equals(PlayerStat.HEALTH_REGEN)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(regenBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(regenBoost);
				}
				else if (stat.equals(PlayerStat.EXERCISE_CONVERSION)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(ECBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(ECBoost);
				}
				else if (stat.equals(PlayerStat.MAX_BARRIER)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(barrierBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(barrierBoost);
				}
				else if (stat.equals(PlayerStat.DETECTION_STRENGTH)) {
					bodyPartBaseStatAmount = bodyPartBaseStatAmount.multiply(DCStrBoost);
					elementBaseStatAmount = elementBaseStatAmount.multiply(DCStrBoost);
				}
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
	public void addCultivationBase(Player player, ICultivation cultivation, BigDecimal amount, HashMap<ResourceLocation, BigDecimal> elementHash) {
		cultivation.getSystemData(System.ESSENCE).consumeEnergy(amount.multiply(new BigDecimal("0.3")));
		amount = super.handleCultivationBaseModifiers(player, cultivation, amount);
		var elements = this.techniqueData.modifier.elements;
		var sumOfAllElements = BigDecimal.ZERO;
		var partsToCultivateByElement = new HashMap<ResourceLocation, HashSet<ResourceLocation>>();
		for (var elementLocation : elements.keySet()) {
			BigDecimal modifier = BigDecimal.ONE;
			for (var elementKey : elementHash.keySet()) {
		 		if (elementLocation.equals(elementKey)) modifier = elementHash.get(elementKey);
			}
			cultivation.addStat(elementLocation, PlayerElementalStat.COMPREHENSION, BigDecimal.valueOf(elements.get(elementLocation)).multiply(new BigDecimal("0.01").multiply(modifier)));
			partsToCultivateByElement.put(elementLocation, this.getAllBodyPartsWithElement(elementLocation));
			sumOfAllElements = sumOfAllElements.add(BigDecimal.valueOf(elements.get(elementLocation)));
		}
		super.handleAspectProficiencyGain(player, cultivation, amount, elementHash);
		//Adds Pill Resonance
		if (player.hasEffect(WuxiaMobEffects.PILL_RESONANCE.get())) {
			var instance = player.getEffect(WuxiaMobEffects.PILL_RESONANCE.get());
			if (instance != null) {
				var amplifier = instance.getAmplifier();
				//amount = amount * (1 + (2 ^ amplifier))
				amount = amount.multiply(BigDecimal.ONE.add(new BigDecimal("2").pow(amplifier)));
			}
		}
		var cultSpeed = cultivation.getStat(system, PlayerSystemStat.CULTIVATION_SPEED);
		amount = amount.add(cultSpeed);
		if (sumOfAllElements.compareTo(BigDecimal.ZERO) <= 0) return;
		for (var elementLocation : partsToCultivateByElement.keySet()) {
			BigDecimal modifier = BigDecimal.ONE;
			if (elementHash.keySet().contains(elementLocation)) modifier = elementHash.get(elementLocation);
			var elementAmount = BigDecimal.valueOf(elements.get(elementLocation)).multiply(modifier);
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
