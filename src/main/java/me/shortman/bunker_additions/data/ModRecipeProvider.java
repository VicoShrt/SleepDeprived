package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.data.recipe.CraftingRecipes;
import me.shortman.bunker_additions.data.recipe.builder.ManualCombiningRecipeBuilder;
import me.shortman.bunker_additions.integration.FarmersDelightIntegration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        FarmersDelightIntegration.crafting(recipeOutput);
        CraftingRecipes.register(recipeOutput);


    }
}
