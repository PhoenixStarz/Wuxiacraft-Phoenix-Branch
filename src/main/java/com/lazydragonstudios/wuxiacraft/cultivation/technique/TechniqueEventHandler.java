package com.lazydragonstudios.wuxiacraft.cultivation.technique;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.ConditionalElementalGenerator;
import com.lazydragonstudios.wuxiacraft.event.CultivatingEvent;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.lazydragonstudios.wuxiacraft.init.WuxiaTechniqueAspects;
import com.lazydragonstudios.wuxiacraft.util.TechniqueUtil;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;
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
			if (aspect instanceof ConditionalElementalGenerator generator) {
				generator.onCultivate(event);
			}
		}
	}


	private static void sendSuccessLearning(Player player, ResourceLocation aspect) {
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendMessage(new TranslatableComponent("wuxiacraft.learn_successful")
							.append(new TranslatableComponent("wuxiacraft.techinque" + aspect + ".name")),
					Util.NIL_UUID);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onStruckByLightning(EntityStruckByLightningEvent event) {
		  var Entity = event.getEntity();
		  if (Entity instanceof Player) {
			 Player player = (Player)Entity;
			 ICultivation cultivation = Cultivation.get(player);
			 var aspects = cultivation.getAspects();
			if (Math.random() * 250d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.SPARK.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.SPARK.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.SPARK.getId());
				}}
			if (Math.random() * 750d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.CIRCUIT.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.CIRCUIT.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.CIRCUIT.getId());
				}}
			if (Math.random() * 2250d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.THUNDERING.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.THUNDERING.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.THUNDERING.getId());
				}}
			if (Math.random() * 750d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.CONDUIT.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.CONDUIT.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.CONDUIT.getId());
				}}
			if (Math.random() * 750d < 1d) { 
				if (!aspects.knowsAspect(WuxiaTechniqueAspects.ARC.getId())) {
					aspects.learnAspect(WuxiaTechniqueAspects.ARC.getId(), cultivation);
					sendSuccessLearning(player, WuxiaTechniqueAspects.ARC.getId());
				}}
			if (Math.random() * 750d < 1d) { 
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
		EntityType<?> entityType = event.getEntity().getType();
		HashMap<ResourceLocation, Double> entityChancedAspects = TechniqueUtil.getAspectChancePerEntity(entityType);
		if (entityChancedAspects != null && !entityChancedAspects.isEmpty()) {
			Entity killer = event.getSource().getEntity();
			if (killer instanceof Player) {
				var player = (Player)killer;
				ICultivation cultivation = Cultivation.get(player);
				AspectContainer aspects = cultivation.getAspects();
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
	   	}
	}
}
