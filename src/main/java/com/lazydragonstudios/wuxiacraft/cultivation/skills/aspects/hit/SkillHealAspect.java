package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationEventHandler;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;

public class SkillHealAspect extends SkillHitAspect {

	public SkillHealAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("2"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("0.8"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.2"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("0.8"));
		this.activation = (caster, skill, hitResult) -> {
			if (hitResult.getType() == HitResult.Type.BLOCK) return false;
			if (hitResult.getType() == HitResult.Type.MISS) return false;
			if (!(hitResult instanceof EntityHitResult result)) return false;
			var target = result.getEntity();
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			BigDecimal healedAmount = skillStrength.multiply(new BigDecimal("0.55"));
			if (target instanceof LivingEntity targetLiving) {
				targetLiving.heal(healedAmount.floatValue());
			}
			return true;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.HEAL.get();
	}
}
