package me.shortman.sleep_deprived;

import me.shortman.sleep_deprived.block.ModBlocks;
import me.shortman.sleep_deprived.effect.ModEffects;
import me.shortman.sleep_deprived.item.ModCreativeModeTabs;
import me.shortman.sleep_deprived.item.ModItems;
import me.shortman.sleep_deprived.potion.ModPotions;
import me.shortman.sleep_deprived.sound.ModSounds;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.event.Level;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SleepDeprived.MOD_ID)
public class SleepDeprived {
    public static final String MOD_ID = "sleep_deprived";
    // Backup Config Setup
    public static boolean CFG_DEBUG = true;
    public static int CFG_FATIGUE_THRESHOLD = 40;
    public static int CFG_MINUTES_TO_NEXT_STAGE = 20;
    public static int CFG_AWAKE_AFTER_SLEEP = 50;

    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SleepDeprived(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModSounds.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    public Level getLogLevel() {
        if (CFG_DEBUG) {
            return Level.DEBUG;
        }
        return Level.INFO;
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.debug(MOD_ID + " server started...");
    }
}
