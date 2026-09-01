package com.lazydragonstudios.wuxiacraft.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.stream.Collectors;

public class WuxiaConfigs {

    // Server Config Values

    // Common Config Values
    public static ForgeConfigSpec.BooleanValue STARTER_BOOK;
    public static ForgeConfigSpec.BooleanValue LIVES_ENABLED;
    public static ForgeConfigSpec.LongValue INITIAL_LIVES;
    public static ForgeConfigSpec.LongValue MAX_LIVES;
    public static ForgeConfigSpec.ConfigValue<String> AFK_SYSTEM;
    public static ForgeConfigSpec.LongValue MAX_CULTPOINTS;
    public static ForgeConfigSpec.DoubleValue CULTIVATION_SPEED_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_MULTIPLIER_LIST;
    // Client Config Values
    public static ForgeConfigSpec.BooleanValue HEALTH_BAR_ENABLED;

    public static void registerConfigs() {
        registerServerConfig();
        registerCommonConfig();
        registerClientConfig();
    }

    private static void registerServerConfig() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

    private static void registerCommonConfig() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Common Config for WuxiaCraft").push("cultivation");

        STARTER_BOOK = COMMON_BUILDER
                .comment("Whether the Starter Book is given on first login. Default(true)")
                .define("starterBook", true);

        LIVES_ENABLED = COMMON_BUILDER
                .comment("Whether the lives are enabled or not. Default(true)")
                .define("livesEnabled", true);

        INITIAL_LIVES = COMMON_BUILDER
                .comment("How many lives do players initially have. Default(3)")
                .defineInRange("initialLives", 3, 1, Long.MAX_VALUE);

        MAX_LIVES = COMMON_BUILDER
                .comment("The max lives a player will manage to obtain or have. Default(3)")
                .defineInRange("maxLives", 3, 1, Long.MAX_VALUE);

        CULTIVATION_SPEED_MULTIPLIER = COMMON_BUILDER
                .comment("The multiplier for the cultivation speed. Default(1)")
                .defineInRange("cultivationSpeedMultiplier", 1.0d, 0, Double.MAX_VALUE);

        AFK_SYSTEM = COMMON_BUILDER
                .comment("Afk System type. (disabled/enabled/detrimental/beneficial/beneficial+/enabled+). Default(disabled)")
                .define("afkSystem", "disabled");

        MAX_CULTPOINTS = COMMON_BUILDER
                .comment("The max cultpoints for the afk system. Default(20000)")
                .defineInRange("maxPoints", 20000, 1, Long.MAX_VALUE);

        DIMENSION_MULTIPLIER_LIST = COMMON_BUILDER
                .comment("per dimension cultivation multiplier speed. Format: namespace:dimension,multiplier (e.g., \"minecraft:the_end,1.25\", \"minecraft:the_nether,0.75\")")
                .defineListAllowEmpty("dimensionMultipliers", List.of(""), 
                    obj -> obj instanceof String && ((String) obj).matches("^[a-z0-9_:-]+,[0-9]*\\.?[0-9]+$"));

        COMMON_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

    private static void registerClientConfig() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client Config for WuxiaCraft").push("cultivation");
        
        HEALTH_BAR_ENABLED = CLIENT_BUILDER
                .comment("Whether the health bar is enabled or not. Default(true)")
                .define("healthBarEnabled", true);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    public static Map<ResourceKey<Level>, Double> getDimensionMultipliers() {
        return DIMENSION_MULTIPLIER_LIST.get().stream().map(str -> {
            try {
                String[] parts = str.split(",");
                ResourceLocation location = new ResourceLocation(parts[0]);
                ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, location);
                double multiplier = Double.parseDouble(parts[1]);
                return Pair.of(dimension, multiplier);
            } catch (Exception e) {
            return null;
            }
        }).filter(Objects::nonNull)
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
