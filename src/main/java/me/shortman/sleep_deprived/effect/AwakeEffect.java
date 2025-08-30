package me.shortman.sleep_deprived.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class AwakeEffect extends MobEffect {
    protected AwakeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        livingEntity.removeEffect(ModEffects.SLEEP_DEPRIVED_EFFECT);
    }
}
