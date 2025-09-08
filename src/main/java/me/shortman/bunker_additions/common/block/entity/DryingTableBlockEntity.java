package me.shortman.bunker_additions.common.block.entity;

import me.shortman.bunker_additions.common.block.DryingTable;
import me.shortman.bunker_additions.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DryingTableBlockEntity extends BlockEntity {
    private final int inventorySize = 1;
    private final RandomSource random = RandomSource.create();

    public final ItemStackHandler inventory = new ItemStackHandler(inventorySize) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return inventorySize;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide())  {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private float rotation;

    public DryingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DRYING_TABLE_BLOCK_ENTITY.get(), pos, blockState);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level.isClientSide) return;
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;
        Map<Integer, List<Item>> dryingList = DryingTable.getDryingMap();
        // check if item is in drying list
        dryingList.forEach((id, items) -> {
            Item input = items.get(0);
            Item output = items.get(1);

            if (stack.is(input)) {
                // chance: 5x per Minecraft day (~24000 ticks/day)
                // => chance per tick = 5 / 24000 ≈ 0.000208
                if (random.nextFloat() < (5f / 24000f)) {
                    inventory.setStackInSlot(0, new ItemStack(output, stack.getCount()));
                    setChanged();
                }
            }
        });
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
