package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block>
        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> WEED_CONSUMABLE = createTag("weed_consumable");
        public static final TagKey<Item> TOBACCO_CONSUMABLE = createTag("tobacco_consumable");
        public static final TagKey<Item> BEER_CONSUMABLE = createTag("beer_consumable");
        public static final TagKey<Item> WINE_CONSUMABLE = createTag("wine_consumable");
        public static final TagKey<Item> BOOZE_CONSUMABLE = createTag("booze_consumable");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID, name));
        }
    }
}
