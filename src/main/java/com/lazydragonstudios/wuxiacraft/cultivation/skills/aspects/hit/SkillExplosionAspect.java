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
import net.minecraft.world.level.Level;

import java.math.BigDecimal;

public class SkillExplosionAspect extends SkillHitAspect {

	public SkillExplosionAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("4"));
		setSkillStat(SkillStat.STRENGTH, new BigDecimal("4"));
		setSkillStat(SkillStat.CAST_TIME, new BigDecimal("1.8"));
		setSkillStat(SkillStat.COOLDOWN, new BigDecimal("2.2"));
		this.activation = (caster, skill, result) -> {
			if (result == null) return false;
			if (caster == null) return false;
			var pos = result.getLocation();
			if (caster.level().isClientSide()); else {
			var interaction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(caster.level(), caster) ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE;
			boolean fire = false;
			var casterCultivation = Cultivation.get(caster);
			var skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);
			BigDecimal damage = skillStrength.multiply(new BigDecimal("3"));
			var damageSource = new WuxiaDamageSource(caster.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.SKILL_EXPLOSION), WuxiaElements.FIRE.get(), caster, damage);
			caster.level().explode(caster, damageSource, null, pos.x, pos.y, pos.z, (float) Math.max(Math.sqrt(damage.floatValue()), 1.0), fire, interaction);
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.EXPLOSION.get();
	}
}
