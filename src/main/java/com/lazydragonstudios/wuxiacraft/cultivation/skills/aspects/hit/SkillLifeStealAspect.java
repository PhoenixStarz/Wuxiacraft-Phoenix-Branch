package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.SkillBeamAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;

public class SkillLifeStealAspect extends SkillHitAspect {

	public SkillLifeStealAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("2"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.4"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.8"));
		this.activation = (caster, skill, hitResult) -> {
			if (hitResult.getType() == HitResult.Type.BLOCK) return false;
			if (hitResult.getType() == HitResult.Type.MISS) return false;
			if (!(hitResult instanceof EntityHitResult entityResult)) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			BigDecimal healedAmount = skillStrength.multiply(new BigDecimal("0.55"));
			if (entityResult.getEntity() instanceof LivingEntity target) {
				var damage = skillStrength;
				ResourceKey<DamageType> damageType = WuxiaDamageTypes.SKILL_ATTACK;
				if(skill.getSkillChain().get(0) instanceof SkillBeamAspect) damageType = WuxiaDamageTypes.SKILL_BEAM_ATTACK;
				var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
				.getHolderOrThrow(damageType), WuxiaElements.PHYSICAL.get(), caster, damage);
				target.hurt(damageSource, damageSource.getDamage().floatValue());
				caster.heal(healedAmount.floatValue());
			}
			return true;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.LIFE_STEAL.get();
	}
}
