package com.lazydragonstudios.wuxiacraft.combat;

import com.lazydragonstudios.wuxiacraft.cultivation.Element;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WuxiaDamageSource extends DamageSource {

	private final Element element;
	private final Entity trueSource;
	private final BigDecimal damage;
	private boolean instantDeath;

	public WuxiaDamageSource(Holder<DamageType> damageTypeIn, Element element, BigDecimal damage) {
		this(damageTypeIn, element, null, damage);
	}

	public WuxiaDamageSource(Holder<DamageType> damageTypeIn, Element element, @Nullable Entity trueSource, BigDecimal damage) {
		super(damageTypeIn);
		this.element = element;
		this.trueSource = trueSource;
		this.damage = damage;
	}

	public WuxiaDamageSource setInstantDeath() {
		this.instantDeath = true;
		return this;
	}

	public Element getElement() {
		return this.element;
	}

	@Nullable
	@Override
	public Entity getEntity() {
		return this.trueSource;
	}

	public BigDecimal getDamage() {
		return this.damage;
	}

	public boolean isInstantDeath() {
		return instantDeath;
	}

	@Nonnull
	@Override
	public Component getLocalizedDeathMessage(@Nonnull LivingEntity target) {
		if (this.trueSource == null) return super.getLocalizedDeathMessage(target);
		Component trueSourceName = this.trueSource.getDisplayName();
		ItemStack stack = this.trueSource instanceof LivingEntity source ? source.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		String localizedMessageId = "death.attack." + this.getMsgId();
		String aboveButWithItem = localizedMessageId + ".item";
		return !stack.isEmpty() ?
				Component.translatable(aboveButWithItem, target.getDisplayName(), trueSourceName, stack.getDisplayName()) :
				Component.translatable(localizedMessageId, target.getDisplayName(), trueSourceName);
	}
}
