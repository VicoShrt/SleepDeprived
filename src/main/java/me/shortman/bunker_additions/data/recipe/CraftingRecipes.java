package me.shortman.bunker_additions.data.recipe;

import me.shortman.bunker_additions.common.registry.ModItems;
import me.shortman.bunker_additions.common.registry.ModSounds;
import me.shortman.bunker_additions.data.recipe.builder.DryingRecipeBuilder;
import me.shortman.bunker_additions.data.recipe.builder.ManualCombiningRecipeBuilder;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.w3c.dom.events.Event;

public class CraftingRecipes {
    public static void register(RecipeOutput recipeOutput) {
        manualCombining(recipeOutput);
        drying(recipeOutput);
    }

    protected static void manualCombining(RecipeOutput recipeOutput) {
        ManualCombiningRecipeBuilder.combining(ModItems.WET_WEED_BUD.get(), 1)
                .mainhand(Ingredient.of(ModItems.RAW_WEED_BUD))
                .offhand(Ingredient.of(Items.SHEARS))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.MOOSHROOM_SHEAR)
                .ticksToCraft(20)
                .unlockedBy("has_wet_weed_bud", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WET_WEED_BUD.get()))
                .save(recipeOutput);
        ManualCombiningRecipeBuilder.combining(ModItems.WEED_BOWL.get(), 1)
                .mainhand(Ingredient.of(ModItems.WEED_BUD))
                .offhand(Ingredient.of(Items.BOWL))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_weed_bud", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WEED_BUD.get()))
                .save(recipeOutput);
        ManualCombiningRecipeBuilder.combining(Ingredient.of(ModItems.BLUNT, Items.BOWL))
                .mainhand(Ingredient.of(ModItems.WEED_BOWL))
                .offhand(Ingredient.of(ModItems.TOBACCO_DRIED_LEAF))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_weed_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WEED_BOWL.get()))
                .save(recipeOutput);
        ManualCombiningRecipeBuilder.combining(Ingredient.of(ModItems.JOINT, Items.BOWL))
                .mainhand(Ingredient.of(ModItems.WEED_BOWL))
                .offhand(Ingredient.of(Items.PAPER))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_weed_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WEED_BOWL.get()))
                .save(recipeOutput);

        ManualCombiningRecipeBuilder.combining(ModItems.TOBACCO_BOWL.get(), 1)
                .mainhand(Ingredient.of(ModItems.TOBACCO_DRIED_LEAF))
                .offhand(Ingredient.of(Items.BOWL))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_tobacco_dried_leaf", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TOBACCO_DRIED_LEAF.get()))
                .save(recipeOutput);
        ManualCombiningRecipeBuilder.combining(Ingredient.of(ModItems.CIGAR, Items.BOWL))
                .mainhand(Ingredient.of(ModItems.TOBACCO_BOWL))
                .offhand(Ingredient.of(ModItems.TOBACCO_DRIED_LEAF))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_tobacco_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TOBACCO_BOWL.get()))
                .save(recipeOutput);
        ManualCombiningRecipeBuilder.combining(Ingredient.of(ModItems.CIGARETTE, Items.BOWL))
                .mainhand(Ingredient.of(ModItems.TOBACCO_BOWL))
                .offhand(Ingredient.of(Items.PAPER))
                .doesHandMatter(false)
                .soundEvent(SoundEvents.ITEM_PICKUP)
                .ticksToCraft(40)
                .unlockedBy("has_tobacco_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TOBACCO_BOWL.get()))
                .save(recipeOutput);


        ManualCombiningRecipeBuilder.combining(Items.COOKED_SALMON, 4)
                .mainhand(Ingredient.of(ModItems.CAN_OPENER))
                .offhand(Ingredient.of(ModItems.CAN_RED))
                .doesHandMatter(false)
                .soundEvent(ModSounds.CAN_OPEN.get())
                .ticksToCraft(50)
                .unlockedBy("has_can_opener", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CAN_OPENER.get()))
                .save(recipeOutput);
    }

    protected static void drying(RecipeOutput recipeOutput) {
        DryingRecipeBuilder.combining(ModItems.TOBACCO_DRIED_LEAF.get())
                .input(Ingredient.of(ModItems.TOBACCO_LEAF))
                .ticksToDry(100)
                .unlockedBy("has_tobacco_leaf", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TOBACCO_LEAF))
                .save(recipeOutput);

        DryingRecipeBuilder.combining(ModItems.WEED_BUD.get())
                .input(Ingredient.of(ModItems.WET_WEED_BUD))
                .ticksToDry(200)
                .unlockedBy("has_wet_weed_bud", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WET_WEED_BUD))
                .save(recipeOutput);
    }
    
}
