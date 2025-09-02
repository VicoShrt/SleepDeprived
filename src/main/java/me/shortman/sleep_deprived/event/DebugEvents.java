package me.shortman.sleep_deprived.event;

import me.shortman.sleep_deprived.Config;
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
        CompoundTag data = player.getPersistentData();

        ItemStack stack = event.getItemStack();
        if (Config.DEBUG.get() && stack.is(Items.STICK)) {
            player.sendSystemMessage(Component.literal("Config:\nDEBUG: true"));
            player.sendSystemMessage(Component.literal("AWAKE_AFTER_SLEEP: " + Config.AWAKE_AFTER_SLEEP.get()));
            player.sendSystemMessage(Component.literal("FATIGUE_THRESHOLD: " + Config.FATIGUE_THRESHOLD.get()));
            player.sendSystemMessage(Component.literal("MINUTES_TO_NEXT_STAGE: " + Config.MINUTES_TO_NEXT_STAGE.get()));
        }
    }
}
