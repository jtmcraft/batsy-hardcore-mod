package batsy.hardcore.mod.fabric.event;

import batsy.hardcore.mod.fabric.BatsyHardcoreModFabric;
import batsy.hardcore.mod.util.BatsyHardcoreModPlayerCloneUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;

public class BatsyHardcoreModPlayerCloneCallback implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        BatsyHardcoreModPlayerCloneUtil.copyFromPlayer(oldPlayer, newPlayer, alive, BatsyHardcoreModFabric.getDataDirectory());
    }
}
