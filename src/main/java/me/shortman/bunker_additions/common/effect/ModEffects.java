package me.shortman.bunker_additions.common.effect;

import me.shortman.bunker_additions.BunkerAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, BunkerAdditions.MOD_ID);


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
