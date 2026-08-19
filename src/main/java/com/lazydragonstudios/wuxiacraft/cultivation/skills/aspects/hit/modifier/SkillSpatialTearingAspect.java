package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.modifier;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.SkillBeamAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.math.BigDecimal;

public class SkillSpatialTearingAspect extends SkillHitModifierAspect {

	public SkillSpatialTearingAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("2"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("2"));
		super.activation = (caster, skill, result) -> {
			if (result instanceof EntityHitResult entityResult) {
				if (entityResult.getEntity() instanceof LivingEntity target) {
					if (caster.level().isClientSide()) return false;
					ICultivation casterCultivation = Cultivation.get(caster);
					BigDecimal skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
					double distance = skillStrength.doubleValue();
					//makes long distance teleporting harder
					if(distance > 12d) distance = distance/2+6d;
					if(distance > 25d) distance = distance/2+12d;
					if(distance > 50d) distance = distance/2+25d;
					if(distance > 100d) distance = distance/2+50d;
					if(distance > 200d) distance = distance/2+100d;
					if(distance > 400d) distance = distance/2+200d;
					if(distance > 800d) distance = distance/2+400d;
					if(distance > 1600d) distance = distance/2+800d;
					if(distance > 3200d) distance = distance/2+1600d;
					Vec3 lookAngle = caster.getLookAngle();
					target.teleportRelative(lookAngle.x*distance, lookAngle.y*distance, lookAngle.z*distance);
				}
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SPATIAL_TEARING.get();
	}
}
