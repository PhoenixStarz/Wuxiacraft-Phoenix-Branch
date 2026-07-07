package com.lazydragonstudios.wuxiacraft.cultivation;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class BodyCultivationStage extends CultivationStage {

	private final HashSet<ResourceLocation> unlockedParts = new HashSet<>();

	/**
	 * Constructor for this cultivation stage
	 *
	 * @param system        the Stage Cultivation System
	 * @param realm         the realm this stage is in
	 * @param previousStage a reference to the previous stage, null if this is the first
	 * @param nextStage     a reference to the next stage, null if this is the last
	 */
	public BodyCultivationStage(System system, ResourceLocation realm, @Nullable ResourceLocation previousStage, @Nullable ResourceLocation nextStage, int numberOfLightningStrikes, int lightningStrength, float lightningStrengthGrowth) {
		super(system, realm, previousStage, nextStage, numberOfLightningStrikes, lightningStrength, lightningStrengthGrowth);
	}

	@SuppressWarnings("unchecked")
	public HashSet<ResourceLocation> getUnlockedParts() {
		return (HashSet<ResourceLocation>) this.unlockedParts.clone();
	}

	public BodyCultivationStage unlockPart(ResourceLocation partLocation) {
		this.unlockedParts.add(partLocation);
		return this;
	}
}
