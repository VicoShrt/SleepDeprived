package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.crafting.DryingRecipe;
import me.shortman.bunker_additions.common.crafting.ManualCombiningRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BunkerAdditions.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, BunkerAdditions.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ManualCombiningRecipe>> MANUAL_COMBINING_SERIALIZER =
            SERIALIZERS.register("manual_combining", ManualCombiningRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ManualCombiningRecipe>> MANUAL_COMBINING_TYPE =
            TYPES.register("manual_combining", () -> new RecipeType<ManualCombiningRecipe>() {
                @Override
                public String toString() {
                    return "manual_combining";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DryingRecipe>> DRYING_SERIALIZER =
            SERIALIZERS.register("drying", DryingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<DryingRecipe>> DRYING_TYPE =
            TYPES.register("drying", () -> new RecipeType<DryingRecipe>() {
                @Override
                public String toString() {
                    return "drying";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
