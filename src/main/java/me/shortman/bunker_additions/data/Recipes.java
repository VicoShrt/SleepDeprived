package me.shortman.bunker_additions.data;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import me.shortman.bunker_additions.data.recipe.CraftingRecipes;
import me.shortman.bunker_additions.integration.FarmersDelightIntegration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Recipes extends RecipeProvider implements IConditionBuilder {
    public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        if (FarmersDelightIntegration.IS_LOADED) {
            FarmersDelightIntegration.crafting(recipeOutput);
        }
        CraftingRecipes.register(recipeOutput);

    }
}
