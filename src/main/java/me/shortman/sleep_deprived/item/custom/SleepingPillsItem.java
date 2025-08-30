package me.shortman.sleep_deprived.item.custom;

import me.shortman.sleep_deprived.effect.ModEffects;
import me.shortman.sleep_deprived.event.ModEvents;
import me.shortman.sleep_deprived.event.SleepEvents;
import me.shortman.sleep_deprived.lib.Constants;
import me.shortman.sleep_deprived.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.Mod;

import java.util.List;

public class SleepingPillsItem extends Item {
    public SleepingPillsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack usedItem = player.getItemInHand(usedHand);
        if (level.isClientSide()) {
            player.playSound(ModSounds.SLEEPING_PILLS.get(), 1f, 1f);
            return InteractionResultHolder.success(usedItem);
        };
        CompoundTag data = player.getPersistentData();
        data.putInt(ModEvents.TAG_NO_SLEEP_TICKS, SleepEvents.getStageThresholdOne());
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false));
        if (player.hasEffect(ModEffects.AWAKE_EFFECT)) player.removeEffect(ModEffects.AWAKE_EFFECT);

        level.playSound(player, player.getOnPos().above(), ModSounds.SLEEPING_PILLS.get(), SoundSource.PLAYERS, 1f, 1f);

        usedItem.hurtAndBreak(1, ((ServerLevel) level), player,
                item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
        return InteractionResultHolder.success(usedItem);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.sleep_deprived.sleeping_pills_1").withColor(Constants.COLOR_DARK_GRAY).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.sleep_deprived.sleeping_pills_2").withColor(Constants.COLOR_MID_GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
