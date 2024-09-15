package batsy.hardcore.mod.fabric.event;

import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import batsy.hardcore.mod.util.BatsyHardcoreBeforePlayerBlockBreakUtil;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BatsyHardcoreModBeforePlayerBlockBreakHandler implements PlayerBlockBreakEvents.Before {
    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity == null) {
            return true;
        }

        if (player instanceof ServerPlayerEntity serverPlayerEntity && blockEntity instanceof ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
            return BatsyHardcoreBeforePlayerBlockBreakUtil.beforeBlockBreak(serverPlayerEntity, reviveAltarBlockEntityFabric.getOwnerUuid());
        }

        return true;
    }
}
