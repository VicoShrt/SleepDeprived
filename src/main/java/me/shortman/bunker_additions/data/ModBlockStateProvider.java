package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.TobaccoCropBlock;
import me.shortman.bunker_additions.common.registry.ModBlocks;
import me.shortman.bunker_additions.common.block.HempCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BunkerAdditions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.DRYING_TABLE.get(), models().cubeBottomTop(
                "drying_table",
                modLoc("block/drying_table_side"),   // sides
                modLoc("block/drying_table_bottom"), // bottom
                modLoc("block/drying_table_top")     // top
        ));

        makeCrop(ModBlocks.HEMP_CROP.get(), "hemp_crop", HempCropBlock.AGE);
        makeCrop(ModBlocks.TOBACCO_CROP.get(), "tobacco_crop", TobaccoCropBlock.AGE);
    }

    protected void makeBushyBlock(Block block, String name) {
        ConfiguredModel model = new ConfiguredModel(models().cross(name,
                ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID, "block/" + name)).renderType("cutout"));
        getVariantBuilder(block).partialState().setModels(model);
    }


    public void makeCrop(Block block, String modelName, IntegerProperty age) {
        Function<BlockState, ConfiguredModel[]> function = state -> cropStates(state, modelName + "_stage", age);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] cropStates(BlockState state, String modelName, IntegerProperty age) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(age),
                ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID, "block/" + modelName + state.getValue(age))).renderType("cutout"));

        return models;
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(BunkerAdditions.MOD_ID + ":block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(BunkerAdditions.MOD_ID + ":block/" + deferredBlock.getId().getPath() + appendix));
    }

}
