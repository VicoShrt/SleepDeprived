package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.common.registry.ModBlockEntities;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

public class RegisterCapabilitiesEvents {
    public static void registerAll(RegisterCapabilitiesEvent event) {
        registerEntities(event);
    }

    public static void registerEntities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.DRYING_TABLE_BLOCK_ENTITY.get(),
                (dryingBE, side) -> {
                    if (side == Direction.DOWN) {
                        return new RangedWrapper(dryingBE.inventory, 4, 8); // out slots 4..7 (maxExclusive)
                    } else {
                        return new RangedWrapper(dryingBE.inventory, 0, 4); // in slots 0..3
                    }
                }
        );
    }
}
