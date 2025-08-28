package me.shortman.sleep_deprived.datagen;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.block.ModBlocks;
import me.shortman.sleep_deprived.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> BAUXITE_SMELTABLES = List.of(ModItems.RAW_BAUXITE, ModBlocks.BAUXITE_ORE, ModBlocks.BAUXITE_DEEPSLATE_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBBER_BLOCK.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', ModItems.RUBBER.get())
                .unlockedBy("has_rubber", has(ModItems.RUBBER))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LATEX_CUTTER.get())
                .pattern(" A")
                .pattern("S ")
                .define('A', ModItems.ALUMINIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy("has_aluminium", has(ModItems.ALUMINIUM))
                .save(recipeOutput);

        // swimsuit crafting
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BREATHER.get())
                .pattern("RRR")
                .pattern("A A")
                .define('R', ModItems.RUBBER.get())
                .define('A', ModItems.ALUMINIUM_INGOT.get())
                .unlockedBy("has_rubber", has(ModItems.RUBBER))
                .unlockedBy("has_aluminium_ingot", has(ModItems.ALUMINIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AIR_TANK.get())
                .pattern(" R ")
                .pattern("A A")
                .pattern("A A")
                .define('R', ModItems.RUBBER.get())
                .define('A', ModItems.ALUMINIUM_INGOT.get())
                .unlockedBy("has_rubber", has(ModItems.RUBBER))
                .unlockedBy("has_aluminium_ingot", has(ModItems.ALUMINIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SWIM_LEGGINGS.get())
                .pattern("RAR")
                .pattern("R R")
                .pattern("A A")
                .define('R', ModItems.RUBBER.get())
                .define('A', ModItems.ALUMINIUM_INGOT.get())
                .unlockedBy("has_rubber", has(ModItems.RUBBER))
                .unlockedBy("has_aluminium_ingot", has(ModItems.ALUMINIUM_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SWIM_FINS.get())
                .pattern("RAR")
                .pattern("R R")
                .pattern("R R")
                .define('R', ModItems.RUBBER.get())
                .define('A', ModItems.ALUMINIUM_INGOT.get())
                .unlockedBy("has_rubber", has(ModItems.RUBBER))
                .unlockedBy("has_aluminium_ingot", has(ModItems.ALUMINIUM_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBBER.get(), 9)
                .requires(ModBlocks.RUBBER_BLOCK)
                .unlockedBy("has_rubber_block", has(ModBlocks.RUBBER_BLOCK))
                .save(recipeOutput);

        oreSmelting(recipeOutput, BAUXITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALUMINIUM.get(), 0.25f, 200, "aluminium");
        oreBlasting(recipeOutput, BAUXITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALUMINIUM.get(), 0.25f, 100, "aluminium");

        oreSmelting(recipeOutput, List.of(ModItems.ALUMINIUM.get()), RecipeCategory.MISC, ModItems.ALUMINIUM_INGOT.get(), 0.25f, 200, "aluminium");
        oreBlasting(recipeOutput, List.of(ModItems.ALUMINIUM.get()), RecipeCategory.MISC, ModItems.ALUMINIUM_INGOT.get(), 0.25f, 100, "aluminium");

    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, SleepDeprived.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
