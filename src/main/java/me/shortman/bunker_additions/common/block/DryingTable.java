package me.shortman.bunker_additions.common.block;

import com.mojang.serialization.MapCodec;
import me.shortman.bunker_additions.common.block.entity.DryingTableBlockEntity;
import me.shortman.bunker_additions.common.registry.ModBlocks;
import me.shortman.bunker_additions.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DryingTable extends BaseEntityBlock {
    public static final VoxelShape SHAPE = BaseEntityBlock.box(0,0,0, 16,16,16);
    public static final MapCodec<DryingTable> CODEC = simpleCodec(DryingTable::new);

    public DryingTable(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    /* BLOCK ENTITY  */

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DryingTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof DryingTableBlockEntity dryingTableBlockEntity) {
                dryingTableBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof DryingTableBlockEntity dryingTableBlockEntity) {
            if (dryingTableBlockEntity.inventory.getStackInSlot(0).isEmpty() && isValidInsertItem(stack.getItem())) {
                dryingTableBlockEntity.inventory.insertItem(0, stack.copy(), false);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if (!dryingTableBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
                ItemStack stackOnDryingTable = dryingTableBlockEntity.inventory.extractItem(0, 1, false);
                player.addItem(stackOnDryingTable);
                dryingTableBlockEntity.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // only run on server
        if (level.isClientSide) return null;

        return (lvl, pos, bs, be) -> {
            if (!(be instanceof DryingTableBlockEntity drying)) return;
            drying.tick(lvl, pos, bs, drying);
        };
    }

    public static Map<Integer, List<Item>> getDryingMap() {
        return Map.of(
                0, List.of(Items.AIR, Items.AIR),
                1, List.of(ModItems.TOBACCO_LEAF.get(), ModItems.TOBACCO_DRIED_LEAF.get()),
                2, List.of(ModItems.WET_WEED_BUD.get(), ModItems.WEED_BUD.get())
        );
    }

    private static boolean isValidInsertItem(Item item) {
        if (item == Items.AIR) {
            return false;
        }
        AtomicBoolean toReturn = new AtomicBoolean(false);
        Map<Integer, List<Item>> dryingMap = getDryingMap();
        dryingMap.forEach((key, itemList) ->  {
            if (itemList.getFirst() == item) {
                toReturn.set(true);
            };
        });
        return toReturn.get();
    }

    private static int getKeyFromItem(Item item) {
        AtomicInteger toReturn = new AtomicInteger(0);
        Map<Integer, List<Item>> dryingMap = getDryingMap();
        dryingMap.forEach((key, itemList) ->  {
            if (itemList.contains(item)) {
                toReturn.set(key);
            };
        });
        return toReturn.get();
    }
    private static Item getEndItemFromKey(int key) {
        Map<Integer, List<Item>> dryingMap = getDryingMap();
        return dryingMap.get(key).get(1);
    }
    private static Item getStartItemFromKey(int key) {
        Map<Integer, List<Item>> dryingMap = getDryingMap();
        return dryingMap.get(key).getFirst();
    }
}
