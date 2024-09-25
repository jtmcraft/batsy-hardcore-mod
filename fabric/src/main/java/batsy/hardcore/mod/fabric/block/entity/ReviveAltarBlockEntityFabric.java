package batsy.hardcore.mod.fabric.block.entity;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.fabric.block.custom.ReviveAltarBlockFabric;
import batsy.hardcore.mod.fabric.item.BatsyHardcoreModItemsFabric;
import batsy.hardcore.mod.fabric.particle.BatsyHardcoreParticlesFabric;
import batsy.hardcore.mod.fabric.screen.ReviveAltarScreenHandler;
import batsy.hardcore.mod.fabric.sound.BatsyHardcoreSoundsFabric;
import batsy.hardcore.mod.sound.BatsyHardcoreSoundPlayer;
import batsy.hardcore.mod.stat.BatsyHardcoreModPlayerStats;
import batsy.hardcore.mod.util.BatsyHardcoreMathUtil;
import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviveAltarBlockEntityFabric extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private String ownerUuid;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(ReviveAltarBlockEntityConstants.INVENTORY_SIZE, ItemStack.EMPTY);
    private final PropertyDelegate propertyDelegate;
    private int loadingProgress;
    private int maxLoadingProgress;
    private int loaded;
    private int tmpLoaded;

    private final Map<Item, Integer> itemLoadedValuesMap = Map.of(
            BatsyHardcoreModItemsFabric.REVIVE_TOTEM_BASIC, ReviveAltarBlockEntityConstants.Properties.LOADED_BASIC_VALUE,
            BatsyHardcoreModItemsFabric.REVIVE_TOTEM_ADVANCED, ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE
    );

    public ReviveAltarBlockEntityFabric(BlockPos blockPos, BlockState blockState) {
        super(BatsyHardcoreModBlockEntitiesFabric.REVIVE_ALTAR_BE, blockPos, blockState);

        this.ownerUuid = "";
        this.loaded = ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
        this.tmpLoaded = ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
        this.loadingProgress = ReviveAltarBlockEntityConstants.INITIAL_LOADING_PROGRESS;
        this.maxLoadingProgress = ReviveAltarBlockEntityConstants.MAX_LOADING_PROGRESS;
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ReviveAltarBlockEntityFabric.this.loadingProgress;
                    case 1 -> ReviveAltarBlockEntityFabric.this.maxLoadingProgress;
                    case 2 -> ReviveAltarBlockEntityFabric.this.loaded;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ReviveAltarBlockEntityFabric.this.loadingProgress = value;
                    case 1 -> ReviveAltarBlockEntityFabric.this.maxLoadingProgress = value;
                    case 2 -> ReviveAltarBlockEntityFabric.this.loaded = value;
                }
            }

            @Override
            public int size() {
                return ReviveAltarBlockEntityConstants.Properties.SIZE;
            }
        };
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("revive_altar.owner_uuid", ownerUuid);
        nbt.putInt("revive_altar.loaded", loaded);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        ownerUuid = nbt.getString("revive_altar.owner_uuid");
        loaded = nbt.getInt("revive_altar.loaded");
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    public void tick(@NotNull World world, @NotNull BlockState blockState, ReviveAltarBlockEntityFabric blockEntity) {
        if (world.isClient) {
            return;
        }

        if (isLoaded()) {
            tryToReviveOwner(world, blockState);
        } else {
            tryToLoadAltar(world, blockState, blockEntity);
        }
    }

    private void updateLightLevel(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState blockState) {
        if (!blockState.get(ReviveAltarBlockFabric.LOADED)) {
            world.setBlockState(pos, blockState.cycle(ReviveAltarBlockFabric.LOADED), 2);
            markDirty(world, pos, blockState);
        }
    }

    private void tryToLoadAltar(@NotNull World world, @NotNull BlockState blockState, ReviveAltarBlockEntityFabric blockEntity) {
        if (!canInputItem() || !isItemReviveTotem() || ownerInUniverse() == null) {
            resetLoadingProgress();
            return;
        }

        updateTmpLoaded();
        incrementProgress();
        markDirty();

        if (hasLoaded()) {
            ServerPlayerEntity serverPlayerEntity = ownerInUniverse();
            if (serverPlayerEntity != null) {
                handleLoadedComplete(serverPlayerEntity, world, blockState, blockEntity);
            }
            resetLoadingProgress();
        }
    }

    private List<ServerPlayerEntity> isWorldAndServerReady(World world, MinecraftServer server) {
        return world != null && server != null ? server.getPlayerManager().getPlayerList() : null;
    }

    private ServerPlayerEntity findOwnerEntity(@NotNull List<ServerPlayerEntity> serverPlayerEntities) {
        return serverPlayerEntities.stream().filter(e -> e.getUuidAsString().equals(ownerUuid)).findFirst().orElse(null);
    }

    private @Nullable ServerPlayerEntity ownerInUniverse() {
        if (ownerUuid == null) {
            return null;
        }

        World world = getWorld();
        MinecraftServer server = world != null ? world.getServer() : null;

        List<ServerPlayerEntity> serverPlayerEntities = isWorldAndServerReady(world, server);
        return serverPlayerEntities != null ? findOwnerEntity(serverPlayerEntities) : null;
    }

    private void tryToReviveOwner(@NotNull World world, @NotNull BlockState blockState) {
        BlockPos blockPos = getPos();
        ServerPlayerEntity serverPlayerEntity = getReviveAltarOwnerIfNearby(world, blockPos);
        if (serverPlayerEntity != null) {
            reviveOwner(serverPlayerEntity, world, blockPos, blockState);
        }
    }

    private boolean canInputItem() {
        return !isLoaded();
    }

    private boolean isItemReviveTotem() {
        return (getStack(ReviveAltarBlockEntityConstants.TOTEM_SLOT).getItem() == BatsyHardcoreModItemsFabric.REVIVE_TOTEM_BASIC
                || getStack(ReviveAltarBlockEntityConstants.TOTEM_SLOT).getItem() == BatsyHardcoreModItemsFabric.REVIVE_TOTEM_ADVANCED)
                && getStack(ReviveAltarBlockEntityConstants.TOTEM_SLOT).getCount() == 1;
    }

    private void updateTmpLoaded() {
        Item currentItem = getStack(ReviveAltarBlockEntityConstants.TOTEM_SLOT).getItem();
        tmpLoaded = getLoadedValue(currentItem);
    }

    private int getLoadedValue(Item totemSlotItem) {
        return getLoadedValueBasedOnItem(totemSlotItem);
    }

    private int getLoadedValueBasedOnItem(Item item) {
        return itemLoadedValuesMap.getOrDefault(item, ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE);
    }

    private void incrementProgress() {
        loadingProgress += 1;
    }

    private boolean hasLoaded() {
        return loadingProgress >= maxLoadingProgress;
    }

    private void handleLoadedComplete(@NotNull ServerPlayerEntity serverPlayerEntity, @NotNull World world, @NotNull BlockState blockState, @NotNull ReviveAltarBlockEntityFabric blockEntity) {
        if (tmpLoaded == ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE) {
            throw new IllegalStateException("Illegal tmpLoaded");
        }

        BlockPos pos = blockEntity.getPos();
        updateLightLevel(world, pos, blockState);
        playAltarLoadedSound(world, pos);
        removeStack(ReviveAltarBlockEntityConstants.TOTEM_SLOT);
        loaded = tmpLoaded;
        setOwnerSpawn(serverPlayerEntity, world, pos);

        if (loaded == ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE) {
            BatsyHardcorePlayerTagsUtil.setKeepInventory(serverPlayerEntity);
        } else {
            BatsyHardcorePlayerTagsUtil.removeKeepInventory(serverPlayerEntity);
        }
    }

    private void playAltarLoadedSound(@NotNull World world, BlockPos pos) {
        SoundEvent soundEvent = (tmpLoaded == ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE)
                ? BatsyHardcoreSoundsFabric.ADVANCED_REVIVAL_LOAD_COMPLETE_SE
                : BatsyHardcoreSoundsFabric.BASIC_REVIVAL_LOAD_COMPLETE_SE;
        BatsyHardcoreSoundPlayer.playBlockSound(world, pos, soundEvent);
    }

    private void playAltarRespawnSound(@NotNull World world, BlockPos blockPos) {
        SoundEvent soundEvent = (loaded == ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE)
                ? BatsyHardcoreSoundsFabric.ADVANCED_REVIVAL_LOAD_COMPLETE_SE
                : BatsyHardcoreSoundsFabric.BASIC_REVIVAL_LOAD_COMPLETE_SE;
        BatsyHardcoreSoundPlayer.playBlockSound(world, blockPos, soundEvent);
    }

    private void playAltarRespawnParticles(@NotNull ServerWorld world, BlockPos blockPos) {
        DefaultParticleType particle = (loaded == ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE)
                ? BatsyHardcoreParticlesFabric.ADVANCED_REVIVE_PARTICLE
                : BatsyHardcoreParticlesFabric.BASIC_REVIVE_PARTICLE;
        for (int i = 0; i < 20; i++) {
            double xCoordinate = calculateCoordinate(blockPos.getX(), i);
            double yCoordinate = blockPos.getY() + 1;
            double zCoordinate = calculateCoordinate(blockPos.getZ(), i);

            world.spawnParticles(particle, xCoordinate, yCoordinate, zCoordinate, 2, ReviveAltarBlockEntityConstants.Particles.XZ_OFFSET, ReviveAltarBlockEntityConstants.Particles.Y_OFFSET, ReviveAltarBlockEntityConstants.Particles.XZ_OFFSET, ReviveAltarBlockEntityConstants.Particles.SPEED);
        }
    }

    private double calculateCoordinate(int coordinate, int index) {
        return coordinate + 0.5d + Math.cos(index * 18) * ReviveAltarBlockEntityConstants.Particles.XZ_OFFSET;
    }

    private void setOwnerSpawn(@NotNull ServerPlayerEntity serverPlayerEntity, @NotNull World world, @NotNull BlockPos spawnPos) {
        BlockPos adjustedSpawnPos = new BlockPos(spawnPos.getX(), spawnPos.getY() + 1, spawnPos.getZ());
        setSpawnForPlayer(serverPlayerEntity, world, adjustedSpawnPos);
        serverPlayerEntity.sendMessage(Text.literal("Your revive altar has been loaded. You will respawn here when you die."));
    }

    private List<ServerPlayerEntity> getServerPlayerEntities(@NotNull World world) {
        MinecraftServer server = world.getServer();

        if (server != null) {
            return server.getPlayerManager().getPlayerList();
        }

        return new ArrayList<>();
    }

    private void resetLoadingProgress() {
        loadingProgress = 0;
        tmpLoaded = ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
        markDirty();
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public boolean isLoaded() {
        return loaded > ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
    }

    public int getLoaded() {
        return loaded;
    }

    private @Nullable ServerPlayerEntity getReviveAltarOwnerIfNearby(@NotNull World level, @NotNull BlockPos blockPos) {
        Box detectionBox = BatsyHardcoreMathUtil.getBox(blockPos,
                BatsyHardcoreConfiguration.reviveAltarDetectionBoxDx,
                BatsyHardcoreConfiguration.reviveAltarDetectionBoxDy,
                BatsyHardcoreConfiguration.reviveAltarDetectionBoxDz);
        List<PlayerEntity> playerEntities = level.getEntitiesByType(EntityType.PLAYER, detectionBox, Entity::isAlive);

        for (PlayerEntity playerEntity : playerEntities) {
            if (isPlayerOwner(playerEntity)) {
                return (ServerPlayerEntity) playerEntity;
            }
        }

        return null;
    }

    private boolean isPlayerOwner(@NotNull PlayerEntity playerEntity) {
        return playerEntity.getUuidAsString().equals(ownerUuid);
    }

    private void reviveOwner(@NotNull ServerPlayerEntity serverPlayerEntity, World world, BlockPos blockPos, BlockState blockState) {
        if (serverPlayerEntity.isSpectator()) {
            playAltarRespawnSound(world, blockPos);
            playAltarRespawnParticles((ServerWorld) world, blockPos);
            BlockState cycledBlockState = blockState.cycle(ReviveAltarBlockFabric.LOADED);
            world.setBlockState(blockPos, cycledBlockState);
            serverPlayerEntity.resetStat(BatsyHardcoreModPlayerStats.BATSY_HARDCORE_TIME_STAT);

            if (loaded == ReviveAltarBlockEntityConstants.Properties.LOADED_BASIC_VALUE) {
                serverPlayerEntity.getInventory().clear();
                serverPlayerEntity.sendMessage(Text.literal("You have lost all of your stuff, but at least you're alive."));
            }

            serverPlayerEntity.changeGameMode(GameMode.SURVIVAL);
            BatsyHardcorePlayerTagsUtil.addAll(serverPlayerEntity);
            loaded = ReviveAltarBlockEntityConstants.Properties.UNLOADED_VALUE;
            tmpLoaded = loaded;
            serverPlayerEntity.sendMessage(Text.literal("Welcome back to the realm of the living. Your revive altar has spent all of its magic bringing you back; consider reloading it as soon as possible. Also, you no longer have a spawn point set."));
            setSpawnForPlayer(serverPlayerEntity, world, null);
            BatsyHardcorePlayerTagsUtil.removeKeepInventory(serverPlayerEntity);
            markDirty(world, blockPos, blockState);
        }
    }

    private void setSpawnForPlayer(@NotNull ServerPlayerEntity serverPlayerEntity, @NotNull World world, BlockPos blockPos) {
        serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), blockPos, 0f, true, true);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, @NotNull PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Revive Altar");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (isLoaded()) {
            return null;
        }

        return new ReviveAltarScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canPlayerUse(@NotNull PlayerEntity player) {
        MinecraftServer server = player.getServer();

        if (server == null) {
            player.sendMessage(Text.literal("There's a tear in the timespace continuum."));
            return false;
        }

        return isAltarOwnerOnline(player, server);
    }

    private boolean isAltarOwnerOnline(PlayerEntity player, @NotNull MinecraftServer server) {
        boolean can = server.getPlayerManager().getPlayerList().stream()
                .anyMatch(serverPlayerEntity -> serverPlayerEntity.getUuidAsString().equals(ownerUuid));

        if (!can) {
            player.sendMessage(Text.literal("The owner of this altar must be somewhere in the timespace continuum."));
        }

        return can;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
