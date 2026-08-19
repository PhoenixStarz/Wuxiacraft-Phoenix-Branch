package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;

public class SkillLightningAspect extends SkillHitAspect {

	public SkillLightningAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("3"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("3"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.4"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("1.8"));
		this.activation = (caster, skill, result) -> {
			if (result == null) return false;
			if (caster == null) return false;
			var pos = result.getLocation();
			if (caster.level().isClientSide()) return false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(caster.level());
            lightningbolt.moveTo(pos.x, pos.y, pos.z);
            lightningbolt.setDamage(skillStrength.floatValue()*4f);
            caster.level().addFreshEntity(lightningbolt);
			return true;
		};
	}
		

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SUMMON_LIGHTNING.get();
	}
}
