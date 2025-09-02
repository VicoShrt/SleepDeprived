package me.shortman.sleep_deprived.event;

import me.shortman.sleep_deprived.Config;
import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.effect.ModEffects;
import me.shortman.sleep_deprived.lib.Constants;
import me.shortman.sleep_deprived.lib.Convertor;
import me.shortman.sleep_deprived.lib.RandomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SleepEvents {
    private static final RandomHelper rand = new RandomHelper();

    private static final int TPS = Constants.TICKS_PER_SECOND;
    private static final int TPM = Constants.TICKS_PER_MINUTE;

    private static final int FATIGUE_THRESHOLD_ONE = Config.FATIGUE_THRESHOLD.getAsInt() * TPM;
    private static final int FATIGUE_THRESHOLD_TWO = (FATIGUE_THRESHOLD_ONE + Config.MINUTES_TO_NEXT_STAGE.getAsInt() * TPM) ;
    private static final int FATIGUE_THRESHOLD_THREE = (FATIGUE_THRESHOLD_TWO + Config.MINUTES_TO_NEXT_STAGE.getAsInt() * TPM);

    private static final int EFFECT_TIME = 10 * TPS;
    private static final int TICKS_TO_SLEEP_THROUGH = 90;
    private static final int AWAKE_AFTER_SLEEP = Config.AWAKE_AFTER_SLEEP.getAsInt() * TPS;

    public static int getFatigueThresholdOne() {
        return FATIGUE_THRESHOLD_ONE;
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        for (ServerPlayer player : serverLevel.players()) {
            CompoundTag data = player.getPersistentData();
            int fatigueLevel = data.getInt(ModEvents.TAG_FATIGUE_LEVEL);
            int ticksSinceLastSlept = data.getInt(ModEvents.TAG_TICKS_SINCE_LAST_SLEPT);
            int ticksSlept = data.getInt(ModEvents.TAG_SLEEP_TICKS);
            if (player.isSleeping()) {
                data.putInt(ModEvents.TAG_SLEEP_TICKS, ticksSlept + 1);
                if (ticksSlept >= TICKS_TO_SLEEP_THROUGH) {
                    data.putInt(ModEvents.TAG_FATIGUE_LEVEL, 0);
                    data.putInt(ModEvents.TAG_TICKS_SINCE_LAST_SLEPT, 0);
                    player.addEffect(new MobEffectInstance(ModEffects.AWAKE_EFFECT, AWAKE_AFTER_SLEEP, 0, false, false));
                }
            } else if (!player.hasEffect(ModEffects.AWAKE_EFFECT)) {
                data.putInt(ModEvents.TAG_SLEEP_TICKS, 0);
                data.putInt(ModEvents.TAG_FATIGUE_LEVEL, fatigueLevel + 1);
                data.putInt(ModEvents.TAG_TICKS_SINCE_LAST_SLEPT, ticksSinceLastSlept + 1);
            }

            if (fatigueLevel % TPS == 0) {
                // Add effect according to ticks not slept
                boolean hasNotAwakeEffect = !player.hasEffect(ModEffects.AWAKE_EFFECT);
                if ((fatigueLevel > (FATIGUE_THRESHOLD_ONE) && fatigueLevel <= FATIGUE_THRESHOLD_TWO) && hasNotAwakeEffect) {
                    data.putInt(ModEvents.TAG_SLEEP_DEPRIVED_STAGE, 1);
                    player.addEffect(new MobEffectInstance(ModEffects.SLEEP_DEPRIVED_EFFECT, EFFECT_TIME, 0, false, false));
                } else if ((fatigueLevel > (FATIGUE_THRESHOLD_TWO) && fatigueLevel <= FATIGUE_THRESHOLD_THREE) && hasNotAwakeEffect) {
                    data.putInt(ModEvents.TAG_SLEEP_DEPRIVED_STAGE, 2);
                    player.addEffect(new MobEffectInstance(ModEffects.SLEEP_DEPRIVED_EFFECT, EFFECT_TIME, 1, false, false));
                } else if ((fatigueLevel > FATIGUE_THRESHOLD_THREE) && hasNotAwakeEffect) {
                    data.putInt(ModEvents.TAG_SLEEP_DEPRIVED_STAGE, 3);
                    player.addEffect(new MobEffectInstance(ModEffects.SLEEP_DEPRIVED_EFFECT, EFFECT_TIME, 2, false, false));
                }
                // Remove effect
                if (fatigueLevel <= FATIGUE_THRESHOLD_ONE) {
                    data.putInt(ModEvents.TAG_SLEEP_DEPRIVED_STAGE, 0);
                    player.removeEffect(ModEffects.SLEEP_DEPRIVED_EFFECT);
                }
            }
        }
    }

    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) return;
        Player player = event.getEntity();
        CompoundTag data = player.getPersistentData();

        ItemStack stack = event.getItemStack();
        if (stack.is(Items.CLOCK)) {
            String msg;
            int fatigueLevel = Math.min(data.getInt(ModEvents.TAG_FATIGUE_LEVEL), FATIGUE_THRESHOLD_ONE);

            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);

            float fatiguePercentage = Math.min(((float) fatigueLevel / FATIGUE_THRESHOLD_ONE) * 100, 100);

            String fatiguePercentageStr = " (" + df.format(fatiguePercentage) + "% " + Component.translatable("player.sleep_deprived.tired").getString() + ")";
            String fatigueLevelToThresholdRatio =  fatigueLevel + "/" + FATIGUE_THRESHOLD_ONE;

            if (fatiguePercentage < 26) {
                msg = Component.translatable("player.sleep_deprived.well_rested").getString() + fatiguePercentageStr;
            } else if (fatiguePercentage < 66) {
                msg = Component.translatable("player.sleep_deprived.feel_fit").getString() + fatiguePercentageStr;
            } else if (fatiguePercentage < 100) {
                msg = Component.translatable("player.sleep_deprived.feel_exhausted").getString() + fatiguePercentageStr;
            } else {
                msg = Component.translatable("player.sleep_deprived.need_to_sleep").getString() + fatiguePercentageStr;
            }
            player.sendSystemMessage(Component.literal(msg + " - " + fatigueLevelToThresholdRatio));
        }
    }

    public static void onPlayerClicksBed(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().isClientSide() &&
                event.getLevel().getBlockState(event.getPos()).isBed(event.getLevel(), event.getPos(), event.getEntity()) &&
                event.getEntity().hasEffect(ModEffects.AWAKE_EFFECT)) {
            event.getEntity().sendSystemMessage(Component.translatable("player.sleep_deprived.cant_sleep"));
            event.setCanceled(true);
        }
    }
}
