package me.shortman.bunker_additions.integration.jei;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.crafting.DryingRecipe;
import me.shortman.bunker_additions.common.registry.ModBlocks;
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

public class DryingRecipeCategory implements IRecipeCategory<DryingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID, "drying");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BunkerAdditions.MOD_ID,
            "textures/gui/jei/drying_gui.png");

    public static final RecipeType<DryingRecipe> DRYING_RECIPE_TYPE =
            new RecipeType<>(UID, DryingRecipe.class);

    public final IDrawable background;
    public final IDrawable icon;

    public DryingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0,0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DRYING_TABLE));
    }

    @Override
    public RecipeType<DryingRecipe> getRecipeType() {
        return DRYING_RECIPE_TYPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, DryingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).addItemStack(recipe.getResultItem(null));
    }
}
