package com.lazydragonstudios.wuxiacraft.cultivation;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EssenceCultivationStage extends CultivationStage {

	/**
	 * Constructor for this cultivation stage
	 *
	 * @param system        the Stage Cultivation System
	 * @param realm         the realm this stage is in
	 * @param previousStage a reference to the previous stage, null if this is the first
	 * @param nextStage     a reference to the next stage, null if this is the last
	 */
	public EssenceCultivationStage(System system, ResourceLocation realm, @Nullable ResourceLocation previousStage, @Nullable ResourceLocation nextStage) {
		super(system, realm, previousStage, nextStage);
	}

	public EssenceCultivationStage cannotHaveBarrier() {
		this.canHaveBarrier = false;
		return this;
	}

	public EssenceCultivationStage cannotConvertToFood() {
		this.canConvertToFood = false;
		return this;
	}

	private boolean canHaveBarrier = true;

	private boolean canConvertToFood = true;

	public boolean isCanHaveBarrier() {
		return canHaveBarrier;
	}

	public void setCanHaveBarrier(boolean canHaveBarrier) {
		this.canHaveBarrier = canHaveBarrier;
	}

	public boolean isCanConvertToFood() {
		return canConvertToFood;
	}
}
