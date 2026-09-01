package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.entity.ThrowSkill;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

import java.math.BigDecimal;

public class SkillWaveAspect extends SkillActivatorAspect {

	public SkillWaveAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("2.2"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.6"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.9"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.2"));
		this.activate = (caster, skillAspects) -> {
			if (caster.level().isClientSide()) return false;
			var casterCultivation = Cultivation.get(caster);
			var essenceData = casterCultivation.getSystemData(System.ESSENCE);
			BigDecimal cost = skillAspects.getAppliedStats(casterCultivation, SkillStat.COST);
			if (!essenceData.consumeEnergy(cost.divide(BigDecimal.TEN))) return false;
			var level = caster.level();
			Vec3 forward = caster.getLookAngle().normalize();
			Vec3 up = new Vec3(0, 1, 0);
			Vec3 right = forward.cross(up).normalize(); 
			int[] steps = { -3, -2, -1, 0, 1, 2, 3 }; 
			for (int s : steps) {
				var entity = new ThrowSkill(WuxiaEntities.THROW_SKILL_TYPE.get(), level, skillAspects);
				double lateralSpawn = s * 0.15;
				float sideSpeed = s * 0.15f;
				Vec3 launchDir = forward.scale(0.8f)
					.add(right.scale(sideSpeed))
					.normalize();
				Vec3 spawnPos = caster.position().add(right.scale(lateralSpawn));
				entity.setPos(spawnPos.x, caster.getY() + caster.getEyeHeight(), spawnPos.z);
				entity.setOwner(caster);
				entity.shoot(launchDir.x, launchDir.y, launchDir.z, 0.8f, 0.05f);
				level.addFreshEntity(entity);
			}
			return true;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.WAVE.get();
	}
}
