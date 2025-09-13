package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.DryingTableBlock;
import me.shortman.bunker_additions.common.block.HempCropBlock;
import me.shortman.bunker_additions.common.block.TobaccoCropBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BunkerAdditions.MOD_ID);

    // Block registry
    public static final DeferredBlock<Block> DRYING_TABLE = registerBlock("drying_table",
            () -> new DryingTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredBlock<Block> HEMP_CROP = BLOCKS.register("hemp_crop",
            () -> new HempCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> TOBACCO_CROP = BLOCKS.register("tobacco_crop",
            () -> new TobaccoCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    // Helpers
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Register Method
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
