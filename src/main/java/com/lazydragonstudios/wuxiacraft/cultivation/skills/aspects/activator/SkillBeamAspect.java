package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.SkillHitAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.modifier.SkillHitModifierAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaParticleTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.util.SkillUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;

public class SkillBeamAspect extends SkillActivatorAspect {

	public SkillBeamAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("0.08"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.03"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("0"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0"));
		this.setActivate((caster, skill) -> {
			var level = caster.level();
			var casterCultivation = Cultivation.get(caster);
			var essenceData = casterCultivation.getSystemData(System.ESSENCE);
			BigDecimal cost = skill.getAppliedStats(casterCultivation, SkillStat.COST);
			if (!essenceData.consumeEnergy(cost.divide(BigDecimal.TEN))) {
				skill.setStat(SkillStat.COOLDOWN, new BigDecimal("220")); // 11s
				return false;
			}
			double range = 64;
			var viewVector = caster.getViewVector(1);
			SimpleParticleType particleType = (SimpleParticleType) WuxiaParticleTypes.BEAM_SKILL_PHYSICAL.get();
			var result = SkillUtil.getHitResult(caster, range, e -> e != caster);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(particleType, caster.getX(), caster.getEyeY() - 0.8, caster.getZ(), 5, 0.05, 0.0, 0.05, 0.001f);
				var displayRange = result.getType() != HitResult.Type.MISS ? Math.sqrt(result.distanceTo(caster)) : range;
				int tickCount = skill.getStatValue(SkillStat.NON_STOP_CASTING_TIME).intValue() - 1;
				for (double i = 0; i < displayRange / 10; i += displayRange / 100) {
					var endDistance = viewVector.scale((tickCount % 10) * (displayRange / 10) + i).add(0, 0.08 * (tickCount % 10), 0);
					serverLevel.sendParticles(particleType, caster.getX() + endDistance.x, caster.getEyeY() - 0.8 + endDistance.y, caster.getZ() + endDistance.z, 2, 0.1, 0.1, 0.1, 0.01f);
				}
				if (result.getType() != HitResult.Type.MISS)
					serverLevel.sendParticles(particleType, result.getLocation().x, result.getLocation().y, result.getLocation().z, 7, 0.2, 0.2, 0.2, 0.05f);
			}
			caster.swinging = true;
			if (result.getType() == HitResult.Type.MISS) return false;
			for (var link : skill.getSkillChain()) {
				if (link instanceof SkillHitAspect hitAspect) {
					hitAspect.activate(caster, skill, result);
				} else
				if (link instanceof SkillHitModifierAspect hitModifierAspect) {
					hitModifierAspect.activate(caster, skill, result);
				} else continue;
			}
			return true;
		});
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.BEAM.get();
	}
}
