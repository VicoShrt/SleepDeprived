package me.shortman.bunker_additions.integration.jei;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.crafting.ManualCombiningRecipe;
import me.shortman.bunker_additions.common.registry.ModBlocks;
import me.shortman.bunker_additions.common.registry.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ManualCombiningRecipeCategory implements IRecipeCategory<ManualCombiningRecipe> {
    public static final ResourceLocation UID = BunkerAdditions.resource("manual_combining");
    public static final ResourceLocation TEXTURE = BunkerAdditions.resource("textures/gui/jei/manual_combining_gui.png");

    public static final RecipeType<ManualCombiningRecipe> MANUAL_COMBINING_RECIPE_TYPE =
            new RecipeType<>(UID, ManualCombiningRecipe.class);

    public final IDrawable background;
    public final IDrawable icon;

    public ManualCombiningRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0,0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SCREWDRIVER.get()));
    }

    @Override
    public RecipeType<ManualCombiningRecipe> getRecipeType() {
        return MANUAL_COMBINING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.bunker_additions.drying_table");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ManualCombiningRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 20).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 49).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).addItemStack(recipe.getResultItem(null));
    }
}
