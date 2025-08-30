package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.item.custom.EnergyDrinkItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SleepDeprived.MOD_ID);

    public static final DeferredItem<Item> ENERGY_DRINK = ITEMS.register("energy_drink",
            () -> new EnergyDrinkItem(new Item.Properties().food(ModFoodProperties.ENERGY_DRINK)));


    // Register Method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
