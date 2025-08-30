package me.shortman.sleep_deprived.item.custom;

import me.shortman.sleep_deprived.lib.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;

import java.util.List;

public class EnergyDrinkItem extends Item {
    public EnergyDrinkItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.sleep_deprived.energy_drink_1").withColor(Constants.COLOR_DARK_GRAY).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.sleep_deprived.energy_drink_2").withColor(Constants.COLOR_MID_GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
