package me.shortman.sleep_deprived.event;

import me.shortman.sleep_deprived.SleepDeprived;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Dictionary;
import java.util.List;


@EventBusSubscriber(modid = SleepDeprived.MOD_ID)
public class ModEvents {
    public static final String TAG_NO_SLEEP_TICKS = "NoSleepTicks";
    public static final String TAG_SLEEP_TICKS = "SleepTicks";

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
}
