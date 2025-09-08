package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.entity.DryingTableBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BunkerAdditions.MOD_ID);

    public static final Supplier<BlockEntityType<DryingTableBlockEntity>> DRYING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("drying_table_block_entity", () -> BlockEntityType.Builder.of(
                    DryingTableBlockEntity::new, ModBlocks.DRYING_TABLE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
