package batsy.hardcore.mod.event;

import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.minecraft.server.network.ServerPlayerEntity;

public class BatsyHardcorePlayerDeathEvent implements BatsyHardcoreEvent {
    private final ServerPlayerEntity serverPlayerEntity;

    public BatsyHardcorePlayerDeathEvent(ServerPlayerEntity serverPlayerEntity) {
        this.serverPlayerEntity = serverPlayerEntity;
    }

    @Override
    public void callEvent() {
        BatsyHardcorePlayerTagsUtil.removeAlive(serverPlayerEntity);
    }
}
