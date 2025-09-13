package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BunkerAdditions.MOD_ID);

    // Creative Tabs registry
    public static final Supplier<CreativeModeTab> NATURE_TAB = CREATIVE_MODE_TAB.register("bunker_additions_nature_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.TOBACCO_LEAF.get()))
                    .title(Component.translatable("creativetab.bunker_additions.nature"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.HEMP_SEEDS.get());
                        output.accept(ModItems.HEMP_STRING.get());
                        output.accept(ModItems.RAW_WEED_BUD.get());
                        output.accept(ModItems.WET_WEED_BUD.get());
                        output.accept(ModItems.WEED_BUD.get());
                        output.accept(ModItems.WEED_BOWL.get());

                        output.accept(ModItems.TOBACCO_SEEDS.get());
                        output.accept(ModItems.TOBACCO_LEAF.get());
                        output.accept(ModItems.TOBACCO_DRIED_LEAF.get());
                        output.accept(ModItems.TOBACCO_BOWL.get());
                    })
                    .build());

    public static final Supplier<CreativeModeTab> CONSUMABLES_TAB = CREATIVE_MODE_TAB.register("bunker_additions_consumables_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.PILLS.get()))
                    .title(Component.translatable("creativetab.bunker_additions.consumables"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PILLS.get());

                        output.accept(ModItems.BEER_BOTTLE.get());
                        output.accept(ModItems.WINE_BOTTLE.get());

                        output.accept(ModItems.CIGAR.get());
                        output.accept(ModItems.CIGARETTE.get());
                        output.accept(ModItems.BLUNT.get());
                        output.accept(ModItems.JOINT.get());

                    })
                    .build());

    public static final Supplier<CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TAB.register("bunker_additions_tools_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SCREWDRIVER.get()))
                    .title(Component.translatable("creativetab.bunker_additions.tools"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.DRYING_TABLE.get());

                        output.accept(ModItems.FIREFIGHTER_AXE.get());

                        output.accept(ModItems.SCREWDRIVER.get());
                        output.accept(ModItems.CAN_OPENER.get());
                    })
                    .build());

    public static final Supplier<CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TAB.register("bunker_additions_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.RHASTAFARIAN_MUSIC_DISC.get()))
                    .title(Component.translatable("creativetab.bunker_additions.items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RHASTAFARIAN_MUSIC_DISC.get());
                        output.accept(ModItems.CAN_RED.get());
                    })
                    .build());

    // Register Method
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
