package me.shortman.sleep_deprived.event;

import me.shortman.sleep_deprived.SleepDeprived;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;


@EventBusSubscriber(modid = SleepDeprived.MOD_ID)
public class ModEvents {
    public static final String TAG_FATIGUE_LEVEL = "FatigueLevel";
    public static final String TAG_TICKS_SINCE_LAST_SLEPT = "TicksSinceLastSlept";
    public static final String TAG_SLEEP_TICKS = "SleepTicks";
    public static final String TAG_SLEEP_DEPRIVED_STAGE = "SleepDeprivedStage";

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        SleepEvents.onLevelTick(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        SleepEvents.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void onPlayerClicksBed(PlayerInteractEvent.RightClickBlock event) {
        SleepEvents.onPlayerClicksBed(event);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        TooltipEvents.onItemTooltip(event);
    }


}
