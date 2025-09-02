package me.shortman.sleep_deprived.event;

import me.shortman.sleep_deprived.lib.Constants;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

public class TooltipEvents {

    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(Items.CLOCK)) {
            event.getToolTip().add(Component.translatable("tooltip.sleep_deprived.clock").withColor(Constants.COLOR_LIGHT_GRAY));
        }
    }
}
