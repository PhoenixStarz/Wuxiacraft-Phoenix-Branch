package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.SkillHitAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.util.SkillUtil;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;

public class SkillTouchAspect extends SkillActivatorAspect {

	public SkillTouchAspect(ICultivation cultivation) {
		super(cultivation);
		var strength = cultivation.getSystemData(System.ESSENCE).getStat(PlayerStat.STRENGTH);
		setSkillStat(SkillStat.COST, new BigDecimal("1"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("1"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("0.8"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.6"));
		this.setActivate((caster, skill) -> {
			var casterCultivation = Cultivation.get(caster);
			var essenceData = casterCultivation.getSystemData(System.ESSENCE);
			BigDecimal cost = skill.getStatValue(SkillStat.COST).multiply(BigDecimal.valueOf(casterCultivation.getStrengthRegulator()));
			if (!essenceData.consumeEnergy(cost)) return false;
			var result = SkillUtil.getHitResult(caster, caster.getBlockReach(), e -> e != caster);
			if (result.getType() == HitResult.Type.MISS) return false;
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
		return WuxiaSkillAspects.PUNCH.get();
	}
}
