package me.shortman.bunker_additions.lib.util;

import me.shortman.bunker_additions.BunkerAdditions;

import java.util.Random;

public class RandomHelper {
    public final static Random random = new Random();
    public static class Tick {
        public int minBetween;
        public int maxBetween;
        public float chancePerTick;
        public int ticksPassedSinceLastSuccess = 0;
        public Tick(int minBetween, int maxBetween, float chancePerTick) {
            this.minBetween = minBetween;
            this.maxBetween = maxBetween;
            this.chancePerTick = chancePerTick;
        }
        public boolean run() {
            boolean success;
            ticksPassedSinceLastSuccess ++;
            BunkerAdditions.LOGGER.debug("Ticks passed: " + ticksPassedSinceLastSuccess);
            if (ticksPassedSinceLastSuccess <= minBetween) {
                success = false;
            } else if (ticksPassedSinceLastSuccess >= maxBetween){
                success = true;
            } else {
                success = new RandomHelper().isPercentageEnough(chancePerTick);
            }
            if (success) {
                ticksPassedSinceLastSuccess = 0;
            }
            return success;
        }
    }

    public static float getFloatBetween(float min, float max) {
        return random.nextFloat(max - min) + min;
    }
    public static int getIntBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }
    public static boolean getBoolean() {
        return random.nextBoolean();
    }

    public boolean isPercentageEnough(float percentage) {
        if (percentage > 100) percentage = 100;
        if (percentage < 0) percentage = 0;
        return random.nextFloat() * 100 < percentage;
    }
}
