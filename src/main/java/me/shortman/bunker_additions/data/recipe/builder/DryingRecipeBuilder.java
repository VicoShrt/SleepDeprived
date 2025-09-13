package me.shortman.bunker_additions.data.recipe.builder;

import me.shortman.bunker_additions.common.crafting.DryingRecipe;
import me.shortman.bunker_additions.common.crafting.ManualCombiningRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DryingRecipeBuilder implements RecipeBuilder {
    private final ItemStack result;
    private Ingredient input = Ingredient.EMPTY;
    private int ticksToDry = 20;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public DryingRecipeBuilder(ItemStack result) {
        this.result = result;
    }


    public static DryingRecipeBuilder combining(ItemStack result) {
        return new DryingRecipeBuilder(result);
    }

    public static DryingRecipeBuilder combining(Item result) {
        return combining(new ItemStack(result));
    }

    public static DryingRecipeBuilder combining(Item result, int count) {
        return combining(new ItemStack(result, count));
    }


    public DryingRecipeBuilder input(Ingredient ingredient) {
        this.input = ingredient;
        return this;
    }


    public DryingRecipeBuilder ticksToDry(int ticks) {
        this.ticksToDry = ticks;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        // Group is not used in this recipe type, but we implement it for interface compliance
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);

        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        DryingRecipe recipe = new DryingRecipe(
                this.input,
                this.result,
                this.ticksToDry
        );

        output.accept(id, recipe, advancementBuilder.build(id.withSuffix("_recipe")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
        if (this.input.hasNoItems()) {
            throw new IllegalStateException("Input has not items for recipe " + id);
        }
    }
}