package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.SkillHitAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;

public class SkillSelfAspect extends SkillActivatorAspect {

	public SkillSelfAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("0.5"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("0.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.8"));
		this.setActivate((caster, skill) -> {
			var casterCultivation = Cultivation.get(caster);
			var essenceData = casterCultivation.getSystemData(System.ESSENCE);
			BigDecimal cost = skill.getAppliedStats(casterCultivation, SkillStat.COST);
			if (!essenceData.consumeEnergy(cost)) return false;
			var result = new EntityHitResult(caster, caster.getEyePosition());
			caster.swinging = true;
			for (var link : skill.getSkillChain()) {
				if (!(link instanceof SkillHitAspect hitAspect)) continue;
				hitAspect.activate(caster, skill, result);
				break;
			}
			return true;
		});
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SELF.get();
	}
}
