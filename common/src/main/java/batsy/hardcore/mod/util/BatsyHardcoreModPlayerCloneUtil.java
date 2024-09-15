package batsy.hardcore.mod.util;

import batsy.hardcore.mod.data.ReviveAltarDataFile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreModPlayerCloneUtil {
    private BatsyHardcoreModPlayerCloneUtil() {}

    public static void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive, String configDir) {
        if (!alive) {
            handlePlayerNotAlive(oldPlayer, newPlayer, configDir);
        }
    }

    private static void handlePlayerNotAlive(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, String configDir) {
        if (BatsyHardcorePlayerTagsUtil.isBatsyHardcore(oldPlayer)) {
            handleHardcoreMode(oldPlayer, newPlayer, configDir);
        }
    }

    private static void handleHardcoreMode(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, String configDir) {
        if (BatsyHardcorePlayerTagsUtil.isAltarLoaded(oldPlayer)) {
            handleAltarLoaded(oldPlayer, newPlayer, configDir);
        } else {
            handleAltarNotLoaded(oldPlayer);
        }
    }

    private static void handleAltarNotLoaded(@NotNull ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.sendMessage(Text.literal("Your revive altar is not loaded."));
    }

    private static void handleAltarLoaded(@NotNull ServerPlayerEntity oldPlayer, @NotNull ServerPlayerEntity newPlayer, String configDir) {
        newPlayer.getInventory().clone(oldPlayer.getInventory());
        newPlayer.changeGameMode(GameMode.SPECTATOR);
        BatsyHardcorePlayerTagsUtil.onClone(newPlayer);
        ReviveAltarDataFile.delete(configDir, newPlayer.getUuidAsString());
        newPlayer.sendMessage(Text.literal("Return to your revive altar."));
    }
}
