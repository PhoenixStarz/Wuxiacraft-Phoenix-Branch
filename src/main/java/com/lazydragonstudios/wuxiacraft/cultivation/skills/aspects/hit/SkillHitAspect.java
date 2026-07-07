package com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.hit;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillDescriptor;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.SkillStat;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillHitModifierAspect;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.NumberRangeParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaSkillAspects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;

import java.math.BigDecimal;
import java.util.LinkedList;

public class SkillHitAspect extends SkillAspect {

	ISkillHitAction activation;

	public SkillHitAspect(ICultivation cultivation) {
		super(cultivation);
		this.skillParameters.put("strength_multiplier", new NumberRangeParameter("strength_multiplier", BigDecimal.ZERO, BigDecimal.ONE));
		this.activation = (p, l, r) -> false;
	}

	public SkillAspect setActivation(ISkillHitAction activation) {
		this.activation = activation;
		return this;
	}

	@Override
	public SkillAspectType getType() {
		return WuxiaSkillAspects.PUNCH.get();
	}

	public boolean activate(Player player, SkillDescriptor skill, HitResult result) {
		return this.activation.hit(player, skill, result);
	}

	@Override
	public boolean canConnect(SkillAspect aspect) {
		return aspect instanceof SkillHitModifierAspect;
	}

	@Override
	public boolean canCompile(LinkedList<SkillAspect> aspectChain) {
		return false;
	}

	@Override
	public BigDecimal applyStat(ICultivation cultivation, SkillStat stat, BigDecimal currentValue) {
		var strengthMultiplier = (BigDecimal) this.skillParameters.get("strength_multiplier").getValue();
		return super.applyStat(cultivation, stat, currentValue).multiply(strengthMultiplier);
	}
}
