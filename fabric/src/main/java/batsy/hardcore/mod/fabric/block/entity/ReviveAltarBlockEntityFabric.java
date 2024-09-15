package batsy.hardcore.mod.fabric.block.entity;

import batsy.hardcore.mod.fabric.block.custom.ReviveAltarBlockFabric;
import batsy.hardcore.mod.stat.BatsyHardcoreModPlayerStats;
import batsy.hardcore.mod.util.BatsyHardcoreMathUtil;
import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReviveAltarBlockEntityFabric extends BlockEntity {
    private String ownerUuid;

    public ReviveAltarBlockEntityFabric(BlockPos blockPos, BlockState blockState) {
        super(BatsyHardcoreModBlockEntitiesFabric.REVIVE_ALTAR_BE, blockPos, blockState);
        this.ownerUuid = "";
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        nbt.putString("revive_altar.owner_uuid", ownerUuid);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        ownerUuid = nbt.getString("revive_altar.owner_uuid");
        super.readNbt(nbt);
    }

    public void tick(@NotNull World world, BlockPos blockPos, BlockState blockState) {
        if (world.isClient) {
            return;
        }

        if (!isReviveAltarLoaded(blockState)) {
            return;
        }

        ServerPlayerEntity reviveAltarOwner = getReviveAltarOwnerIfNearby(world, blockPos);
        if (reviveAltarOwner != null) {
            reviveOwner(reviveAltarOwner, world, blockPos, blockState);
        }
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
        markDirty();
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    private @Nullable ServerPlayerEntity getReviveAltarOwnerIfNearby(@NotNull World level, @NotNull BlockPos blockPos) {
        List<PlayerEntity> playerEntities = level.getEntitiesByType(EntityType.PLAYER, BatsyHardcoreMathUtil.getBox(blockPos, 0, 2, 0), Entity::isAlive);
        for (PlayerEntity playerEntity : playerEntities) {
            if (isPlayerOwner(playerEntity)) {
                return (ServerPlayerEntity) playerEntity;
            }
        }
        return null;
    }

    private boolean isReviveAltarLoaded(@NotNull BlockState blockState) {
        return blockState.get(ReviveAltarBlockFabric.LOADED);
    }

    private boolean isPlayerOwner(@NotNull PlayerEntity player) {
        return player.getUuidAsString().equals(ownerUuid);
    }

    private void reviveOwner(@NotNull ServerPlayerEntity player, World level, BlockPos blockPos, BlockState blockState) {
        if (player.isSpectator()) {
            level.setBlockState(blockPos, blockState.cycle(ReviveAltarBlockFabric.LOADED));
            player.resetStat(BatsyHardcoreModPlayerStats.BATSY_HARDCORE_TIME_STAT);
            player.changeGameMode(GameMode.SURVIVAL);
            BatsyHardcorePlayerTagsUtil.addAll(player);
        }
    }
}
