package com.lazydragonstudios.wuxiacraft.entity;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Desert_Snake extends Snake {

	public Desert_Snake(EntityType<? extends Snake> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 48.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.65D)
				.add(Attributes.ATTACK_DAMAGE, 6D);
	}
	
}
