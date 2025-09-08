package me.shortman.bunker_additions.common.item.properties;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoodProperties {

    public static final FoodProperties PILLS = new FoodProperties.Builder()
            .nutrition(0)
            .saturationModifier(0f)
            .alwaysEdible()
            .fast()
            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 20,         0, false, false), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400,         0, false, false), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200,              0, false, false), 0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 400,                   0, false, false), 0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200,           0, false, false), 0.1f)
            .build();

    public static final FoodProperties BEER = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.15f)
            .alwaysEdible()
            .fast()
            .usingConvertsTo(Items.GLASS_BOTTLE)
            .build();

    public static final FoodProperties WINE = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.25f)
            .alwaysEdible()
            .fast()
            .usingConvertsTo(Items.GLASS_BOTTLE)
            .build();
}
