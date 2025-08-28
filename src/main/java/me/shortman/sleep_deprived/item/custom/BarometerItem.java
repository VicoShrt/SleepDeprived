package me.shortman.sleep_deprived.item.custom;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.util.WaterPressure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;


public class BarometerItem extends Item {

    public BarometerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.diversdreams.barometer").withColor(SleepDeprived.TOOLTIP_COLOR));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            int bars = WaterPressure.get(level, player);

            String msg = Component.translatable("text.diversdreams.water_pressure").getString() + ": " + bars + "b";
            if (!level.isClientSide()) {
                player.sendSystemMessage(Component.literal(msg));
                player.getCooldowns().addCooldown(this, 10);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }


}
