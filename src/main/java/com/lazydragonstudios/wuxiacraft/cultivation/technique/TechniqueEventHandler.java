package com.lazydragonstudios.wuxiacraft.cultivation.technique;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.SystemContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.ConditionalElementalGenerator;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.WeaponElementalGenerator;
import com.lazydragonstudios.wuxiacraft.blocks.TechniqueInscriber;
import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import com.lazydragonstudios.wuxiacraft.item.TechniqueManual;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.networking.RequestTechniqueDataChange;
import com.lazydragonstudios.wuxiacraft.networking.WeaponSwingMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.math.BigDecimal;
import java.util.HashMap;

@Mod.EventBusSubscriber
public class TechniqueEventHandler {

	@SubscribeEvent
	public static void onCultivateCustomAspect(CultivatingEvent event) {
		var techniqueData = Cultivation.get(event.getPlayer()).getSystemData(event.getSystem()).techniqueData;
		var grid = techniqueData.grid;
		for (var aspectLocation : grid.getGrid().values()) {
			var aspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspectLocation);
			if (aspect == null) continue;
			if (event.isCanceled()) break;
			if (aspect instanceof ConditionalElementalGenerator generator && !event.getElement().keySet().contains(generator.element)) {
				generator.onCultivate(event);
			}
		}
	}

	private static void sendSuccessLearning(Player player, ResourceLocation aspect) {
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.learn_successful")
							.append(Component.translatable("wuxiacraft.aspect." + aspect.getPath() + ".name")),
					true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onStruckByLightning(EntityStruckByLightningEvent event) {
		  var Entity = event.getEntity();
		  if (Entity instanceof Player) {
			 Player player = (Player)Entity;
			 ICultivation cultivation = Cultivation.get(player);
			 var aspects = cultivation.getAspects();
			if (Math.random() * 100d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.SPARK.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.SPARK.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.SPARK.getId());
				}}
			if (Math.random() * 300d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.CIRCUIT.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.CIRCUIT.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.CIRCUIT.getId());
				}}
			if (Math.random() * 900d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.THUNDERING.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.THUNDERING.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.THUNDERING.getId());
				}}
			if (Math.random() * 300d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.CONDUIT.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.CONDUIT.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.CONDUIT.getId());
				}}
			if (Math.random() * 300d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.ARC.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.ARC.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.ARC.getId());
				}}
			if (Math.random() * 300d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.FLASH.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.FLASH.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.FLASH.getId());
				}}
		}
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		var player = event.getPlayer();
		ICultivation cultivation = Cultivation.get(player);
		var aspects = cultivation.getAspects();
		if (aspects.knowsAspect(WuxiaTechniqueAspects.START.getId())) {
			if (cultivation.getRebirths() > 0)
			aspects.learnRebirthAspects(cultivation);
			HashMap<ResourceLocation, Double> aspectsPerBlock = TechniqueUtil.getAspectChancePerBlock(event.getState().getBlock());
			for (var aspect : aspectsPerBlock.keySet()) {
			double randomVal = Math.random() * aspectsPerBlock.get(aspect);
			if (randomVal < 1d) {
				if (!aspects.knowsAspect(aspect)) {
				aspects.learnAspect(aspect, cultivation);
					if (aspects.knowsAspect(aspect)) {
						sendSuccessLearning(player, aspect);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onKillEntity(LivingDeathEvent event) {
		var entityType = event.getEntity().getType();
		var entityChancedAspects = TechniqueUtil.getAspectChancePerEntity(entityType);
		if (entityChancedAspects == null || entityChancedAspects.isEmpty()) return;
		var killer = event.getSource().getEntity();
		if (!(killer instanceof Player player)) return;
		ICultivation cultivation = Cultivation.get(player);
		var aspects = cultivation.getAspects();
		if (aspects.knowsAspect(WuxiaTechniqueAspects.START.getId())) {
			for(var aspect : entityChancedAspects.keySet()) {
			double randomVal = Math.random() * entityChancedAspects.get(aspect);
			if (randomVal < 1d) {
				if (!aspects.knowsAspect(aspect)) {
				aspects.learnAspect(aspect, cultivation);
					if (aspects.knowsAspect(aspect)) {
						sendSuccessLearning(player, aspect);
						}
					}
				}	
			}
		}
	}

	/*
	*  Shift-Right click on technique inscriber to copy Manual technique into player's grid.
	*
	*/
	@SubscribeEvent
	public static void onCopyFromTechniqueInscriber(PlayerInteractEvent.RightClickBlock event) {
		Level level = event.getLevel();
    	BlockPos pos = event.getPos();
    	BlockState state = level.getBlockState(pos);
    	Block block = state.getBlock();
		Player player = event.getEntity();
		if (!(block instanceof TechniqueInscriber)) return;
		if (!player.isCrouching()) return;
		ItemStack itemstack = player.getMainHandItem();
		if (itemstack.isEmpty()) return;
		if (!(itemstack.getItem() instanceof TechniqueManual manual)) return;
		var itemTag = itemstack.getTag();
		if (itemTag == null) return;
		if (!(itemstack.getTag().contains("technique-grid"))) return;
		TechniqueGrid techGrid = new TechniqueGrid();
		techGrid.deserialize(itemTag.getCompound("technique-grid"));
		ICultivation cultivation = Cultivation.get(player);
		System system = manual.getSystem();
		SystemContainer systemData = cultivation.getSystemData(system);
		BigDecimal playerRadius = BigDecimal.ONE.add(cultivation.getStat(system, PlayerSystemStat.ADDITIONAL_GRID_RADIUS));
		AspectContainer aspectData = cultivation.getAspects();
		for (var hexC : techGrid.getGrid().keySet()) {
			var aspectLocation = techGrid.getAspectAtGrid(hexC);
			var techAspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspectLocation);
			if (techAspect == null) continue;
			if (!aspectData.knowsAspect(aspectLocation)) {
				if (player instanceof ServerPlayer serverPlayer) {
					serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.copy_missing_aspects"), true);
				}
				return;
			}
		}
		int radius = 0;
		if (itemTag.contains("radius")) {
			radius = itemTag.getInt("radius");
		}
		String name = null;
		if (itemTag.contains("name")) {
			name = itemTag.getString("name");
		}
		if (playerRadius.compareTo(BigDecimal.valueOf(radius)) < 0) {
			if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.copy_insufficient_radius"), true);
			}
			return;
		}
		systemData.techniqueData.grid.deserialize(techGrid.serialize());
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(Component.translatable(name).append(" ")
			.append(Component.translatable("wuxiacraft.copy_successful"))
			, true);
		} else 
		WuxiaPacketHandler.INSTANCE.sendToServer(new RequestTechniqueDataChange(system, systemData.techniqueData.serialize()));
	}

		

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onSwingWeapon(PlayerInteractEvent.LeftClickEmpty event) {
		if (!event.getEntity().level().isClientSide()) return;
		var player = event.getEntity();
		if (Minecraft.getInstance().player != player) return;
		ICultivation cultivation = Cultivation.get(player);
		var aspects = cultivation.getAspects();
		if (cultivation.isCombat()) return;
		if (!aspects.knowsAspect(WuxiaTechniqueAspects.START.getId())) return;
		var heldItem = player.getMainHandItem();
		boolean isValidWeapon = false;
		WeaponElementalGenerator.WeaponType validWeaponType = null;
		for (var weaponType : WeaponElementalGenerator.WeaponType.values()) {
			if (weaponType.weaponItemType == null) continue;
			if (weaponType.weaponItemType.isInstance(heldItem.getItem())) {
				isValidWeapon = true;
				validWeaponType = weaponType;
				break;
			}
		}
		if (!isValidWeapon && heldItem.isEmpty()) {
			isValidWeapon = true;
			validWeaponType = WeaponElementalGenerator.WeaponType.FIST;
		}
		if (!isValidWeapon) return;
		for (var system : System.values()) {
			var systemData = cultivation.getSystemData(system);
			var weaponStats = systemData.techniqueData.modifier.getWeaponStats();
			if (weaponStats.containsKey(validWeaponType)) {
				var weaponGenerationValue = weaponStats.get(validWeaponType).multiply(new BigDecimal("0.25"));
				var attackStrength = BigDecimal.valueOf(player.getAttackStrengthScale(0.0f));
				cultivation.addCultivationBase(player, system, weaponGenerationValue.multiply(attackStrength));
				WuxiaPacketHandler.INSTANCE.sendToServer(new WeaponSwingMessage(system, weaponGenerationValue.multiply(attackStrength)));
			}
		}
	}
}
