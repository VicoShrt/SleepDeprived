package me.shortman.bunker_additions.integration;

import dev.ghen.thirst.api.ThirstHelper;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

public class ThirstIntegration {
    private static boolean IS_LOADED = false;
    public static void register(IEventBus modBus) {
        if (ModList.get().isLoaded("thirst")) {
            IS_LOADED = true;
        }
    }

    public static boolean isLoaded() {
        return IS_LOADED;
    }

    public static void registerThirstItems (RegisterThirstValueEvent event) {
        event.addDrink(ModItems.BEER_BOTTLE.get(), 4, 2);
        event.addDrink(ModItems.WINE_BOTTLE.get(), 3, 1);
    }
}
