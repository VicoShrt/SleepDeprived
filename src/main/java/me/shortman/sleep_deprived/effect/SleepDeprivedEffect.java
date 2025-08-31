package me.shortman.sleep_deprived.effect;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.lib.Constants;
import me.shortman.sleep_deprived.lib.RandomHelper;
import me.shortman.sleep_deprived.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import org.slf4j.Logger;

public class SleepDeprivedEffect extends MobEffect {
    protected SleepDeprivedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private final int TPS = Constants.TICKS_PER_SECOND;
    private final RandomHelper rand = new RandomHelper();

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        boolean isClient = livingEntity.level().isClientSide();
        if (isClient) {
            switch (amplifier) {
                case 0:
                    if (rand.isPercentageEnough(0.1f)) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(1f, 2f), rand.getFloatBetween(0.9f, 1.1f));
                    }
                case 1:
                    if (rand.isPercentageEnough(0.5f)) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(2f, 2.5f), rand.getFloatBetween(0.9f, 1.1f));
                    }
                case 2:
                    if (rand.isPercentageEnough(1f)) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(2.5f, 3f), rand.getFloatBetween(0.9f, 1.1f));
                    }
            }
        } else {
            switch (amplifier) {
                case 0:
                    break;
                case 1:
                    if (rand.isPercentageEnough(1f)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, rand.getIntBetween(30, 70), 0, false, false));
                    }
                    if (rand.isPercentageEnough(1f)) {
                        switch (rand.getIntBetween(1, 2)) {
                            case 1:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, rand.getIntBetween(10 * TPS, 25 * TPS), 0, false, false));
                                break;
                            case 2:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, rand.getIntBetween(10 * TPS, 25 * TPS), 0, false, false));
                                break;

                        }
                    }
                case 2:
                    if (rand.isPercentageEnough(5f)) {
                        SleepDeprived.LOGGER.debug("BLINDNESS");
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, rand.getIntBetween(40, 80), 0, false, false));
                    }
                    if (rand.isPercentageEnough(1f)) {
                        switch (rand.getIntBetween(1, 4)) {
                            case 1:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, rand.getIntBetween(10 * TPS, 25 * TPS), 0, false, false));
                                break;
                            case 2:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, rand.getIntBetween(5 * TPS, 15 * TPS), 0, false, false));
                                break;
                            case 3:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, rand.getIntBetween(10 * TPS, 25 * TPS), 0, false, false));
                                break;
                            case 4:
                                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, rand.getIntBetween(10 * TPS, 25 * TPS), 0, false, false));
                                break;
                        }
                    }
            }

        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
