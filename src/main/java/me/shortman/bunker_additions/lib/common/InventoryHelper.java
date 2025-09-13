package me.shortman.bunker_additions.lib.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InventoryHelper {
    public static void shrinkOrDamage(int amount, ItemStack stack, LivingEntity player, InteractionHand hand) {
        Level level = player.level();
        if (level.isClientSide()) return;
        if (stack.isDamageableItem()) {
            stack.hurtAndBreak(amount, ((ServerLevel) level), player,
                    item -> player.onEquippedItemBroken(item, InteractionHandHelper.getEquipmentSlot(hand)));
        } else {
            stack.shrink(1);
        }
    }

}
