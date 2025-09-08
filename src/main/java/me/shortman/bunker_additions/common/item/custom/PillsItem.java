package me.shortman.bunker_additions.common.item.custom;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PillsItem extends Item {
    public PillsItem(Properties properties) {
        super(properties);
    }

    private static final int USE_DURATION = 15;
    private static final UseAnim USE_ANIM = UseAnim.DRINK;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        player.playSound(ModSounds.PILLS_OPEN.get());
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return USE_ANIM;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Player player = level.getPlayerByUUID(entity.getUUID());
        if (player == null) {
            BunkerAdditions.LOGGER.error("Couldn't get player from entity UUID");
            return ItemStack.EMPTY;
        }

        if (!level.isClientSide()) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            player.getCooldowns().addCooldown(stack.getItem(), 30);
        } else if (stack.getCount() > 1) {
            player.playSound(ModSounds.PILLS_CLOSE.get());
        }
        return stack;
    }
}
