package me.shortman.sleep_deprived.lib;

import java.util.Random;

public class RandomHelper {
    public final Random random = new Random();

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
