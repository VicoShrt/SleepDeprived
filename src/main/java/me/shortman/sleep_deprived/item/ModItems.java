package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.item.custom.BarometerItem;
import me.shortman.sleep_deprived.item.custom.LatexCutterItem;
import me.shortman.sleep_deprived.item.custom.ModArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SleepDeprived.MOD_ID);

    // Materials
    public static final DeferredItem<Item> LATEX = ITEMS.register("latex",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RUBBER = ITEMS.register("rubber",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BAUXITE = ITEMS.register("raw_bauxite",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ALUMINIUM = ITEMS.register("raw_aluminium",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot",
            () -> new Item(new Item.Properties()));

    // Tools
    public static final DeferredItem<Item> LATEX_CUTTER = ITEMS.register("latex_cutter",
            () -> new LatexCutterItem(new Item.Properties().durability(128)));

    public static final DeferredItem<Item> BAROMETER = ITEMS.register("barometer",
            () -> new BarometerItem(new Item.Properties()));

    // Water Suite
    public static final DeferredItem<ArmorItem> BREATHER = ITEMS.register("breather",
            () -> new ArmorItem(ModArmorMaterials.SWIMSUIT_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final DeferredItem<ArmorItem> AIR_TANK = ITEMS.register("air_tank",
            () -> new ModArmorItem(ModArmorMaterials.SWIMSUIT_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<ArmorItem> SWIM_LEGGINGS = ITEMS.register("swim_leggings",
            () -> new ArmorItem(ModArmorMaterials.SWIMSUIT_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final DeferredItem<ArmorItem> SWIM_FINS = ITEMS.register("swim_fins",
            () -> new ArmorItem(ModArmorMaterials.SWIMSUIT_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));


    // Register Method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
