package com.airesnor.wuxiacraft.entities.skills;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ThunderBoltThrowable extends EntityThrowable implements IEntityAdditionalSpawnData {

	private float damage;
	private int duration;
	public float roll;

	private EntityLivingBase owner;

	@SuppressWarnings("unused")
	public ThunderBoltThrowable(World worldIn) {
		super(worldIn);
	}

	public ThunderBoltThrowable(World worldIn, EntityLivingBase owner, float damage, int duration) {
		super(worldIn, owner.posX, owner.posY + owner.getEyeHeight() - 0.1, owner.posZ);
		this.owner = owner;
		this.damage = damage;
		this.duration = duration;
		this.setNoGravity(true);
		this.handleWaterMovement();
		setSize(0.5f, 1f);
		this.roll = -30f + this.world.rand.nextFloat() * 60f;
	}

	@Override
	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
		float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		this.shoot(f, f1, f2, velocity, inaccuracy);
	}

	@Override
	public void onUpdate() {
		if (this.inWater) {
			this.motionX *= 1.3f / 0.81f;
			this.motionY *= 1.3f / 0.81f;
			this.motionZ *= 1.3f / 0.81f;
		}
		super.onUpdate();
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		if (this.ticksExisted >= this.duration) {
			this.setDead();
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.typeOfHit == RayTraceResult.Type.ENTITY && !result.entityHit.equals(this.owner)) {
			if (result.entityHit instanceof EntityLivingBase) {
				if (!this.world.isRemote) {
					this.attackEntityOnDirectHit((EntityLivingBase) result.entityHit);
					this.setDead();
				}
			}
		} else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			if (canNotPassThroughHitBlock(result)) {
				this.setDead();
			}
		}
	}

	private void attackEntityOnDirectHit(EntityLivingBase hitEntity) {
		float damage = this.damage;
		if(hitEntity.isInWater()) {
			damage *= 4f;
			AxisAlignedBB range = new AxisAlignedBB(new BlockPos(hitEntity.posX, hitEntity.posY, hitEntity.posZ)).grow(32f);
			List<EntityLivingBase> targets = hitEntity.world.getEntitiesWithinAABB(EntityLivingBase.class, range, t -> {
				assert t != null;
				return t.isInWater();
			});
			for(EntityLivingBase target : targets) {
				target.attackEntityFrom(DamageSource.causeMobDamage(owner).setMagicDamage().setProjectile(), this.damage);
			}
		}
		hitEntity.attackEntityFrom(DamageSource.causeMobDamage(owner).setMagicDamage().setProjectile(), damage);

		if (this.owner != null) {
			hitEntity.setLastAttackedEntity(this.owner);
		}
	}

	private boolean canNotPassThroughHitBlock(RayTraceResult result) {
		BlockPos hitBlockPos = result.getBlockPos();
		IBlockState hitState = this.world.getBlockState(hitBlockPos);
		return hitState.getMaterial().blocksMovement();
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.duration);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.duration = additionalData.readInt();
	}
}
