package me.shortman.sleep_deprived.effect;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.event.ModEvents;
import me.shortman.sleep_deprived.lib.Constants;
import me.shortman.sleep_deprived.lib.RandomHelper;
import me.shortman.sleep_deprived.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
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
    private final int TPM = Constants.TICKS_PER_MINUTE;
    private final RandomHelper rand = new RandomHelper();
    RandomHelper.Tick randomYawnTick1 = new RandomHelper.Tick(45 * TPS, 3 * TPM, 0.2f);
    RandomHelper.Tick randomYawnTick2 = new RandomHelper.Tick(30 * TPS, 2 * TPM, 0.5f);
    RandomHelper.Tick randomYawnTick3 = new RandomHelper.Tick(15 * TPS, TPM, 1f);
    RandomHelper.Tick randomEffectTick1 = new RandomHelper.Tick(45 * TPS, TPM, 0.2f);
    RandomHelper.Tick randomEffectTick2 = new RandomHelper.Tick(30 * TPS, TPM, 0.5f);
    RandomHelper.Tick randomEffectTick3 = new RandomHelper.Tick(15 * TPS, TPM, 1f);

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        boolean isClient = livingEntity.level().isClientSide();
        CompoundTag data = livingEntity.getPersistentData();

        int playerSleepDeprivedStage = data.getInt(ModEvents.TAG_SLEEP_DEPRIVED_STAGE);

        if (isClient) {
            switch (playerSleepDeprivedStage) {
                case 1:
                    if (randomYawnTick1.run()) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(1f, 2f), rand.getFloatBetween(0.9f, 1.1f));
                    }
                case 2:
                    if (randomYawnTick2.run()) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(2f, 2.5f), rand.getFloatBetween(0.9f, 1.1f));
                    }
                case 3:
                    if (randomYawnTick3.run()) {
                        livingEntity.playSound(ModSounds.YAWN.get(), rand.getFloatBetween(2.5f, 3f), rand.getFloatBetween(0.9f, 1.1f));
                    }
                default:
                    break;
            }
        } else {
            switch (playerSleepDeprivedStage) {
                case 1:
                    if (randomEffectTick1.run()) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, rand.getIntBetween(30, 70), 0, false, false));
                    }
                case 2:
                    if (randomEffectTick2.run()) {
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
                case 3:
                    if (randomEffectTick3.run()) {
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
                default:
                    break;
            }

        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
