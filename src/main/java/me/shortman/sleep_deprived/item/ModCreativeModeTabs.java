package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SleepDeprived.MOD_ID);

    // Creative Tabs registry
    public static final Supplier<CreativeModeTab> SLEEP_DEPRIVED_TAB = CREATIVE_MODE_TAB.register("sleep_deprived_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ENERGY_DRINK.get()))
                    .title(Component.translatable("creativetab.sleep_deprived"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ENERGY_DRINK.get());
                    })
                    .build());

    // Register Method
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
