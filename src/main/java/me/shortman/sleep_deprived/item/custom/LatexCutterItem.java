package me.shortman.sleep_deprived.item.custom;

import me.shortman.sleep_deprived.SleepDeprived;
import me.shortman.sleep_deprived.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class LatexCutterItem extends Item {
    public LatexCutterItem(Properties properties) {
        super(properties);
    }

    private static final Map<Block, Integer> LATEX_MAP =
            Map.of(
                    Blocks.DANDELION, 1
            );

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.diversdreams.latex_cutter").withColor(SleepDeprived.TOOLTIP_COLOR));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        Block clickedBlock = level.getBlockState(clickedPos).getBlock();

        if (LATEX_MAP.containsKey(clickedBlock)) {
            if (!level.isClientSide()) {
                ItemStack dropItems = new ItemStack(ModItems.LATEX.get().asItem(), LATEX_MAP.get(clickedBlock));
                level.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState());
                context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), player,
                        item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
                player.addItem(dropItems);
                level.playSound(null, clickedPos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
