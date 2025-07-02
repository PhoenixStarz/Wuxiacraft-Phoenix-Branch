package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect.Checkpoint;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class BodyTransformationAspect extends ElementToStatsConsumer {

	public BodyTransformationAspect(ResourceLocation element, double cost) {
		super(element, cost);
	}

	@Override
	public boolean canShowForSystem(System system) {
		return system == System.BODY;
	}

	public static class TransformationCheckpoint extends Checkpoint {

		private final ResourceLocation transformationLocation;

		public TransformationCheckpoint(String name, BigDecimal proficiencyRequired, ResourceLocation transformationLocation, BigDecimal modifier, Consumer<ICultivation> onReached, HashSet<ResourceLocation> skills) {
			super(name, proficiencyRequired, modifier, onReached, skills);
			this.transformationLocation = transformationLocation;
		}

		public TransformationCheckpoint(String name, BigDecimal proficiencyRequired, ResourceLocation transformationLocation, BigDecimal modifier, Consumer<ICultivation> onReached) {
			super(name, proficiencyRequired, modifier, onReached);
			this.transformationLocation = transformationLocation;
		}

		public TransformationCheckpoint(String name, BigDecimal proficiency, ResourceLocation transformationLocation, BigDecimal modifier) {
			super(name, proficiency, modifier);
			this.transformationLocation = transformationLocation;
		}

		public TransformationCheckpoint(String name, BigDecimal proficiency, ResourceLocation transformationLocation, Consumer<ICultivation> onReached) {
			super(name, proficiency, onReached);
			this.transformationLocation = transformationLocation;
		}

		public TransformationCheckpoint(String name, BigDecimal proficiencyRequired, ResourceLocation transformationLocation) {
			super(name, proficiencyRequired);
			this.transformationLocation = transformationLocation;
		}

		@Nullable
		public ResourceLocation getTransformationLocation() {
			return transformationLocation;
		}

		@Override
		public boolean equals(Object obj) {
			return obj == this || obj != null && obj.getClass() == this.getClass();
		}

		@Override
		public int hashCode() {
			return 1;
		}

		@Override
		public String toString() {
			return "TransformationCheckpoint[]";
		}

	}

}
