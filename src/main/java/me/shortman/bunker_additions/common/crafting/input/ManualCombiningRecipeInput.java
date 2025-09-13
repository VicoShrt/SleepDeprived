package me.shortman.bunker_additions.common.crafting.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ManualCombiningRecipeInput(ItemStack mainhandInput, ItemStack offhandInput) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        ItemStack toReturn;
        switch (i) {
            case 0 -> toReturn = this.mainhandInput;
            case 1 -> toReturn = this.offhandInput;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + i);
        }
        return toReturn;
    }

    @Override
    public int size() {
        return 2;
    }
}
