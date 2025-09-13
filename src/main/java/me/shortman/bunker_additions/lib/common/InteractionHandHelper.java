package me.shortman.bunker_additions.lib.common;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;

public class InteractionHandHelper {
    public static InteractionHand getOtherHand(InteractionHand hand) {
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionHand.MAIN_HAND;
        return InteractionHand.OFF_HAND;
    }
    public static EquipmentSlot getEquipmentSlot(InteractionHand hand) {
        if (hand.equals(InteractionHand.OFF_HAND)) return EquipmentSlot.MAINHAND;
        return EquipmentSlot.OFFHAND;
    }
}
