package me.shortman.bunker_additions.lib.common;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SlotItemHandlerOutput extends SlotItemHandler {
    public SlotItemHandlerOutput(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        // Prevent placing items into this slot
        return false;
    }
}
