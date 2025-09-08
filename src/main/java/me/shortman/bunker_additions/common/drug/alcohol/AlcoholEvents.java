package me.shortman.bunker_additions.common.drug.alcohol;

import me.shortman.bunker_additions.common.registry.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.checkerframework.checker.units.qual.C;

public class AlcoholEvents {
    public static final double MAX_TICKS_SINCE_LAST_ALCOHOL = 24000;
    public static final int TICKS_TO_SUBTRACT_LEVEL_AFTER = 300;
    public static final int TICKS_TO_CHANGE_TOLERANCE_AFTER = 600;

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide()) return;
        event.getLevel().players().forEach(player -> {
            int playerTicks = player.tickCount;
            AttributeInstance alcoholToleranceAttribute = player.getAttribute(ModAttributes.ALCOHOL_TOLERANCE_MULTIPLIER);
            double alcoholTolerance = alcoholToleranceAttribute.getValue();
            if (playerTicks % TICKS_TO_CHANGE_TOLERANCE_AFTER == 0) {
                alcoholToleranceAttribute.setBaseValue(Math.min(1, alcoholTolerance * 1.1));
            }

            AttributeInstance lastAlcoholAttribute = player.getAttribute(ModAttributes.TICKS_SINCE_LAST_ALCOHOL);
            double ticksSinceLastAlcohol = lastAlcoholAttribute.getBaseValue();
            if (ticksSinceLastAlcohol >= MAX_TICKS_SINCE_LAST_ALCOHOL) {
                lastAlcoholAttribute.setBaseValue(MAX_TICKS_SINCE_LAST_ALCOHOL);
            } else {
                lastAlcoholAttribute.setBaseValue(ticksSinceLastAlcohol + 1);
            }

            AttributeInstance alcoholLevelAttribute = player.getAttribute(ModAttributes.ALCOHOL_LEVEL);
            double alcoholLevel = alcoholLevelAttribute.getValue();
            if (playerTicks % TICKS_TO_SUBTRACT_LEVEL_AFTER == 0) {
                alcoholLevelAttribute.setBaseValue(alcoholLevel - 1);
            }


        });
    }
}
