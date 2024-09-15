package batsy.hardcore.mod.fabric.event;

import batsy.hardcore.mod.fabric.BatsyHardcoreModFabric;
import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import batsy.hardcore.mod.util.BatsyHardcoreAfterPlayerBlockBreakUtil;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BatsyHardcoreModAfterPlayerBlockBreakHandler implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            if (blockEntity instanceof ReviveAltarBlockEntityFabric) {
                if (serverPlayerEntity.isCreativeLevelTwoOp()) {
                    // TODO handle this case
                    return;
                }
                BatsyHardcoreAfterPlayerBlockBreakUtil.afterBlockBreak(serverPlayerEntity, BatsyHardcoreModFabric.getDataDirectory());
            }
        }
    }
}
