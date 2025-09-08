package me.shortman.bunker_additions.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForgeConfig;

import java.util.List;

public class Configuration {
    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    // COMMON
    public static final String CATEGORY_SETTINGS = "settings";
    public static ModConfigSpec.BooleanValue ENABLE_DEBUG;


    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        COMMON_BUILDER.comment("Game Settings").push(CATEGORY_SETTINGS);
        ENABLE_DEBUG = COMMON_BUILDER.comment("Enable debug")
                .define("debug", false);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();

    }

}
