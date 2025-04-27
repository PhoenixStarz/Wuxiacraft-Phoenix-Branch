package com.airesnor.wuxiacraft.items;

import com.airesnor.wuxiacraft.cultivation.Cultivation;
import com.airesnor.wuxiacraft.cultivation.ICultivation;
import com.airesnor.wuxiacraft.cultivation.skills.ISkillCap;
import com.airesnor.wuxiacraft.cultivation.skills.Skill;
import com.airesnor.wuxiacraft.cultivation.skills.Skills;
import com.airesnor.wuxiacraft.networking.NetworkWrapper;
import com.airesnor.wuxiacraft.networking.SkillCapMessage;
import com.airesnor.wuxiacraft.entities.mobs.WanderingCultivator;
import com.airesnor.wuxiacraft.utils.CultivationUtils;
import com.airesnor.wuxiacraft.world.event.Events;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemDemonBlade extends ItemBase {

	private double amount;
	private double amount2;
	private int soul;
	private int soulPK;

	public ItemDemonBlade(String item_name) {
		super(item_name);
		this.amount = 0;
		this.amount2 = 0;
		this.soul = 0;
		this.soulPK = 0;
		setMaxStackSize(1);
	}
	
	@Override
	@ParametersAreNonnullByDefault
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	public ItemDemonBlade setAmount(double amount) {
		this.amount = amount;
		return this;
	}

	public ItemDemonBlade setAmount2(double amount2) {
		this.amount2 = amount2;
		return this;
	}

	public double getAmount() {
		return amount;
	}
	public double getAmount2() {
		return amount2;
	}

	public int getSoul() {
		return soul;
	}
	public int getSoulPK() {
		return soulPK;
	}

	public void addSoul() {
		this.soul++;
	}
	public void addSoulPK() {
		this.soulPK++;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		ICultivation cultivation = CultivationUtils.getCultivationFromEntity(attacker);
		ICultivation targetCultivation = CultivationUtils.getCultivationFromEntity(target);
		if (target.getHealth() <= 0) {
			if(attacker instanceof EntityPlayer) {
				if(target instanceof EntityPlayer) {
					CultivationUtils.cultivatorAddProgress(attacker, Cultivation.System.DIVINE, (targetCultivation.getEssenceLevel().getProgressBySubLevel(targetCultivation.getEssenceSubLevel())*this.getAmount()), false, true);
					if (Events.getWorldEvent3() == true) {this.addSoulPK();}
				} 
				else if(target instanceof WanderingCultivator) {
					CultivationUtils.cultivatorAddProgress(attacker, Cultivation.System.DIVINE, (targetCultivation.getEssenceLevel().getProgressBySubLevel(targetCultivation.getEssenceSubLevel())*this.getAmount2()), false, true);
					if (Events.getWorldEvent3() == true) {this.addSoul();}
				}
			}
			ISkillCap skillCap = CultivationUtils.getSkillCapFromEntity(attacker);
			if (this.getSoul() == 100 && !attacker.world.isRemote) {
				skillCap.addSkill(Skills.DEMONIC_WILL);
				TextComponentString text = new TextComponentString("Added skill Demonic Will");
				attacker.sendMessage(text);
				NetworkWrapper.INSTANCE.sendTo(new SkillCapMessage(skillCap, false, attacker.getUniqueID()), (EntityPlayerMP) attacker);
				stack.shrink(1);
			} else if (this.getSoulPK() == 50 && !attacker.world.isRemote) {
				skillCap.addSkill(Skills.DEMONIC_TRANSFORMATION);
				TextComponentString text = new TextComponentString("Added skill Demonic Transformation");
				attacker.sendMessage(text);
				NetworkWrapper.INSTANCE.sendTo(new SkillCapMessage(skillCap, false, attacker.getUniqueID()), (EntityPlayerMP) attacker);
				stack.shrink(1);
			}
		}
		return true;
	}
}
