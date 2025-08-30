package me.shortman.sleep_deprived.item;

import me.shortman.sleep_deprived.effect.ModEffects;
import me.shortman.sleep_deprived.lib.Constants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final int MINUTE = Constants.TICKS_PER_MINUTE;
    public static final int ENERGY_AWAKE_DURATION = 5 * MINUTE;

    public static final FoodProperties ENERGY_DRINK = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.5f)
            .alwaysEdible()
            .effect(() -> new MobEffectInstance(ModEffects.AWAKE_EFFECT, ENERGY_AWAKE_DURATION, 0, false, false), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400,         0, false, false), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200,              0, false, false), 0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 400,                   0, false, false), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200,           0, false, false), 0.1f)
            .build();
}
