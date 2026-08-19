package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;

public class SkillAttackAspect extends SkillHitAspect {

	public SkillAttackAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("1.2"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("2"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.8"));
		this.activation = (caster, skill, result) -> {
			if (result instanceof EntityHitResult entityResult) {
				if (entityResult.getEntity() instanceof LivingEntity target) {
					var casterCultivation = Cultivation.get(caster);
					var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
					var damage = skillStrength.multiply(new BigDecimal(2));
					ResourceKey<DamageType> damageType = WuxiaDamageTypes.SKILL_ATTACK;
					if(skill.getSkillChain().get(0) instanceof SkillBeamAspect) damageType = WuxiaDamageTypes.SKILL_BEAM_ATTACK;
					var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
							.getHolderOrThrow(damageType), WuxiaElements.PHYSICAL.get(), caster, damage);
					target.hurt(damageSource, damageSource.getDamage().floatValue());
				}
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.ATTACK.get();
	}
}
