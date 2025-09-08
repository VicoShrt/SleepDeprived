package me.shortman.bunker_additions.common.item.custom;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.registry.ModItems;
import me.shortman.bunker_additions.lib.InteractionHandHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import java.util.Random;


public class RawWeedBudItem extends Item {
    public RawWeedBudItem(Properties properties) {
        super(properties);
    }

    private static final int ITEM_DAMAGE_AMOUNT = 2;
    private static final int USE_DURATION = 20;
    private static final UseAnim USE_ANIM = UseAnim.BRUSH;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack otherItemStack = player.getItemInHand(InteractionHandHelper.getOtherHand(usedHand));
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (otherItemStack.is(Tags.Items.TOOLS_SHEAR)) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
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
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Random random = new Random();
        Player player = level.getPlayerByUUID(entity.getUUID());
        if (player == null) {
            BunkerAdditions.LOGGER.error("Couldn't get player from entity UUID");
            return ItemStack.EMPTY;
        }
        InteractionHand usedHand = player.getUsedItemHand();
        InteractionHand otherHand = InteractionHandHelper.getOtherHand(usedHand);
        ItemStack rawWeedStack = player.getItemInHand(usedHand);
        ItemStack otherItemStack = player.getItemInHand(otherHand);
        ItemStack droppedWeedItem = new ItemStack(ModItems.WEED_BUD.get());
        ItemStack droppedStringItem = random.nextBoolean() ? ItemStack.EMPTY : new ItemStack(ModItems.HEMP_STRING.get());

        if (!level.isClientSide()) {
            rawWeedStack.shrink(1);
            player.swing(usedHand);
            player.swing(otherHand);
            if (otherItemStack.isDamageableItem()) {
                otherItemStack.hurtAndBreak(ITEM_DAMAGE_AMOUNT, ((ServerLevel) level), player,
                        item -> player.onEquippedItemBroken(item, InteractionHandHelper.getEquipmentSlot(otherHand)));
            }
            player.addItem(droppedWeedItem);
            player.addItem(droppedStringItem);
        }
        level.playSound(player, entity.getOnPos().above(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS);
        return stack;
    }

}
