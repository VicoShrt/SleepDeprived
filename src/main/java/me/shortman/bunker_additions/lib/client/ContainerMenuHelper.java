package me.shortman.bunker_additions.lib.client;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class ContainerMenuHelper {
    public static List<Slot> getPlayerInventorySlots(Inventory playerInventory) {
        List<Slot> list = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                list.add(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
        return list;
    }
    public static List<Slot> getPlayerHotbarSlots(Inventory playerInventory) {
        List<Slot> list = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < 9; ++i) {
            list.add(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        return list;
    }
}
