package me.shortman.bunker_additions.common.block.entity.container;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.entity.DryingTableBlockEntity;
import me.shortman.bunker_additions.common.registry.ModBlocks;
import me.shortman.bunker_additions.common.registry.ModMenuTypes;
import me.shortman.bunker_additions.lib.client.ContainerMenuHelper;
import me.shortman.bunker_additions.lib.common.SlotItemHandlerOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class DryingTableMenu extends AbstractContainerMenu {
    public final DryingTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public DryingTableMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(9));
    }

    public DryingTableMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.DRYING_TABLE_MENU.get(), containerId);
        if (!(blockEntity instanceof DryingTableBlockEntity dryingTable)) {
            throw new IllegalStateException("BlockEntity is not a DryingTableBlockEntity: " + blockEntity);
        }
        this.blockEntity = dryingTable;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        int x_SLOT_1 = 12;
        int y_SLOT_1 = 20;
        this.addSlot(new SlotItemHandler(((DryingTableBlockEntity) blockEntity).inventory, 0, x_SLOT_1, y_SLOT_1));
        int x_SLOT_2 = 94;
        int y_SLOT_2 = 20;
        this.addSlot(new SlotItemHandler(((DryingTableBlockEntity) blockEntity).inventory, 1, x_SLOT_2, y_SLOT_2));
        int x_SLOT_3 = 12;
        int y_SLOT_3 = 49;
        this.addSlot(new SlotItemHandler(((DryingTableBlockEntity) blockEntity).inventory, 2, x_SLOT_3, y_SLOT_3));
        int x_SLOT_4 = 94;
        int y_SLOT_4 = 49;
        this.addSlot(new SlotItemHandler(((DryingTableBlockEntity) blockEntity).inventory, 3, x_SLOT_4, y_SLOT_4));
        int x_SLOT_5 = 62;
        int y_SLOT_5 = 20;
        this.addSlot(new SlotItemHandlerOutput(((DryingTableBlockEntity) blockEntity).inventory, 4, x_SLOT_5, y_SLOT_5));
        int x_SLOT_6 = 144;
        int y_SLOT_6 = 20;
        this.addSlot(new SlotItemHandlerOutput(((DryingTableBlockEntity) blockEntity).inventory, 5, x_SLOT_6, y_SLOT_6));
        int x_SLOT_7 = 62;
        int y_SLOT_7 = 49;
        this.addSlot(new SlotItemHandlerOutput(((DryingTableBlockEntity) blockEntity).inventory, 6, x_SLOT_7, y_SLOT_7));
        int x_SLOT_8 = 144;
        int y_SLOT_8 = 49;
        this.addSlot(new SlotItemHandlerOutput(((DryingTableBlockEntity) blockEntity).inventory, 7, x_SLOT_8, y_SLOT_8));

        addDataSlots(data);
    }

    public boolean canDry() {
        return data.get(8) == 1;
    }

    public boolean isCrafting(int slot) {
        return data.get(slot) > 0;
    }

    public int getScaledArrowProgress(int slot) {
        int progress = this.data.get(slot);
        int maxProgress = this.data.get(slot + 4);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots and the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // Number of slots
    private static final int TE_INVENTORY_SLOT_COUNT = 8;

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.DRYING_TABLE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        ContainerMenuHelper.getPlayerInventorySlots(playerInventory).forEach(this::addSlot);
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        ContainerMenuHelper.getPlayerHotbarSlots(playerInventory).forEach(this::addSlot);
    }
}
