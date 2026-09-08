package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit.modifier;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.BodyCultivationContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.activator.SkillBeamAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

import java.math.BigDecimal;

public class SkillShareCultivationAspect extends SkillHitModifierAspect {

	public SkillShareCultivationAspect(ICultivation cultivation) {
		super(cultivation);
		setSkillStat(SkillStat.COST, new BigDecimal("2"));
		super.activation = (caster, skill, result) -> {
			if (result instanceof EntityHitResult entityResult) {
				if (entityResult.getEntity() instanceof Player target) {
					ICultivation targetCultivation = Cultivation.get(target);
					ICultivation casterCultivation = Cultivation.get(caster);
					BodyCultivationContainer targetBodyData = (BodyCultivationContainer) targetCultivation.getSystemData(System.BODY);
					BodyCultivationContainer casterBodyData = (BodyCultivationContainer) casterCultivation.getSystemData(System.BODY);
					BigDecimal skillStrength = skill.getAppliedStats(casterCultivation, SkillStat.STRENGTH);

					for (System systems : System.values()) {
						BigDecimal amount = skillStrength.multiply(new BigDecimal(2));
						BigDecimal hasAmount = casterCultivation.getStat(systems, PlayerSystemStat.CULTIVATION_BASE);
						if (hasAmount.compareTo(amount) < 0) amount = hasAmount;
						if (systems == System.BODY) {
							targetBodyData.forgeAllParts(amount);
							casterBodyData.forgeAllParts(amount.negate());
						} else {
							targetCultivation.addStat(systems, PlayerSystemStat.CULTIVATION_BASE, amount);
							casterCultivation.addStat(systems, PlayerSystemStat.CULTIVATION_BASE, amount.negate());
						}
						targetCultivation.setStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION, 
							cultivation.getStat(System.ESSENCE, WuxiaElements.DEMONIC.getId(), PlayerSystemElementalStat.FOUNDATION)
								.subtract(amount.multiply(new BigDecimal(5))).max(BigDecimal.ZERO));
					}
				}
			}
			return false;
		};
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.SHARE_CULTIVATION.get();
	}
}
