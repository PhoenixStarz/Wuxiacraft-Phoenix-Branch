package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.entity.ThrowSkill;
import com.lazydragonstudios.wuxiacraft.init.WuxiaEntities;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;

import java.math.BigDecimal;

public class SkillShootAspect extends SkillActivatorAspect {

	public SkillShootAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("1.3"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.7"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.5"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1"));
		this.activate = (caster, skillAspects) -> {
			if (caster.level().isClientSide()) return false;
			var casterCultivation = Cultivation.get(caster);
			var essenceData = casterCultivation.getSystemData(System.ESSENCE);
			BigDecimal cost = skillAspects.getAppliedStats(casterCultivation, SkillStat.COST);
			if (!essenceData.consumeEnergy(cost.divide(BigDecimal.TEN))) return false;
			var level = caster.level();
			var lookAngle = caster.getLookAngle();
			var entity = new ThrowSkill(WuxiaEntities.THROW_SKILL_TYPE.get(), level, skillAspects);
			entity.setPos(caster.getEyePosition());
			entity.setOwner(caster);
			entity.shoot(lookAngle.x, lookAngle.y, lookAngle.z, 0.86f, 0.05f);
			level.addFreshEntity(entity);
			return true;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SHOOT.get();
	}
}
