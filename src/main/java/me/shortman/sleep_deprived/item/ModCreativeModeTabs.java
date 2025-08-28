package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SleepDeprived.MOD_ID);

    // Creative Tabs registry
    public static final Supplier<CreativeModeTab> DIVERS_DREAMS = CREATIVE_MODE_TAB.register("divers_dreams_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BAROMETER.get()))
                    .title(Component.translatable("creativetab.diversdreams"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.BAUXITE_ORE);
                        output.accept(ModBlocks.BAUXITE_DEEPSLATE_ORE);
                        output.accept(ModItems.RAW_BAUXITE);
                        output.accept(ModItems.ALUMINIUM);
                        output.accept(ModItems.ALUMINIUM_INGOT);
                        output.accept(ModItems.LATEX_CUTTER);
                        output.accept(ModItems.BAROMETER);
                        output.accept(ModItems.RUBBER);
                        output.accept(ModItems.LATEX);
                        output.accept(ModBlocks.RUBBER_BLOCK);
                        output.accept(ModItems.RUBBER);
                        output.accept(ModItems.BREATHER);
                        output.accept(ModItems.AIR_TANK);
                        output.accept(ModItems.SWIM_LEGGINGS);
                        output.accept(ModItems.SWIM_FINS);
                    })
                    .build());

    // Register Method
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
