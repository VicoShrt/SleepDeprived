package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import me.shortman.bunker_additions.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                       CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BunkerAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.AXES)
                .add(ModItems.FIREFIGHTER_AXE.get());

        tag(ModTags.Items.BEER_CONSUMABLE)
                .add(ModItems.BEER_BOTTLE.get());

        tag(ModTags.Items.WINE_CONSUMABLE)
                .add(ModItems.WINE_BOTTLE.get());

    }
}
