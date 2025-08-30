package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.item.custom.EnergyDrinkItem;
import me.shortman.sleep_deprived.item.custom.SleepingPillsItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SleepDeprived.MOD_ID);

    public static final DeferredItem<Item> ENERGY_DRINK = ITEMS.register("energy_drink",
            () -> new EnergyDrinkItem(new Item.Properties().food(ModFoodProperties.ENERGY_DRINK)));

    public static final DeferredItem<Item> SLEEPING_PILLS = ITEMS.register("sleeping_pills",
            () -> new SleepingPillsItem(new Item.Properties().durability(3)));

    // Register Method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
