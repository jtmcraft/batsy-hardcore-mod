package batsy.hardcore.mod.fabric.item;

import batsy.hardcore.mod.data.ReviveAltarDataFile;
import batsy.hardcore.mod.fabric.block.custom.ReviveAltarBlockFabric;
import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import batsy.hardcore.mod.util.BatsyHardcoreBlockUtil;
import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SimpleReviveToken extends Item {
    public SimpleReviveToken(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        World world = context.getWorld();
        if (!isServerWorld(world)) {
            return ActionResult.FAIL;
        }

        if (context.getPlayer() instanceof ServerPlayerEntity serverPlayerEntity) {
            if (!isPlayerInHardcoreMode(serverPlayerEntity)) {
                return ActionResult.FAIL;
            }

            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (!isBlockTaggedAsReviveAltar(blockState)) {
                return ActionResult.FAIL;
            }

            if (isAlreadyLoaded(blockState)) {
                return ActionResult.FAIL;
            }

            if (world.getBlockEntity(blockPos) instanceof ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
                return processReviveAltarActivation(context, serverPlayerEntity, world, blockPos, reviveAltarBlockEntityFabric);
            }
        }

        return ActionResult.FAIL;
    }

    private boolean isServerWorld(@NotNull World world) {
        return !world.isClient;
    }

    private boolean isPlayerInHardcoreMode(ServerPlayerEntity serverPlayerEntity) {
        if (BatsyHardcorePlayerTagsUtil.isBatsyHardcore(serverPlayerEntity)) {
            return true;
        }

        serverPlayerEntity.sendMessage(Text.literal("Something has gone wrong. You are not in hardcore mode."));

        return false;
    }

    private boolean isBlockTaggedAsReviveAltar(BlockState blockState) {
        return BatsyHardcoreBlockUtil.isTagged(blockState, "revive_altars");
    }

    private boolean isAlreadyLoaded(@NotNull BlockState blockState) {
        return blockState.get(ReviveAltarBlockFabric.LOADED);
    }

    private ActionResult processReviveAltarActivation(ItemUsageContext context, @NotNull ServerPlayerEntity serverPlayerEntity, World world, BlockPos blockPos, @NotNull ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
        String playerUuid = serverPlayerEntity.getUuidAsString();
        if (!playerUuid.equals(reviveAltarBlockEntityFabric.getOwnerUuid())) {
            serverPlayerEntity.sendMessage(Text.literal("You do not own this revive altar."));

            return ActionResult.FAIL;
        }

        if (updateAndSave(serverPlayerEntity, world, blockPos)) {
            context.getStack().decrement(1);
            return ActionResult.CONSUME;
        }

        return ActionResult.FAIL;
    }

    private boolean updateAndSave(@NotNull ServerPlayerEntity serverPlayerEntity, @NotNull World world, BlockPos blockPos) {
        boolean isDataWrittenSuccessfully = ReviveAltarDataFile.write(String.valueOf(FabricLoader.getInstance().getConfigDir()), serverPlayerEntity.getUuidAsString(), world.getRegistryKey().toString(), blockPos);

        if (isDataWrittenSuccessfully) {
            BatsyHardcorePlayerTagsUtil.setAltarLoaded(serverPlayerEntity);
            world.setBlockState(blockPos, world.getBlockState(blockPos).cycle(ReviveAltarBlockFabric.LOADED), 2);
            serverPlayerEntity.sendMessage(Text.literal("You have activated your revive altar."));
        } else {
            serverPlayerEntity.sendMessage(Text.literal("Failed to save data. Please report."));
        }

        return isDataWrittenSuccessfully;
    }
}
