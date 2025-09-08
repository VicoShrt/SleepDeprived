package me.shortman.bunker_additions.data.recipe;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.io.IOException;
import java.util.List;

import static me.shortman.bunker_additions.lib.TextHelper.getHasName;

public class CraftingRecipes {
    public static void register(RecipeOutput recipeOutput) {
        //recipesVanillaAlternatives(recipeOutput);
        damageRecipes(recipeOutput);
    }

    protected static void recipesVanillaAlternatives(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HEMP_STRING, 1)
                .requires(ModItems.RAW_WEED_BUD)
                .requires(Tags.Items.TOOLS_SHEAR)
                .unlockedBy("has_raw_weed_bud", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_WEED_BUD)).save(recipeOutput);

    }
    protected static void damageRecipes(RecipeOutput recipeOutput) {


    }
    
}
