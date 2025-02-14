package com.airesnor.wuxiacraft.entities.mobs;

import com.airesnor.wuxiacraft.WuxiaCraft;
import com.airesnor.wuxiacraft.cultivation.BaseSystemLevel;
import com.airesnor.wuxiacraft.cultivation.skills.Skills;
import com.airesnor.wuxiacraft.entities.ai.EntityAIReleaseSkills;
import com.airesnor.wuxiacraft.world.dimensions.WuxiaDimensions;
import com.airesnor.wuxiacraft.utils.MathUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GiantAnt extends EntityCultivator implements IMob {

	private static final ResourceLocation DROP_TABLE_1 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l1");
	private static final ResourceLocation DROP_TABLE_2 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l2");
	private static final ResourceLocation DROP_TABLE_3 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l3");
	private static final ResourceLocation DROP_TABLE_4 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l4");
	private static final ResourceLocation DROP_TABLE_5 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l5");
	private static final ResourceLocation DROP_TABLE_6 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l6");
	private static final ResourceLocation DROP_TABLE_7 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l7");
	private static final ResourceLocation DROP_TABLE_8 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l8");
	private static final ResourceLocation DROP_TABLE_9 = new ResourceLocation(WuxiaCraft.MOD_ID, "entities/giant_ant_l9");

	public GiantAnt(World worldIn) {
		super(worldIn);
		setSize(1.7f, 0.7f);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIReleaseSkills(this));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.6D, false));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.4D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyCultivation(World world) {
		if(world.provider.getDimensionType().getId() == 0) {
			int result = world.rand.nextInt(100);
			BaseSystemLevel aux = BaseSystemLevel.DEFAULT_ESSENCE_LEVEL.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 0, 30)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 7;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 31, 41)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 10;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 42, 47)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 15;
			}
			result = 1+world.rand.nextInt(35);
			this.cultivation.setEssenceSubLevel(5-(int)Math.floor(Math.sqrt(result)));
			result = world.rand.nextInt(100);
			if(result < 50) {
				skillCap.addSkill(Skills.WIND_BLADE);
				if(MathUtils.between(result, 0, 5)) {
					skillCap.addSkill(Skills.WIND_BLADE);
				}
			} else {
				skillCap.addSkill(Skills.WATER_NEEDLE);
				if(MathUtils.between(result,50, 55)) {
					skillCap.addSkill(Skills.WATER_BLADE);
				}
			}
		}

		if(world.provider.getDimensionType().getId() == WuxiaDimensions.MINING.getId() || world.provider.getDimensionType().getId() == WuxiaDimensions.ELEMENTAL.getId()) {
			int result = world.rand.nextInt(100);
			BaseSystemLevel aux = BaseSystemLevel.DEFAULT_ESSENCE_LEVEL.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);//third level minimum
			if(MathUtils.between(result, 48, 100)) { // base level for other worlds
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 10;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 0, 30)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 15;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 31, 41)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 25;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS); // guess this should be immortal 3
			if(MathUtils.between(result, 42, 47)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 40;
			}
			result = 1+world.rand.nextInt(35);
			this.cultivation.setEssenceSubLevel(5-(int)Math.floor(Math.sqrt(result)));
			result = world.rand.nextInt(100);
			if(result < 50) {
				skillCap.addSkill(Skills.WIND_BLADE);
				skillCap.addSkill(Skills.WIND_BLADE);
			} else {
				skillCap.addSkill(Skills.WATER_NEEDLE);
				skillCap.addSkill(Skills.WATER_BLADE);
			}
		}

		if(world.provider.getDimensionType().getId() == WuxiaDimensions.SKY.getId()) {
			int result = world.rand.nextInt(100);
			BaseSystemLevel aux = BaseSystemLevel.DEFAULT_ESSENCE_LEVEL.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 48, 100)) { 
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 25;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 0, 30)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 40;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
			if(MathUtils.between(result, 31, 41)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 60;
			}
			aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS); 
			if(MathUtils.between(result, 42, 47)) {
				this.cultivation.setEssenceLevel(aux);
				this.experienceValue = 100;
			}
			result = 1+world.rand.nextInt(35);
			this.cultivation.setEssenceSubLevel(5-(int)Math.floor(Math.sqrt(result)));
			result = world.rand.nextInt(100);
			if(result < 50) {
				skillCap.addSkill(Skills.WIND_BLADE);
				skillCap.addSkill(Skills.WIND_BLADE);
			} else {
				skillCap.addSkill(Skills.WATER_BLADE);
				skillCap.addSkill(Skills.WATER_BLADE);
			}
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		ResourceLocation table = DROP_TABLE_1;
		BaseSystemLevel aux = BaseSystemLevel.DEFAULT_ESSENCE_LEVEL.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_2;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_3;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_4;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_5;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_6;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_7;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_8;
		}
		aux = aux.nextLevel(BaseSystemLevel.ESSENCE_LEVELS);
		if(this.getCultivationLevel() == aux) {
			table = DROP_TABLE_9;
		}
		return table;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
}
