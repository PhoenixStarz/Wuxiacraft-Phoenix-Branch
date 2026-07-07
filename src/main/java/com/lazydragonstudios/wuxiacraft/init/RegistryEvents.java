package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationRealm;
import com.lazydragonstudios.wuxiacraft.cultivation.CultivationStage;
import com.lazydragonstudios.wuxiacraft.cultivation.Element;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPart;
import com.lazydragonstudios.wuxiacraft.cultivation.body.BodyPartType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.aspects.SkillAspectType;
import com.lazydragonstudios.wuxiacraft.cultivation.skills.parameter.SkillParameter;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import com.lazydragonstudios.wuxiacraft.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

	@SubscribeEvent
	public static void onCreateNewRegistries(final NewRegistryEvent event) {
		WuxiaCraft.LOGGER.info("Creating registries");

		WuxiaCraft.LOGGER.debug("Creating the registry for the Cultivation Realms");
		WuxiaRegistries.CULTIVATION_REALMS = event.create(new RegistryBuilder<CultivationRealm>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "cultivation_realms")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Cultivation Stages");
		WuxiaRegistries.CULTIVATION_STAGES = event.create(new RegistryBuilder<CultivationStage>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "cultivation_stages")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Elements");
		WuxiaRegistries.ELEMENTS = event.create(new RegistryBuilder<Element>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "elements")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Technique Aspects");
		WuxiaRegistries.TECHNIQUE_ASPECT = event.create(new RegistryBuilder<TechniqueAspect>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "technique_aspects")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Skills Aspects");
		WuxiaRegistries.SKILL_ASPECT = event.create(new RegistryBuilder<SkillAspectType>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "skill_aspects")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Body Part Types");
		WuxiaRegistries.BODY_PART_TYPE = event.create(new RegistryBuilder<BodyPartType>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "body_part_types")));

		WuxiaCraft.LOGGER.debug("Creating the registry for the Body Parts");
		WuxiaRegistries.BODY_PART = event.create(new RegistryBuilder<BodyPart>()
				.setName(new ResourceLocation(WuxiaCraft.MOD_ID, "body_parts")));
	}

	@SubscribeEvent
	public static void onCreateEntityAttributes(final EntityAttributeCreationEvent event) {
		event.put(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), Snake.createAttributes().build());
		event.put(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), Desert_Snake.createAttributes().build());
		event.put(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), Red_Snake.createAttributes().build());
		event.put(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), White_Snake.createAttributes().build());
	}

	@SubscribeEvent
	public static void something(final SpawnPlacementRegisterEvent event) {
		event.register(WuxiaEntities.SNAKE_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(WuxiaEntities.DESERT_SNAKE_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(WuxiaEntities.RED_SNAKE_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(WuxiaEntities.WHITE_SNAKE_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

}