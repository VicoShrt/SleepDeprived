package me.shortman.sleep_deprived.effect;

import me.shortman.sleep_deprived.SleepDeprived;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SleepDeprived.MOD_ID);

    public static final Holder<MobEffect> SLEEP_DEPRIVED_EFFECT = MOB_EFFECTS.register("sleep_deprived",
            () -> new SleepDeprivedEffect(MobEffectCategory.HARMFUL, 0x212121));

    public static final Holder<MobEffect> AWAKE_EFFECT = MOB_EFFECTS.register("awake",
            () -> new AwakeEffect(MobEffectCategory.BENEFICIAL, 0xcecece));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
