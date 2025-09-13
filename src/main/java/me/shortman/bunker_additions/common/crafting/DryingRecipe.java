package me.shortman.bunker_additions.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shortman.bunker_additions.common.crafting.input.DryingRecipeInput;
import me.shortman.bunker_additions.common.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record DryingRecipe(Ingredient inputItem, ItemStack output, int ticksToDry) implements Recipe<DryingRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(inputItem);
        return ingredients;
    }

    @Override
    public boolean matches(DryingRecipeInput dryingRecipeInput, Level level) {
        if (level.isClientSide()) return false;
        return inputItem.test(dryingRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(DryingRecipeInput dryingRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRYING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DRYING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DryingRecipe> {
        public static final MapCodec<DryingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(DryingRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(DryingRecipe::output),
                Codec.INT.fieldOf("ticks_to_dry").forGetter(DryingRecipe::ticksToDry)
        ).apply(inst, DryingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, DryingRecipe::inputItem,
                        ItemStack.STREAM_CODEC, DryingRecipe::output,
                        ByteBufCodecs.VAR_INT, DryingRecipe::ticksToDry,
                        DryingRecipe::new);

        @Override
        public MapCodec<DryingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
