package com.lazydragonstudios.wuxiacraft.cultivation;

import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.*;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.TechniqueContainer;
import com.lazydragonstudios.wuxiacraft.init.WuxiaConfigs;
import com.lazydragonstudios.wuxiacraft.init.WuxiaMobEffects;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.apache.commons.lang3.tuple.Pair;
import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class DivineCultivationContainer extends SystemContainer {

	public Pair<ResourceKey<Level>, BlockPos> storedLocation;

	/**
	 * The constructor for this system cultivation stats
	 *
	 * @param system the system this belongs to
	 */
	public DivineCultivationContainer() {
		super(System.DIVINE);
	}
	
	@Override
	public void addCultivationBase(Player player, ICultivation cultivation, BigDecimal amount, HashMap<ResourceLocation, BigDecimal> elementHash) {
		cultivation.getSystemData(System.BODY).consumeEnergy(amount.multiply(new BigDecimal("0.3")));
		//all initialized data so that orders can change around
		var elements = this.techniqueData.modifier.elements;
		amount = super.handleCultivationBaseModifiers(player, cultivation, amount);
		//Adds foundation and comprehension
		for (var elementLocation : elements.keySet()) {
			BigDecimal modifier = BigDecimal.ONE;
			if (elementHash.keySet().contains(elementLocation)) modifier = elementHash.get(elementLocation);
			cultivation.addStat(system, elementLocation, PlayerSystemElementalStat.FOUNDATION, BigDecimal.valueOf(elements.get(elementLocation) * 0.1).multiply(amount).multiply(modifier));
			cultivation.addStat(elementLocation, PlayerElementalStat.COMPREHENSION, BigDecimal.valueOf(elements.get(elementLocation)).multiply(modifier));
		}
		super.handleAspectProficiencyGain(player, cultivation, amount, elementHash);
		//Applies Enlightenment
		if (player.hasEffect(WuxiaMobEffects.ENLIGHTENMENT.get())) {
			var instance = player.getEffect(WuxiaMobEffects.ENLIGHTENMENT.get());
			if (instance != null) {
				var amplifier = instance.getAmplifier();
				//amount = amount * (1 + (2 ^ amplifier))
				amount = amount.multiply(BigDecimal.ONE.add(new BigDecimal("2").pow(amplifier)));
			}
		}
		var cultSpeed = cultivation.getStat(system, PlayerSystemStat.CULTIVATION_SPEED);
		amount = amount.add(cultSpeed);
		//adds the base
		cultivation.addStat(system, PlayerSystemStat.CULTIVATION_BASE, amount);
	}

	public void setStoredLocation(ResourceKey<Level> level, BlockPos pos) {
		this.storedLocation = Pair.of(level, pos);
	}

	public Pair<ResourceKey<Level>, BlockPos> getStoredLocation() {
		return this.storedLocation;
	}

	@Override
	public CompoundTag serialize() {
		CompoundTag containerTag = super.serialize();
		Pair<ResourceKey<Level>, BlockPos> pair = this.getStoredLocation();
		if (pair != null)
		if (pair.getKey() !=null && pair.getValue() != null) {
			ResourceKey<Level> level = pair.getKey();
			BlockPos pos = pair.getValue();
			containerTag.putLong("blockpos", pos.asLong());
			containerTag.putString("dimension", level.location().toString());
		}
		return containerTag;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		if (tag.contains("blockpos") && tag.contains("dimension")) {
			BlockPos pos = BlockPos.of(tag.getLong("blockpos"));
			String dimStr = tag.getString("dimension");
			ResourceLocation dimLoc = new ResourceLocation(dimStr);
			ResourceKey<Level> dimKey = ResourceKey.create(Registries.DIMENSION, dimLoc);
			this.setStoredLocation(dimKey, pos);
		}
		super.deserialize(tag);
	}
}
