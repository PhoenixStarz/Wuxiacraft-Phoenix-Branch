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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;

public class SkillTemporalTearingAspect extends SkillHitModifierAspect {

	public SkillTemporalTearingAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("0.2"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("0.15"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.1"));
		super.activation = (caster, skill, result) -> {
			if (result instanceof EntityHitResult entityResult) {
				if (entityResult.getEntity() instanceof LivingEntity target) {
					ICultivation casterCultivation = Cultivation.get(caster);
					BigDecimal skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
					int amplifier = Math.min(9, (int)Math.sqrt(Math.sqrt(skillStrength.intValue())));
					if (target == caster) {
						target.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40+amplifier*20, amplifier, true, false));
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40+amplifier*20, amplifier, true, false));
					} else {
						target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40+amplifier*20, amplifier, true, false));
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40+amplifier*20, amplifier, true, false));
					}
				}
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.TEMPORAL_TEARING.get();
	}
}
