package com.airesnor.wuxiacraft.items;

import com.airesnor.wuxiacraft.utils.TeleportationUtil;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemReturnTalisman extends ItemBase {

	public ItemReturnTalisman(String item_name) {
		super(item_name);
		setMaxStackSize(1);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayerMP playerMP, EnumHand handIn) {
		ItemStack actualTalisman = playerMP.getHeldItem(EnumHand.MAIN_HAND);
		NBTTagCompound tag = actualTalisman.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		boolean activated = false;
		BlockPos targetPos = new BlockPos(0, 0, 0);
			int usageStep = tag.hasKey("usageStep") ? tag.getInteger("usageStep") : 0;
			switch (usageStep) {
				case 0:
					targetPos = worldIn.getSpawnPoint();
					tag.setInteger("usageStep", 1); // to use next
					tag.setInteger("iniX", (int) playerMP.posX);
					tag.setInteger("iniY", (int) playerMP.posY);
					tag.setInteger("iniZ", (int) playerMP.posZ);
					activated = true;
					break;
			}
		actualTalisman.setTagCompound(tag);
		if (activated) {
			TeleportationUtil.teleportPlayerToDimension((EntityPlayerMP) playerMP, 0, targetPos.getX(), targetPos.getY(), targetPos.getZ(), 0f, 0f);
			actualTalisman.shrink(1);
			if (actualTalisman.getCount() <= 0) {
				actualTalisman = ItemStack.EMPTY;
			}
		}
		return new ActionResult<>(activated ? EnumActionResult.SUCCESS : EnumActionResult.PASS, actualTalisman);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		int usageStep = tag.hasKey("usageStep") ? tag.getInteger("usageStep") : 0;
		int x = 0;
		int y = 0;
		int z = 0;
		if (worldIn != null) {
			x = tag.hasKey("iniX") ? tag.getInteger("iniX") : worldIn.getSpawnPoint().getX();
			y = tag.hasKey("iniY") ? tag.getInteger("iniY") : worldIn.getSpawnPoint().getY();
			z = tag.hasKey("iniZ") ? tag.getInteger("iniZ") : worldIn.getSpawnPoint().getZ();
		}
		tooltip.add(TextFormatting.GOLD + "Going to world center");
		tooltip.add(TextFormatting.GOLD + "x = " + x + ", y = " + y + ", z = " + z);
	}
}
