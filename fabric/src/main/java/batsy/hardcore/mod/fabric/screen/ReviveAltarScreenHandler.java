package batsy.hardcore.mod.fabric.screen;

import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityConstants;
import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class ReviveAltarScreenHandler extends ScreenHandler {
    public final ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric;
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    private static final int HOTBAR_SIZE = 9;
    private static final int HOTBAR_X_POSITION = 8;
    private static final int HOTBAR_Y_POSITION = 142;
    private static final int HOTBAR_ITEM_DISTANCE = 18;
    private static final int INVENTORY_ROWS = 3;
    private static final int INVENTORY_COLUMNS = 9;
    private static final int INVENTORY_X_POSITION = 8;
    private static final int INVENTORY_Y_POSITION = 84;
    private static final int INVENTORY_ITEM_DISTANCE = 18;
    private static final int PROGRESS_ARROW_WIDTH = 26;

    public ReviveAltarScreenHandler(int syncId, PlayerInventory playerInventory, @NotNull PacketByteBuf packetByteBuf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(packetByteBuf.readBlockPos()),
                new ArrayPropertyDelegate(ReviveAltarBlockEntityConstants.Properties.SIZE));
    }

    public ReviveAltarScreenHandler(int syncId, PlayerInventory playerInventory,
                                    BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(BatsyHardcoreScreenHandlersFabric.REVIVE_ALTAR_SCREEN_HANDLER, syncId);

        ReviveAltarBlockEntityFabric reviveAltarBlockEntity = (ReviveAltarBlockEntityFabric) blockEntity;
        checkSize(reviveAltarBlockEntity, ReviveAltarBlockEntityConstants.INVENTORY_SIZE);

        this.inventory = reviveAltarBlockEntity;
        this.propertyDelegate = propertyDelegate;
        this.reviveAltarBlockEntityFabric = reviveAltarBlockEntity;

        addSlots();
        addPlayerInventories(playerInventory);
        addProperties(this.propertyDelegate);
    }

    private void addPlayerInventories(PlayerInventory playerInventory) {
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addSlots() {
        addSlot(new Slot(inventory, ReviveAltarBlockEntityConstants.TOTEM_SLOT, 80, 11));
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < HOTBAR_SIZE; ++i) {
            int xPosition = HOTBAR_X_POSITION + i * HOTBAR_ITEM_DISTANCE;
            addSlot(new Slot(playerInventory, i, xPosition, HOTBAR_Y_POSITION));
        }
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < INVENTORY_ROWS; ++i) {
            for (int j = 0; j < INVENTORY_COLUMNS; ++j) {
                int slotIndex = j + i * INVENTORY_COLUMNS + INVENTORY_COLUMNS;
                int xPosition = INVENTORY_X_POSITION + j * INVENTORY_ITEM_DISTANCE;
                int yPosition = INVENTORY_Y_POSITION + i * INVENTORY_ITEM_DISTANCE;
                addSlot(new Slot(playerInventory, slotIndex, xPosition, yPosition));
            }
        }
    }

    public boolean isLoading() {
        return propertyDelegate.get(ReviveAltarBlockEntityConstants.Properties.LOADING_PROGRESS_INDEX) > 0;
    }

    public boolean isLoaded() {
        return getLoaded() > ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
    }

    public int getLoaded() {
        return propertyDelegate.get(ReviveAltarBlockEntityConstants.Properties.LOADED_INDEX);
    }

    public int getScaledLoadingProgress() {
        int progress = propertyDelegate.get(ReviveAltarBlockEntityConstants.Properties.LOADING_PROGRESS_INDEX);
        int maxProgress = propertyDelegate.get(ReviveAltarBlockEntityConstants.Properties.MAX_LOADING_PROGRESS_INDEX);

        if (maxProgress == 0 || progress == 0) {
            return 0;
        }

        int scaledProgress = progress * PROGRESS_ARROW_WIDTH;
        return scaledProgress / maxProgress;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int inventorySlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(inventorySlot);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = getSlotStackIfAny(originalStack, inventorySlot);

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private ItemStack getSlotStackIfAny(@NotNull ItemStack originalStack, int inventorySlot) {
        ItemStack newStack = originalStack.copy();

        if (!insertIntoInventory(originalStack, inventorySlot)) {
            return ItemStack.EMPTY;
        }

        return newStack;
    }

    private boolean insertIntoInventory(ItemStack originalStack, int inventorySlot) {
        if (inventorySlot < inventory.size()) {
            return insertItem(originalStack, inventory.size(), slots.size(), true);
        }

        return insertItem(originalStack, 0, inventory.size(), false);
    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return !isLoaded() && inventory.canPlayerUse(playerEntity);
    }
}
