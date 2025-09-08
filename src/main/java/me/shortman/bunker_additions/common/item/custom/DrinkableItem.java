package me.shortman.bunker_additions.common.item.custom;

import me.shortman.bunker_additions.common.drug.Drugs;
import me.shortman.bunker_additions.common.registry.ModAttributes;
import me.shortman.bunker_additions.common.registry.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class DrinkableItem extends Item {
    public final int ALCOHOL_STRENGTH;
    public final int USE_DURATION;

    public DrinkableItem(Properties properties, Drugs.Alcohol.TYPE type) {
        super(properties);
        ALCOHOL_STRENGTH = Drugs.Alcohol.getStrength(type);
        USE_DURATION = Drugs.Alcohol.getUseDuration(type);
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int str = stack.getOrDefault(ModDataComponents.STRENGTH.get(), 0);
        tooltipComponents.add(Component.literal("Strength: " + str + "%"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // faster animation than DRINK
    }

    @Override
    public SoundEvent getBreakingSound() {
        // This is called when the item breaks/finishes
        return SoundEvents.GLASS_PLACE;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return getDrinkingSound();
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this.asItem());
        stack.set(ModDataComponents.STRENGTH.get(), ALCOHOL_STRENGTH);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            double str = stack.getOrDefault(ModDataComponents.STRENGTH.get(), ALCOHOL_STRENGTH);

            if (entity instanceof Player player) {
                AttributeMap attributes = player.getAttributes();
                double alcoholLevel = attributes.getInstance(ModAttributes.ALCOHOL_LEVEL).getValue();
                double alcoholToleranceMultiplier = attributes.getInstance(ModAttributes.ALCOHOL_TOLERANCE_MULTIPLIER).getValue();
                attributes.getInstance(ModAttributes.ALCOHOL_TOLERANCE_MULTIPLIER).setBaseValue(Math.max(alcoholToleranceMultiplier * 0.9, 0.3));
                attributes.getInstance(ModAttributes.ALCOHOL_LEVEL).setBaseValue(alcoholLevel + str * alcoholToleranceMultiplier);
                attributes.getInstance(ModAttributes.TICKS_SINCE_LAST_ALCOHOL).setBaseValue(0);
            }
        }
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
