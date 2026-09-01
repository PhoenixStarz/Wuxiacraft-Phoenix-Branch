package com.lazydragonstudios.wuxiacraft.cultivation.technique;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;

public class AspectContainer {

	private final HashMap<ResourceLocation, BigDecimal> aspectAndProficiency = new HashMap<>();

	private int knownAspectCount = 0;

	public AspectContainer() {
		aspectAndProficiency.put(WuxiaTechniqueAspects.START.getId(), BigDecimal.TEN);
		countKnownAspects();
	}

	public BigDecimal getAspectProficiency(ResourceLocation aspect) {
		return aspectAndProficiency.getOrDefault(aspect, BigDecimal.ZERO);
	}

	public void countKnownAspects() {
		this.knownAspectCount = 0;
		var aspectsToRemove = new HashSet<ResourceLocation>();
		for (var aspect : this.aspectAndProficiency.keySet()) {
			if (this.knowsAspect(aspect)) {
				this.knownAspectCount += 1;
			} else {
				aspectsToRemove.add(aspect);
			}
		}
		for(var aspect : aspectsToRemove) {
			this.aspectAndProficiency.remove(aspect);
		}
	}

	public Set<ResourceLocation> getKnownAspects() {
		return this.aspectAndProficiency.keySet();
	}

	public int getKnownAspectsCount() {
		return this.knownAspectCount;
	}

	/**
	 * Probably empty aspects might be added because of poor manipulation
	 * so let's add rule that it needs to be bigger than 10
	 *
	 * @param aspect the id of the aspect to be known
	 * @return true if this aspect is known by the player
	 */
	public boolean knowsAspect(ResourceLocation aspect) {
		return this.aspectAndProficiency.getOrDefault(aspect, BigDecimal.ZERO).compareTo(BigDecimal.TEN) >= 0;
	}

	public void addAspectProficiency(ResourceLocation aspect, BigDecimal amount, ICultivation cultivation) {
		if (this.aspectAndProficiency.containsKey(aspect)) {
			TechniqueAspect aspectInstance = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
			if (aspectInstance == null) return;
			this.aspectAndProficiency.put(aspect, this.aspectAndProficiency.get(aspect).add(amount).max(BigDecimal.ZERO));
		} else {
			if (amount.compareTo(BigDecimal.TEN) >= 0) {
				if (this.learnAspect(aspect, cultivation)) {
					this.aspectAndProficiency.put(aspect, amount);
				}
			}
		}
		countKnownAspects();
	}
	
	public void addAllAspectsProficiency(BigDecimal amount, ICultivation cultivation) {
		for (var aspect : WuxiaRegistries.TECHNIQUE_ASPECT.get().getKeys()) {
			if (this.aspectAndProficiency.containsKey(aspect)) {
				TechniqueAspect aspectInstance = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspect);
				if (aspectInstance == null) return;
				this.aspectAndProficiency.put(aspect, this.aspectAndProficiency.get(aspect).add(amount).max(BigDecimal.ZERO));
			} else {
				if (amount.compareTo(BigDecimal.TEN) >= 0) {
					this.aspectAndProficiency.put(aspect, amount);
				}
			}
			countKnownAspects();
		}
	}

	public void setAspectAndProficiency(ResourceLocation aspect, BigDecimal amount) {
		this.aspectAndProficiency.put(aspect, amount.max(BigDecimal.ZERO));
		countKnownAspects();
	}

	public void subtractAspectProficiency(ResourceLocation aspect, BigDecimal amount) {
		if (this.aspectAndProficiency.containsKey(aspect)) {
			this.aspectAndProficiency.put(aspect, this.aspectAndProficiency.get(aspect).subtract(amount).max(BigDecimal.ZERO));
		} else {
			this.aspectAndProficiency.put(aspect, amount.multiply(new BigDecimal("-1")).max(BigDecimal.ZERO));
		}
		countKnownAspects();
	}

	public void learnRebirthAspects(ICultivation cultivation) {
		this.learnAspect(WuxiaTechniqueAspects.ASHES_OF_REBIRTH.getId(), cultivation);
		this.learnAspect(WuxiaTechniqueAspects.EMBER_OF_REKINDLING.getId(), cultivation);
		this.learnAspect(WuxiaTechniqueAspects.CINDER_OF_RENEWAL.getId(), cultivation);
		this.learnAspect(WuxiaTechniqueAspects.SPIRITUAL_RECONSTRUCTION.getId(), cultivation);
	}

	public boolean learnAspect(ResourceLocation location, ICultivation cultivation) {
		return this.learnAspectWithProficiency(location, cultivation, BigDecimal.TEN);
	}

	public boolean learnAspectWithProficiency(ResourceLocation location, ICultivation cultivation, BigDecimal proficiency) {
		if (!this.knowsAspect(location)) {
			var aspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(location);
			if (aspect == null) return false;
			if (aspect.canLearn.test(cultivation)) {
				this.aspectAndProficiency.put(location, BigDecimal.TEN.max(proficiency));
				return true;
			}
		}
		return false;
	}

	public void calculateAspects(ICultivation cultivation) {
		LinkedList<Consumer<ICultivation>> onReachedSuppliers = new LinkedList<>();
		for (var aspectLocation : this.aspectAndProficiency.keySet()) {
			var aspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspectLocation);
			if (aspect == null) continue;
			for (var checkpoint : aspect.checkpoints) {
				var proficiency = this.aspectAndProficiency.get(aspectLocation);
				if (proficiency.compareTo(checkpoint.proficiencyRequired()) > 0) {
					onReachedSuppliers.add(checkpoint.onReached());
				}
			}
		}
		for (var reached : onReachedSuppliers) {
			reached.accept(cultivation);
		}
	}

	public CompoundTag serialize() {
		var tag = new CompoundTag();
		ListTag listTag = new ListTag();
		for (var aspect : this.aspectAndProficiency.keySet()) {
			var aspectTag = new CompoundTag();
			aspectTag.putString("aspect-name", aspect.toString());
			aspectTag.putString("aspect-proficiency", aspectAndProficiency.get(aspect).setScale(4, RoundingMode.HALF_DOWN).toPlainString());
			listTag.add(aspectTag);
		}
		tag.put("aspect-list", listTag);
		return tag;
	}

	public void deserialize(CompoundTag tag, ICultivation cultivation) {
		var listTag = (ListTag) tag.get("aspect-list");
		if (listTag == null) return;
		this.aspectAndProficiency.clear();
		for (var itemTag : listTag) {
			if (!(itemTag instanceof CompoundTag aspectTag)) continue;
			var location = aspectTag.getString("aspect-name");
			var proficiency = aspectTag.getString("aspect-proficiency");
			this.aspectAndProficiency.put(new ResourceLocation(location), new BigDecimal(proficiency));
		}
		calculateAspects(cultivation);
		countKnownAspects();
	}

}
