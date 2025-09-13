package me.shortman.bunker_additions.data.recipe.builder;

import me.shortman.bunker_additions.BunkerAdditions;
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

public class ManualCombiningRecipeBuilder implements RecipeBuilder {
    private final Ingredient result;
    private Ingredient mainhandItem = Ingredient.EMPTY;
    private Ingredient offhandItem = Ingredient.EMPTY;
    private boolean doesHandMatter = true;
    private int ticksToCraft = 20;
    private Optional<Holder<SoundEvent>> soundEvent;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ManualCombiningRecipeBuilder(Ingredient result) {
        this.result = result;
    }

    public static ManualCombiningRecipeBuilder combining(Ingredient result) {
        return new ManualCombiningRecipeBuilder(result);
    }

    public static ManualCombiningRecipeBuilder combining(Item result) {
        return combining(Ingredient.of(result));
    }

    public static ManualCombiningRecipeBuilder combining(Item result, int count) {
        return combining(Ingredient.of(new ItemStack(result, count)));
    }

    public ManualCombiningRecipeBuilder mainhand(Ingredient ingredient) {
        this.mainhandItem = ingredient;
        return this;
    }

    public ManualCombiningRecipeBuilder offhand(Ingredient ingredient) {
        this.offhandItem = ingredient;
        return this;
    }

    public ManualCombiningRecipeBuilder doesHandMatter(boolean doesHandMatter) {
        this.doesHandMatter = doesHandMatter;
        return this;
    }

    public ManualCombiningRecipeBuilder ticksToCraft(int ticks) {
        this.ticksToCraft = ticks;
        return this;
    }

    public ManualCombiningRecipeBuilder soundEvent(SoundEvent sound) {
        this.soundEvent = Optional.of(Holder.direct(sound));
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
        return this.result.getItems()[0].getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);

        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        ManualCombiningRecipe recipe = new ManualCombiningRecipe(
                this.mainhandItem,
                this.offhandItem,
                this.result,
                this.doesHandMatter,
                this.ticksToCraft,
                this.soundEvent
        );

        output.accept(id, recipe, advancementBuilder.build(id.withSuffix("_recipe")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
        if (this.mainhandItem.isEmpty()) {
            throw new IllegalStateException("Mainhand ingredient cannot be empty for recipe " + id);
        }
        if (this.offhandItem.isEmpty()) {
            throw new IllegalStateException("Offhand ingredient cannot be empty for recipe " + id);
        }
    }
}