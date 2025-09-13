package me.shortman.bunker_additions.common.block.entity;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.entity.container.DryingTableMenu;
import me.shortman.bunker_additions.common.crafting.DryingRecipe;
import me.shortman.bunker_additions.common.crafting.input.DryingRecipeInput;
import me.shortman.bunker_additions.common.registry.ModBlockEntities;
import me.shortman.bunker_additions.common.registry.ModItems;
import me.shortman.bunker_additions.common.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class DryingTableBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private final int inventorySize = 8;
    private final RandomSource random = RandomSource.create();

    public final ItemStackHandler inventory = new ItemStackHandler(inventorySize) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot < 4;
        }
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide())  {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int OUTPUT_SLOT_1 = 4;
    private static final int OUTPUT_SLOT_2 = 5;
    private static final int OUTPUT_SLOT_3 = 6;
    private static final int OUTPUT_SLOT_4 = 7;

    private static final Map<Integer, Integer> SLOT_PAIRS = Map.of(
            INPUT_SLOT_1, OUTPUT_SLOT_1,
            INPUT_SLOT_2, OUTPUT_SLOT_2,
            INPUT_SLOT_3, OUTPUT_SLOT_3,
            INPUT_SLOT_4, OUTPUT_SLOT_4
    );

    protected final ContainerData data;
    private int progress1 = 0;
    private int progress2 = 0;
    private int progress3 = 0;
    private int progress4 = 0;
    private int maxProgress1 = 100;
    private int maxProgress2 = 100;
    private int maxProgress3 = 100;
    private int maxProgress4 = 100;
    private int canDry = 0;

    private static final int[] INPUT_SLOTS = new int[]{0, 1, 2, 3};
    private static final int[] OUTPUT_SLOTS = new int[]{4, 5, 6, 7};

    public DryingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DRYING_TABLE_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> DryingTableBlockEntity.this.progress1;
                    case 1 -> DryingTableBlockEntity.this.progress2;
                    case 2 -> DryingTableBlockEntity.this.progress3;
                    case 3 -> DryingTableBlockEntity.this.progress4;
                    case 4 -> DryingTableBlockEntity.this.maxProgress1;
                    case 5 -> DryingTableBlockEntity.this.maxProgress2;
                    case 6 -> DryingTableBlockEntity.this.maxProgress3;
                    case 7 -> DryingTableBlockEntity.this.maxProgress4;
                    case 8 -> DryingTableBlockEntity.this.canDry;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> DryingTableBlockEntity.this.progress1 = value;
                    case 1 -> DryingTableBlockEntity.this.progress2 = value;
                    case 2 -> DryingTableBlockEntity.this.progress3 = value;
                    case 3 -> DryingTableBlockEntity.this.progress4 = value;
                    case 4 -> DryingTableBlockEntity.this.maxProgress1 = value;
                    case 5 -> DryingTableBlockEntity.this.maxProgress2 = value;
                    case 6 -> DryingTableBlockEntity.this.maxProgress3 = value;
                    case 7 -> DryingTableBlockEntity.this.maxProgress4 = value;
                    case 8 -> DryingTableBlockEntity.this.canDry = value;
                }
            }

            @Override
            public int getCount() {
                return 9;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.bunker_additions.drying_table");
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!canDry(pos)) return;

        SLOT_PAIRS.keySet().forEach((inSlot) -> {
            if (hasRecipe(inSlot)) {
                setMaxProgressForSlot(inSlot, getMaxProgressFromRecipe(inSlot));
                increaseProgressForSlot(inSlot);
                setChanged(level, pos, state);
                if (hasCraftingFinished(inSlot)) {
                    craftItem(inSlot);
                    resetProgress(inSlot);
                }
            } else {
                resetProgress(inSlot);
            }
        });
    }

    private int getMaxProgressFromRecipe(Integer slot) {
        return getCurrentRecipe(slot).get().value().ticksToDry();
    }

    private boolean canDry(BlockPos pos) {
        boolean canDryBool = level.isDay() && !level.isRainingAt(pos);
        if (canDryBool) {
            canDry = 1;
        } else {
            canDry = 0;
        }
        return canDryBool;
    }

    private void craftItem(int slot) {
        Optional<RecipeHolder<DryingRecipe>> recipe = getCurrentRecipe(slot);
        ItemStack output = recipe.get().value().output();
        int outSlot = SLOT_PAIRS.get(slot);

        inventory.extractItem(slot, 1, false);
        inventory.setStackInSlot(outSlot, new ItemStack(output.getItem(),
                inventory.getStackInSlot(outSlot).getCount() + output.getCount()));
    }

    private void resetProgress(int slot) {
        setProgressForSlot(slot, 0);
    }

    private boolean hasCraftingFinished(int slot) {
        return getProgressForSlot(slot) >= getMaxProgressForSlot(slot);
    }


    private boolean hasRecipe(int slot) {
        Optional<RecipeHolder<DryingRecipe>> recipe = getCurrentRecipe(slot);
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount(), slot) && canInsertItemIntoOutputSlot(output, slot);
    }

    private Optional<RecipeHolder<DryingRecipe>> getCurrentRecipe(int slot) {
        Optional<RecipeHolder<DryingRecipe>> recipe = this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.DRYING_TYPE.get(), new DryingRecipeInput(inventory.getStackInSlot(slot)), level);
        return recipe;

    }

    private boolean canInsertAmountIntoOutputSlot(int count, int slot) {
        int outSlot = SLOT_PAIRS.get(slot);
        int maxCount = inventory.getStackInSlot(outSlot).isEmpty() ? 64 : inventory.getStackInSlot(outSlot).getMaxStackSize();
        int currentCount = inventory.getStackInSlot(outSlot).getCount();

        return maxCount >= currentCount + count;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output, int slot) {
        int outSlot = SLOT_PAIRS.get(slot);
        return inventory.getStackInSlot(outSlot).isEmpty() ||
                inventory.getStackInSlot(outSlot).getItem() == output.getItem();
    }

    private int getProgressForSlot(int slot) {
        int progress = 0;
        switch (slot) {
            case 0 -> progress = progress1;
            case 1 -> progress = progress2;
            case 2 -> progress = progress3;
            case 3 -> progress = progress4;
        }
        return progress;
    }

    private int getMaxProgressForSlot(int slot) {
        int progress = 0;
        switch (slot) {
            case 0 -> progress = maxProgress1;
            case 1 -> progress = maxProgress2;
            case 2 -> progress = maxProgress3;
            case 3 -> progress = maxProgress4;
        }
        return progress;
    }

    private void increaseProgressForSlot(int slot) {
        setProgressForSlot(slot, getProgressForSlot(slot) + 1);
    }

    private void setProgressForSlot(int slot, int amount) {
        switch (slot) {
            case 0 -> progress1 = amount;
            case 1 -> progress2 = amount;
            case 2 -> progress3 = amount;
            case 3 -> progress4 = amount;
        }
    }

    private void setMaxProgressForSlot(int slot, int amount) {
        switch (slot) {
            case 0 -> maxProgress1 = amount;
            case 1 -> maxProgress2 = amount;
            case 2 -> maxProgress3 = amount;
            case 3 -> maxProgress4 = amount;
        }
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("drying_block.progress1", progress1);
        tag.putInt("drying_block.progress2", progress2);
        tag.putInt("drying_block.progress3", progress3);
        tag.putInt("drying_block.progress4", progress4);
        tag.putInt("drying_block.maxProgress1", maxProgress1);
        tag.putInt("drying_block.maxProgress2", maxProgress2);
        tag.putInt("drying_block.maxProgress3", maxProgress3);
        tag.putInt("drying_block.maxProgress4", maxProgress4);
        tag.putInt("drying_block.canDry", canDry);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress1 = tag.getInt("drying_block.progress1");
        progress2 = tag.getInt("drying_block.progress2");
        progress3 = tag.getInt("drying_block.progress3");
        progress4 = tag.getInt("drying_block.progress4");
        maxProgress1 = tag.getInt("drying_block.maxProgress1");
        maxProgress2 = tag.getInt("drying_block.maxProgress2");
        maxProgress3 = tag.getInt("drying_block.maxProgress3");
        maxProgress4 = tag.getInt("drying_block.maxProgress4");
        canDry = tag.getInt("drying_block.canDry");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DryingTableMenu(i, inventory, this, data);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return direction == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return slot < 4; // only input slots accept items
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot >= 4; // only output slots are extractable
    }

    @Override
    public int getContainerSize() {
        return 8;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack extracted = inventory.extractItem(slot, amount, false);
        if (!extracted.isEmpty()) setChanged();
        return extracted;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(
                (double) worldPosition.getX() + 0.5,
                (double) worldPosition.getY() + 0.5,
                (double) worldPosition.getZ() + 0.5
        ) <= 64.0;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
