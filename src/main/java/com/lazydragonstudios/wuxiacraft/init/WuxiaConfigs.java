package com.lazydragonstudios.wuxiacraft.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class WuxiaConfigs {

    //Server Config Values

    //Common Config Values
    public static ForgeConfigSpec.DoubleValue CULTIVATION_SPEED_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<String> REALM_LIMIT;


    //Client Config Values


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
        CULTIVATION_SPEED_MULTIPLIER = COMMON_BUILDER
                .comment("The multiplier for the cultivation speed.")
                .defineInRange("cultivationSpeedMultiplier", 1.0d, 0, Double.MAX_VALUE);
        REALM_LIMIT = COMMON_BUILDER
                .comment("Limit Cultivation Realms. essence_golden_core_stage/essence_core_expansion_stage")
                .define("realmlimit", "essence_golden_core_stage");

        COMMON_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

    private static void registerClientConfig() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
