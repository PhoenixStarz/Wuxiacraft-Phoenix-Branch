package com.lazydragonstudios.wuxiacraft.cultivation;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DivineCultivationStage extends CultivationStage {

	private int dimensionSize;

	//Transport other entities into your pocket dimension.
	private boolean canHaveExtraEntities;

	/**
	 * Constructor for this cultivation stage
	 *
	 * @param system        the Stage Cultivation System
	 * @param realm         the realm this stage is in
	 * @param previousStage a reference to the previous stage, null if this is the first
	 * @param nextStage     a reference to the next stage, null if this is the last
	 */
	public DivineCultivationStage(System system, ResourceLocation realm, @Nullable ResourceLocation previousStage, @Nullable ResourceLocation nextStage, int numberOfLightningStrikes, int lightningStrength, float lightningStrengthGrowth, int dimensionSize, boolean canHaveExtraEntities) {
		super(system, realm, previousStage, nextStage, numberOfLightningStrikes, lightningStrength, lightningStrengthGrowth);
		this.dimensionSize = dimensionSize;
		this.canHaveExtraEntities = canHaveExtraEntities;
	}

	public int getDimensionSize() {
		return this.dimensionSize;
	}

	public boolean isCanHaveExtraEntities() {
		return canHaveExtraEntities;
	}
}
