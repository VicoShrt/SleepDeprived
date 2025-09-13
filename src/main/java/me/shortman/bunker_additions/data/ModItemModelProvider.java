package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BunkerAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.PILLS.get());

        // Hemp
        basicItem(ModItems.HEMP_SEEDS.get());
        basicItem(ModItems.HEMP_STRING.get());
        basicItem(ModItems.RAW_WEED_BUD.get());
        basicItem(ModItems.WEED_BUD.get());
        basicItem(ModItems.WET_WEED_BUD.get());
        basicItem(ModItems.WEED_BOWL.get());
        // Tobacco
        basicItem(ModItems.TOBACCO_SEEDS.get());
        basicItem(ModItems.TOBACCO_LEAF.get());
        basicItem(ModItems.TOBACCO_DRIED_LEAF.get());
        basicItem(ModItems.TOBACCO_BOWL.get());

        // Beer
        basicItem(ModItems.BEER_BOTTLE.get());
        // Wine
        basicItem(ModItems.WINE_BOTTLE.get());
        // Booze

        // Smoking
        basicItem(ModItems.CIGAR.get());
        basicItem(ModItems.CIGARETTE.get());
        basicItem(ModItems.JOINT.get());
        basicItem(ModItems.BLUNT.get());
        // Music Discs
        basicItem(ModItems.RHASTAFARIAN_MUSIC_DISC.get());
        basicItem(ModItems.CAN_RED.get());

        // Tools
        handheldItem(ModItems.FIREFIGHTER_AXE.get());
        handheldItem(ModItems.SCREWDRIVER.get());
        handheldItem(ModItems.CAN_OPENER.get());
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID,"item/" + item.getId().getPath()));
    }
}
