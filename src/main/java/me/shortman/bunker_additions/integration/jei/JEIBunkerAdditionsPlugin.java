package me.shortman.bunker_additions.integration.jei;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.client.screen.DryingTableScreen;
import me.shortman.bunker_additions.common.crafting.DryingRecipe;
import me.shortman.bunker_additions.common.crafting.ManualCombiningRecipe;
import me.shortman.bunker_additions.common.registry.ModBlocks;
import me.shortman.bunker_additions.common.registry.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIBunkerAdditionsPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return BunkerAdditions.resource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DryingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));
        registration.addRecipeCategories(new ManualCombiningRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DryingRecipe> dryingRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.DRYING_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(DryingRecipeCategory.DRYING_RECIPE_TYPE, dryingRecipes);

        List<ManualCombiningRecipe> manualCombiningRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.MANUAL_COMBINING_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(ManualCombiningRecipeCategory.MANUAL_COMBINING_RECIPE_TYPE, manualCombiningRecipes);

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DryingTableScreen.class, DryingTableScreen.X_PROGRESS_1, DryingTableScreen.Y_PROGRESS_1, 22, 20,
                DryingRecipeCategory.DRYING_RECIPE_TYPE);
        registration.addRecipeClickArea(DryingTableScreen.class, DryingTableScreen.X_PROGRESS_2, DryingTableScreen.Y_PROGRESS_2, 22, 20,
                DryingRecipeCategory.DRYING_RECIPE_TYPE);
        registration.addRecipeClickArea(DryingTableScreen.class, DryingTableScreen.X_PROGRESS_3, DryingTableScreen.Y_PROGRESS_3, 22, 20,
                DryingRecipeCategory.DRYING_RECIPE_TYPE);
        registration.addRecipeClickArea(DryingTableScreen.class, DryingTableScreen.X_PROGRESS_4, DryingTableScreen.Y_PROGRESS_4, 22, 20,
                DryingRecipeCategory.DRYING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_TABLE),
                DryingRecipeCategory.DRYING_RECIPE_TYPE);
    }
}
