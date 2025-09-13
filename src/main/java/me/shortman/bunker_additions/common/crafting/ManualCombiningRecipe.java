package me.shortman.bunker_additions.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shortman.bunker_additions.common.crafting.input.ManualCombiningRecipeInput;
import me.shortman.bunker_additions.common.registry.ModRecipes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;
public record ManualCombiningRecipe(Ingredient mainhandItem, Ingredient offhandItem, Ingredient output,
                                    boolean doesHandMatter, int ticksToCraft, Optional<Holder<SoundEvent>> sound) implements Recipe<ManualCombiningRecipeInput> {
    public static SoundEvent DEFAULT_SOUND_EVENT = SoundEvents.AMETHYST_BLOCK_BREAK;

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(0, mainhandItem);
        list.add(1, offhandItem);
        return list;
    }

    @Override
    public boolean matches(ManualCombiningRecipeInput manualCombiningRecipeInput, Level level) {
        if (level.isClientSide()) return false;
        boolean matchInput1;
        boolean matchInput2;
        matchInput1 = mainhandItem.test(manualCombiningRecipeInput.getItem(0));
        matchInput2 = offhandItem.test(manualCombiningRecipeInput.getItem(1));
        if (!this.doesHandMatter && (!matchInput1 || !matchInput2)) {
            matchInput1 = mainhandItem.test(manualCombiningRecipeInput.getItem(1));
            matchInput2 = offhandItem.test(manualCombiningRecipeInput.getItem(0));
        }
        return matchInput1 && matchInput2;
    }

    @Override
    public ItemStack assemble(ManualCombiningRecipeInput manualCombiningRecipeInput, HolderLookup.Provider provider) {
        return output.getItems()[0].copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.getItems()[0];
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MANUAL_COMBINING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MANUAL_COMBINING_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public SoundEvent getSoundEvent() {
        SoundEvent soundEvent = DEFAULT_SOUND_EVENT;
        if (sound.isPresent()) {
            soundEvent = sound.get().value();
        }
        return soundEvent;
    }

    public ItemStack getMainhandInput() {
        return mainhandItem.getItems()[0];
    }

    public ItemStack getOffhandInput() {
        return offhandItem.getItems()[0];
    }

    public ItemStack[] getResults() {
        return output.getItems();
    }

    /* SERIALIZER */
    public static class Serializer implements RecipeSerializer<ManualCombiningRecipe> {
        public static final MapCodec<ManualCombiningRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("mainhand").forGetter(ManualCombiningRecipe::mainhandItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("offhand").forGetter(ManualCombiningRecipe::offhandItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("result").forGetter(ManualCombiningRecipe::output),
                Codec.BOOL.fieldOf("does_hand_matter").forGetter(ManualCombiningRecipe::doesHandMatter),
                Codec.INT.fieldOf("ticks_to_craft").forGetter(ManualCombiningRecipe::ticksToCraft),
                SoundEvent.CODEC.optionalFieldOf("sound").forGetter(ManualCombiningRecipe::sound)
        ).apply(inst, ManualCombiningRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ManualCombiningRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ManualCombiningRecipe::mainhandItem,
                        Ingredient.CONTENTS_STREAM_CODEC, ManualCombiningRecipe::offhandItem,
                        Ingredient.CONTENTS_STREAM_CODEC, ManualCombiningRecipe::output,
                        ByteBufCodecs.BOOL, ManualCombiningRecipe::doesHandMatter,
                        ByteBufCodecs.VAR_INT, ManualCombiningRecipe::ticksToCraft,
                        ByteBufCodecs.optional(SoundEvent.STREAM_CODEC), ManualCombiningRecipe::sound,
                        ManualCombiningRecipe::new);

        @Override
        public MapCodec<ManualCombiningRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ManualCombiningRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
