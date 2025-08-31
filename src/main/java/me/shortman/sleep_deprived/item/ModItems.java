package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.block.ModBlocks;
import me.shortman.sleep_deprived.item.custom.EnergyDrinkItem;
import me.shortman.sleep_deprived.item.custom.SleepingPillsItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SleepDeprived.MOD_ID);

    public static final DeferredItem<Item> ENERGY_DRINK = ITEMS.register("energy_drink",
            () -> new EnergyDrinkItem(new Item.Properties().food(ModFoodProperties.ENERGY_DRINK)));

    public static final DeferredItem<Item> SLEEPING_PILLS = ITEMS.register("sleeping_pills",
            () -> new SleepingPillsItem(new Item.Properties().durability(3)));

    public static final DeferredItem<Item> COFFEE_BEANS = ITEMS.register("coffee_beans",
            () -> new ItemNameBlockItem(ModBlocks.COFFEE_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> COFFEE_SEEDS = ITEMS.register("coffee_seeds",
            () -> new ItemNameBlockItem(ModBlocks.COFFEE_CROP.get(), new Item.Properties().food(ModFoodProperties.COFFEE_BEANS)));

    // Register Method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
