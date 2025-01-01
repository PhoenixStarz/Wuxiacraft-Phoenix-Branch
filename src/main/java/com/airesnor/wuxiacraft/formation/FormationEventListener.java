package com.airesnor.wuxiacraft.formation;

import com.airesnor.wuxiacraft.utils.FormationUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class FormationEventListener implements IWorldEventListener {

	private final FormationTileEntity parent; //Reference to the formation
	private final FormationUtils.FormationDiagram diagram; // Reference to check the formation position
	private final EnumFacing direction; // Reference tho see which direction should check directly, (instead of checking every direction)

	public FormationEventListener(FormationTileEntity parent, FormationUtils.FormationDiagram diagram, EnumFacing direction) {
		this.parent = parent;
		this.diagram = diagram;
		this.direction = direction;
	}

	@Override
	public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
		List<BlockPos> posList = this.diagram.getAllBlockPos();
		BlockPos parentPos = this.parent.getPos();
		BlockPos toTest = new BlockPos(pos.getX()- parentPos.getX(), pos.getY() - parentPos.getY(), pos.getZ() - parentPos.getZ());
		if(posList.contains(FormationUtils.rotateBlockPosBack(toTest, this.direction))) {
			if(newState != diagram.getBlockState(pos)) {
				AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(),pos.getY(),pos.getZ(),pos.getX()+1,pos.getY()+1,pos.getZ()+1).grow(6);
				List<EntityLivingBase> interrupters = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, aabb, input -> true);
				this.parent.interruptFormation(pos, interrupters);
			}
		}
	}

	@Override
	public void notifyLightSet(BlockPos pos) {

	}

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {

	}

	@Override
	public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {

	}

	@Override
	public void playRecord(SoundEvent soundIn, BlockPos pos) {

	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {

	}

	@Override
	public void spawnParticle(int id, boolean ignoreRange, boolean p_190570_3_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {

	}

	@Override
	public void onEntityAdded(Entity entityIn) {

	}

	@Override
	public void onEntityRemoved(Entity entityIn) {

	}

	@Override
	public void broadcastSound(int soundID, BlockPos pos, int data) {

	}

	@Override
	public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {

	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {

	}
}
