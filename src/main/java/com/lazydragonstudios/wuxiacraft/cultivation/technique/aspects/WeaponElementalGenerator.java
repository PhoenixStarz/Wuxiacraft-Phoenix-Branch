package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.math.BigDecimal;
import java.util.HashMap;

public class WeaponElementalGenerator extends ElementalGenerator {

	public enum WeaponType {
		SWORD(SwordItem.class),
		AXE(AxeItem.class),
		BOW(BowItem.class),
		FIST(null);

		public final Class<? extends Item> weaponItemType;

		WeaponType(Class<? extends Item> weaponItemType) {
			this.weaponItemType = weaponItemType;
		}
	}

	public final WeaponType weaponType;

	public WeaponElementalGenerator(double generated, ResourceLocation element, WeaponType weaponType) {
		super(generated, element);
		this.weaponType = weaponType;
	}

	public boolean isWeaponType(ItemStack itemStack) {
		if (this.weaponType == null) {
			return itemStack.isEmpty();
		}
		return this.weaponType.weaponItemType.isInstance(itemStack.getItem());
	}

	@Override
	public void accept(HashMap<String, Object> metaData, BigDecimal proficiency) {
		super.accept(metaData, proficiency);
		var modifierName = "weapon-" + this.weaponType.name().toLowerCase();
		var modifier = this.getCurrentCheckpoint(proficiency).modifier();
		var generated = this.generated * (1 + modifier.doubleValue());
		metaData.put(modifierName, BigDecimal.valueOf(generated).add((BigDecimal) metaData.getOrDefault(modifierName, BigDecimal.ZERO)));
	}
}
