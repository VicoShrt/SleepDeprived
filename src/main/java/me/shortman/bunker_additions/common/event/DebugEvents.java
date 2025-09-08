package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.common.Configuration;
import me.shortman.bunker_additions.common.registry.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class DebugEvents {
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        Player player = event.getEntity();

        ItemStack stack = event.getItemStack();
        if (Configuration.ENABLE_DEBUG.get() && stack.is(Items.STICK)) {
            double alcoholLevel = player.getAttribute(ModAttributes.ALCOHOL_LEVEL).getValue();
            double alcoholTolerance = player.getAttribute(ModAttributes.ALCOHOL_TOLERANCE_MULTIPLIER).getValue();
            double ticksSinceLast = player.getAttribute(ModAttributes.TICKS_SINCE_LAST_ALCOHOL).getValue();
            player.sendSystemMessage(Component.literal("Alc Level:                " + alcoholLevel));
            player.sendSystemMessage(Component.literal("Alc Tolerance Multiplier: " + alcoholTolerance));
            player.sendSystemMessage(Component.literal("Alc Ticks Since Last:     " + ticksSinceLast));
        }
    }
}
