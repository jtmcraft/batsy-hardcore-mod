package batsy.hardcore.mod.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreModPlayerCloneUtil {
    private BatsyHardcoreModPlayerCloneUtil() {}

    public static void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive, String configDir) {
        if (!alive && BatsyHardcorePlayerTagsUtil.isBatsyHardcore(oldPlayer)) {
            handleHardcoreMode(oldPlayer, newPlayer, configDir);
        }
    }

    private static void handleHardcoreMode(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, String configDir) {
        if (BatsyHardcorePlayerTagsUtil.isAltarLoaded(oldPlayer)) {
            handleAltarLoaded(oldPlayer, newPlayer, configDir);
        }
    }

    private static void handleAltarLoaded(@NotNull ServerPlayerEntity oldPlayer, @NotNull ServerPlayerEntity newPlayer, String configDir) {
        newPlayer.getInventory().clone(oldPlayer.getInventory());
        newPlayer.changeGameMode(GameMode.SPECTATOR);
        BatsyHardcorePlayerTagsUtil.onClone(newPlayer);
    }
}
