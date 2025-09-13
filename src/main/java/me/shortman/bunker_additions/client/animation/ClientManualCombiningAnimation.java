package me.shortman.bunker_additions.client.animation;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientManualCombiningAnimation {
    private static boolean isCrafting = false;
    private static int totalTicks = 0;
    private static float currentProgress = 0.0f;
    private static int animationTicks = 0;

    public static void startCrafting(int total, float progress) {
        isCrafting = true;
        totalTicks = total;
        currentProgress = progress;
        animationTicks = 0;

    }

    public static void updateCrafting(float progress, boolean completed, boolean cancelled) {
        currentProgress = progress;
        if (cancelled) {
            stopCrafting();
        }
        if (completed) {
            // Play completion effects
            playCompletionEffects();
            stopCrafting();
        }
    }

    public static void stopCrafting() {
        isCrafting = false;
        totalTicks = 0;
        currentProgress = 0.0f;
        animationTicks = 0;
    }

    public static void tick() {
        if (isCrafting) {
            animationTicks++;
            // Update any client-side animations here
        }
    }

    public static boolean isCrafting() {
        return isCrafting;
    }

    public static float getProgress() {
        return currentProgress;
    }

    public static int getAnimationTicks() {
        return animationTicks;
    }

    // Method to render progress bar or other UI elements
    public static void renderCraftingOverlay(GuiGraphics graphics, int screenWidth, int screenHeight) {
        if (!isCrafting) return;

        int barWidth = 100;
        int barHeight = 8;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight / 2 + 20;

        // Background
        graphics.fill(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, 0xFF000000);
        graphics.fill(x, y, x + barWidth, y + barHeight, 0xFF333333);

        int progressWidth = (int) (barWidth * currentProgress);
        graphics.fill(x, y, x + progressWidth, y + barHeight, 0xFF00FF00);

        String text = "Crafting... " + (int)(currentProgress * 100) + "%";
        int textWidth = Minecraft.getInstance().font.width(text);
        graphics.drawString(Minecraft.getInstance().font, text,
                (screenWidth - textWidth) / 2, y - 15, 0xFFFFFF);
    }

    private static void playCompletionEffects() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            for (int i = 0; i < 10; i++) {
                player.level().addParticle(ParticleTypes.HAPPY_VILLAGER,
                        player.getX() + (Math.random() - 0.5) * 2,
                        player.getY() + 1 + Math.random(),
                        player.getZ() + (Math.random() - 0.5) * 2,
                        0, 0.5, 0);
            }
        }
    }
}