package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.client.animation.ClientManualCombiningAnimation;
import me.shortman.bunker_additions.common.crafting.ManualCombiningRecipe;
import me.shortman.bunker_additions.common.crafting.ManualCombiningStateManager;
import me.shortman.bunker_additions.common.crafting.input.ManualCombiningRecipeInput;
import me.shortman.bunker_additions.common.network.StartManualCombiningPacket;
import me.shortman.bunker_additions.common.network.UpdateManualCombiningPacket;
import me.shortman.bunker_additions.common.registry.ModRecipes;
import me.shortman.bunker_additions.lib.common.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Arrays;
import java.util.Optional;

public class ManualCombiningEvents {

    /* EVENTS */
    public static void onRightClickItems(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        if (level.isClientSide) return;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (ManualCombiningStateManager.isCrafting(player)) {
            return;
        }

        Optional<RecipeHolder<ManualCombiningRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.MANUAL_COMBINING_TYPE.get(),
                        new ManualCombiningRecipeInput(mainHand, offHand), level);

        if (recipeOptional.isPresent()) {
            ManualCombiningRecipe recipe = recipeOptional.get().value();
            int craftingTicks = recipe.ticksToCraft();
            ManualCombiningStateManager.startCrafting(player, recipe, mainHand, offHand, craftingTicks);

            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer,
                        new StartManualCombiningPacket(craftingTicks, 0.0f)
                );
            }
            event.setCanceled(true);
        }
    }

    public static void onServerTick(ServerTickEvent.Post event) {

        ManualCombiningStateManager.tick();
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            ManualCombiningStateManager.ManualCombiningState state = ManualCombiningStateManager.getCraftingState(player);

            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            boolean cancelled = false;
            if (state != null) {
                if (!mainHand.is(state.mainHand.getItem()) || !offHand.is(state.offHand.getItem())) {
                    cancelled = true;
                    ManualCombiningStateManager.removeCrafting(player);
                }
                PacketDistributor.sendToPlayer(player,
                        new UpdateManualCombiningPacket(state.getProgress(), state.isComplete(), cancelled)
                );
                if (state.currentTicks % 16 == 0) {
                    player.swing(InteractionHand.MAIN_HAND, true);
                } else if (state.currentTicks % 16 == 9) {
                    player.swing(InteractionHand.OFF_HAND, true);
                }
                if (state.completed) {
                    completeCrafting(player, state);
                }
            }
        }
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        ClientManualCombiningAnimation.tick();
    }

    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (net.minecraft.client.Minecraft.getInstance().screen == null) {
            ClientManualCombiningAnimation.renderCraftingOverlay(event.getGuiGraphics(),
                    event.getGuiGraphics().guiWidth(),
                    event.getGuiGraphics().guiHeight());

        }
    }

    /* HELPER */
    private static void completeCrafting(ServerPlayer player, ManualCombiningStateManager.ManualCombiningState state) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        player.level().playSound(null, player.getOnPos().above(),
                state.recipe.getSoundEvent(), SoundSource.PLAYERS, 1f, 1f);

        if (mainHand.is(state.mainHand.getItem()) && offHand.is(state.offHand.getItem())) {
            InventoryHelper.shrinkOrDamage(1, mainHand, player, InteractionHand.MAIN_HAND);
            InventoryHelper.shrinkOrDamage(1, offHand, player, InteractionHand.OFF_HAND);
            ItemStack[] results = state.recipe.getResults();
            Arrays.stream(results).iterator().forEachRemaining((result) -> {
                ItemStack resultCopy = result.copy();
                if (!player.getInventory().add(resultCopy)) {
                    player.drop(resultCopy, false);
                }
            });
        }

        ManualCombiningStateManager.removeCrafting(player);
    }
}
