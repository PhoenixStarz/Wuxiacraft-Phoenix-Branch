package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.entity.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class WuxiaEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WuxiaCraft.MOD_ID);

	//placeholders to custom renderers

	public static final RegistryObject<EntityType<AbstractClientPlayer>> ANIMATED_PLAYER_ENTITY = ENTITY_TYPE_REGISTER.register("animated_player_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("animated_player_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> ANIMATED_PLAYER_ENTITY_SLIM = ENTITY_TYPE_REGISTER.register("animated_player_entity_slim",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("animated_player_entity_slim")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> GHOST_ENTITY = ENTITY_TYPE_REGISTER.register("ghost_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("ghost_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> AURA_ENTITY = ENTITY_TYPE_REGISTER.register("aura_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("aura_entity")
	);

	//body transformation ones

	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_one_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_one_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_two_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_two_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_three_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_three_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_four_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_four_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_five_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_five_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_six_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_six_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_seven_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_seven_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_eight_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_eight_tail_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("kitsune_nine_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("kitsune_nine_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_one_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_one_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_two_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_two_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_three_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_three_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_four_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_four_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_five_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_five_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_six_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_six_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_seven_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_seven_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_eight_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_eight_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> LIGHT_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("light_kitsune_nine_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("light_kitsune_nine_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_ONE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_one_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_one_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_TWO_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_two_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_two_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_THREE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_three_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_three_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_FOUR_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_four_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_four_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_FIVE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_five_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_five_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_SIX_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_six_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_six_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_SEVEN_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_seven_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_seven_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_EIGHT_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_eight_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_eight_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> SPATIAL_KITSUNE_NINE_TAIL_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("spatial_kitsune_nine_tail_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("spatial_kitsune_nine_tail_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> HALF_DRAGON_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("half_dragon_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("half_dragon_body_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("half_dragon_body_armed_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("half_dragon_body_armed_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("half_dragon_body_horned_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("half_dragon_body_horned_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> AZURE_HALF_DRAGON_BODY_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("azure_half_dragon_body_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("azure_half_dragon_body_transformation_entity")
	);
	public static final RegistryObject<EntityType<AbstractClientPlayer>> AZURE_HALF_DRAGON_BODY_ARMED_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("azure_half_dragon_body_armed_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("azure_half_dragon_body_armed_transformation_entity")
	);

	public static final RegistryObject<EntityType<AbstractClientPlayer>> AZURE_HALF_DRAGON_BODY_HORNED_TRANSFORMATION_ENTITY = ENTITY_TYPE_REGISTER.register("azure_half_dragon_body_horned_transformation_entity",
			() -> EntityType.Builder.<AbstractClientPlayer>createNothing(MobCategory.MISC).build("azure_half_dragon_body_horned_transformation_entity")
	);

	//actual entities

	public static final RegistryObject<EntityType<ThrowSkill>> THROW_SKILL_TYPE = ENTITY_TYPE_REGISTER.register("throw_skill",
			() -> EntityType.Builder.<ThrowSkill>of(ThrowSkill::new, MobCategory.MISC).sized(0.6f, 0.6f).build("throw_skill")
	);

	public static final RegistryObject<EntityType<Snake>> SNAKE_ENTITY_TYPE = ENTITY_TYPE_REGISTER.register("snake",
			() -> EntityType.Builder.of(Snake::new, MobCategory.MONSTER).sized(0.8f, 1.7f).build("snake")
	);

	public static final RegistryObject<EntityType<Desert_Snake>> DESERT_SNAKE_ENTITY_TYPE = ENTITY_TYPE_REGISTER.register("desert_snake",
			() -> EntityType.Builder.of(Desert_Snake::new, MobCategory.MONSTER).sized(0.8f, 1.7f).build("desert_snake")
	);	
	
	public static final RegistryObject<EntityType<Red_Snake>> RED_SNAKE_ENTITY_TYPE = ENTITY_TYPE_REGISTER.register("red_snake",
			() -> EntityType.Builder.of(Red_Snake::new, MobCategory.MONSTER).sized(0.8f, 1.7f).build("red_snake")
	);	
	
	public static final RegistryObject<EntityType<White_Snake>> WHITE_SNAKE_ENTITY_TYPE = ENTITY_TYPE_REGISTER.register("white_snake",
			() -> EntityType.Builder.of(White_Snake::new, MobCategory.MONSTER).sized(0.8f, 1.7f).build("white_snake")
	);

}
