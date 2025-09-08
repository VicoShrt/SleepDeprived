package me.shortman.bunker_additions.lib;

import me.shortman.bunker_additions.BunkerAdditions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class TextHelper {
    private static final MutableComponent NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(BunkerAdditions.MOD_ID + "." + key, args);
    }

    public static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    public static String getHasName(ItemLike item) {
        return "has_" + item;
    }

}
