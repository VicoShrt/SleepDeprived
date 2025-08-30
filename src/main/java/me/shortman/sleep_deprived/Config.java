package me.shortman.sleep_deprived;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Enable Debug mode.")
            .define("debug", false);

    public static final ModConfigSpec.IntValue MINUTES_UNTIL_SLEEP_DEPRIVED = BUILDER
            .comment("Minutes until sleep deprived effect starts.")
            .defineInRange("minutesUntilSleepDeprived", 40, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue MINUTES_TO_NEXT_STAGE = BUILDER
            .comment("Minutes until the next sleep deprived stage starts.")
            .defineInRange("minutesToNextStage", 20, 0, Integer.MAX_VALUE);


    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
