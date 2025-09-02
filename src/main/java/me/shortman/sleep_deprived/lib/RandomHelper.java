package me.shortman.sleep_deprived.lib;

import me.shortman.sleep_deprived.SleepDeprived;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.Random;

public class RandomHelper {
    public final Random random = new Random();
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
            SleepDeprived.LOGGER.debug("Ticks passed: " + ticksPassedSinceLastSuccess);
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

    public float getFloatBetween(float min, float max) {
        return random.nextFloat(max - min) + min;
    }
    public int getIntBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }
    public boolean getBoolean() {
        return random.nextBoolean();
    }

    public boolean isPercentageEnough(float percentage) {
        if (percentage > 100) percentage = 100;
        if (percentage < 0) percentage = 0;
        return random.nextFloat() * 100 < percentage;
    }
}
