package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.drug.Drugs;
import me.shortman.bunker_additions.common.item.custom.PillsItem;
import me.shortman.bunker_additions.common.item.custom.DrinkableItem;
import me.shortman.bunker_additions.common.item.properties.ModFoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BunkerAdditions.MOD_ID);

    public static final DeferredItem<Item> PILLS = ITEMS.register("pills",
            () -> new PillsItem(new Item.Properties().stacksTo(16)));

    /**
     *  DRUGS
     * **/
    // Hemp / Weed
    public static final DeferredItem<Item> HEMP_SEEDS = ITEMS.register("hemp_seeds",
            () -> new ItemNameBlockItem(ModBlocks.HEMP_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> HEMP_STRING = ITEMS.register("hemp_string",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_WEED_BUD = ITEMS.register("raw_weed_bud",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WET_WEED_BUD = ITEMS.register("wet_weed_bud",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WEED_BUD = ITEMS.register("weed_bud",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WEED_BOWL = ITEMS.register("weed_bowl",
            () -> new Item(new Item.Properties()));

    // Tobacco
    public static final DeferredItem<Item> TOBACCO_SEEDS = ITEMS.register("tobacco_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOBACCO_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> TOBACCO_LEAF = ITEMS.register("tobacco_leaf",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOBACCO_DRIED_LEAF = ITEMS.register("tobacco_dried_leaf",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TOBACCO_BOWL = ITEMS.register("tobacco_bowl",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CIGAR = ITEMS.register("cigar",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JOINT = ITEMS.register("joint",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLUNT = ITEMS.register("blunt",
            () -> new Item(new Item.Properties()));

    // Beer
    public static final DeferredItem<Item> BEER_BOTTLE = ITEMS.register("beer_bottle",
            () -> new DrinkableItem(new Item.Properties()
                    .food(ModFoodProperties.BEER)
                    .component(ModDataComponents.STRENGTH, Drugs.Alcohol.getStrength(Drugs.Alcohol.TYPE.BEER))
                    .stacksTo(1), Drugs.Alcohol.TYPE.BEER));

    // Wine
    public static final DeferredItem<Item> WINE_BOTTLE = ITEMS.register("wine_bottle",
            () -> new DrinkableItem(new Item.Properties()
                    .food(ModFoodProperties.WINE)
                    .component(ModDataComponents.STRENGTH, Drugs.Alcohol.getStrength(Drugs.Alcohol.TYPE.WINE))
                    .stacksTo(1), Drugs.Alcohol.TYPE.WINE));

    // Booze

    /**
     * TOOLS
     **/
    public static final DeferredItem<AxeItem> FIREFIGHTER_AXE = ITEMS.register("firefighter_axe",
            () -> new AxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 7, -3.2f))));
    public static final DeferredItem<Item> SCREWDRIVER = ITEMS.register("screwdriver",
            () -> new Item(new Item.Properties()
                    .durability(500)));
    public static final DeferredItem<Item> CAN_OPENER = ITEMS.register("can_opener",
            () -> new Item(new Item.Properties()
                    .durability(500)));


    /**
     * STUFF
     **/
    // Music
    public static final DeferredItem<Item> RHASTAFARIAN_MUSIC_DISC = ITEMS.register("rhastafarian_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.RHASTAFARIAN_KEY).stacksTo(1)));
    public static final DeferredItem<Item> CAN_RED = ITEMS.register("can_red",
            () -> new Item(new Item.Properties()));



    // Register Method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
