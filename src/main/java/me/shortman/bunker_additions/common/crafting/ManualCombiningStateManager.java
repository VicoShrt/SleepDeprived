package me.shortman.bunker_additions.common.crafting;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ManualCombiningStateManager {
    private static final Map<UUID, ManualCombiningState> activeCrafting = new ConcurrentHashMap<>();

    public static class ManualCombiningState {
        public final ManualCombiningRecipe recipe;
        public final ItemStack mainHand;
        public final ItemStack offHand;
        public final int totalTicks;
        public int currentTicks;
        public boolean completed;

        public ManualCombiningState(ManualCombiningRecipe recipe, ItemStack mainHand, ItemStack offHand, int totalTicks) {
            this.recipe = recipe;
            this.mainHand = mainHand.copy();
            this.offHand = offHand.copy();
            this.totalTicks = totalTicks;
            this.currentTicks = 0;
            this.completed = false;
        }

        public float getProgress() {
            return totalTicks > 0 ? (float) currentTicks / totalTicks : 1.0f;
        }

        public boolean isComplete() {
            return currentTicks >= totalTicks;
        }
    }

    public static void startCrafting(Player player, ManualCombiningRecipe recipe, ItemStack mainHand, ItemStack offHand, int craftingTicks) {
        activeCrafting.put(player.getUUID(), new ManualCombiningStateManager.ManualCombiningState(recipe, mainHand, offHand, craftingTicks));
    }

    public static ManualCombiningState getCraftingState(Player player) {
        return activeCrafting.get(player.getUUID());
    }

    public static void removeCrafting(Player player) {
        activeCrafting.remove(player.getUUID());
    }

    public static boolean isCrafting(Player player) {
        return activeCrafting.containsKey(player.getUUID());
    }

    public static void tick() {
        activeCrafting.entrySet().removeIf(entry -> {
            ManualCombiningState state = entry.getValue();
            state.currentTicks++;

            if (state.isComplete() && !state.completed) {
                state.completed = true;
                return false;
            }
            return state.completed;
        });
    }
}