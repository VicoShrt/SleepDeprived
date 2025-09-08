package me.shortman.bunker_additions.integration;

import me.shortman.bunker_additions.common.registry.ModItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

public class FarmersDelightIntegration {
    public static boolean IS_LOADED = false;
    public static void register(IEventBus modBus) {
        if (ModList.get().isLoaded("farmersdelight")) {
            IS_LOADED = true;
        }
    }

    public static void crafting(RecipeOutput recipeOutput) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.RAW_WEED_BUD), Ingredient.of(Items.SHEARS), ModItems.WEED_BUD)
                .addResult(ModItems.HEMP_STRING)
                .addResultWithChance(ModItems.HEMP_STRING, 0.5f)
                .build(recipeOutput);
    }
}
