package me.shortman.sleep_deprived.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WaterPressure {
    public static int get(Level level, Player player) {
        int waterSurfaceY = calculateTopWaterY(level, player);
        int posY = player.getBlockY();
        return calculateBars(posY, waterSurfaceY);
    }

    // Helper
    private static int calculateTopWaterY(Level level, Player player) {
        int maxHeight = level.getMaxBuildHeight();
        BlockPos currentPos = player.blockPosition();

        if (!player.isInWater()) {
            return 1;
        }

        while (currentPos.getY() < maxHeight &&
                !level.getFluidState(currentPos).isEmpty()) {
            currentPos = currentPos.above();
        }

        return currentPos.getY() - 1;
    }

    private static int calculateBars(int posY, int waterSurfaceY) {
        final int BAR_THRESHOLD = 8;
        int depth = waterSurfaceY - posY;

        return Math.max(1, 1 + (depth / BAR_THRESHOLD));
    }
}
