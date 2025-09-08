package me.shortman.bunker_additions.common.drug;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Drugs {
    public enum TYPE {
        NONE,
        THC,
        ALCOHOL,
        NICOTINE
    }
    public static class Alcohol {
        public enum TYPE {
            BEER,
            WINE,
            BOOZE
        }
        public static Integer getStrength(TYPE type) {
            return ALCOHOL_STRENGTH_MAP.get(type);
        }

        public static Integer getUseDuration(TYPE type) {
            return ALCOHOL_USE_DURATION_MAP.get(type);
        }

        public static List<Holder<MobEffect>> getMobEffectsFromLevel(Integer level) {
            if (level == 0) return null;
            AtomicReference<List<Holder<MobEffect>>> toReturn = new AtomicReference<>();
            ALCOHOL_LEVEL_EFFECT_MAP.forEach(
                    (integer, holders) -> {
                        if (integer >= level) {
                            toReturn.set(holders);
                        }
                    }
            );
            return toReturn.get();
        }

        private static final Map<TYPE, Integer> ALCOHOL_STRENGTH_MAP = Map.of(
                TYPE.BEER, 5,
                TYPE.WINE, 7,
                TYPE.BOOZE, 30
        );
        private static final Map<TYPE, Integer> ALCOHOL_USE_DURATION_MAP = Map.of(
                TYPE.BEER, 60,
                TYPE.WINE, 60,
                TYPE.BOOZE, 60
        );
        private static final Map<Integer, List<Holder<MobEffect>>> ALCOHOL_LEVEL_EFFECT_MAP = Map.of(
                20, List.of(MobEffects.REGENERATION, MobEffects.DIG_SPEED, MobEffects.MOVEMENT_SPEED),
                40, List.of(MobEffects.CONFUSION, MobEffects.DIG_SLOWDOWN, MobEffects.MOVEMENT_SLOWDOWN),
                80, List.of(MobEffects.CONFUSION, MobEffects.DARKNESS)
                );


    }
}
