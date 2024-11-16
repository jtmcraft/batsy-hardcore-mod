package batsy.hardcore.mod.util;

import batsy.hardcore.mod.BatsyHardcoreMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreBeforePlayerBlockBreakUtil {
    private BatsyHardcoreBeforePlayerBlockBreakUtil() {}

    public static boolean beforeReviveAltarBreak(@NotNull ServerPlayerEntity serverPlayerEntity, String altarOwnerUuid) {
        String playerUuid = serverPlayerEntity.getUuidAsString();
        return playerUuid.equals(altarOwnerUuid) ||
                handleNonOwnerBreakingReviveAltar(serverPlayerEntity, playerUuid, altarOwnerUuid);
    }

    private static boolean handleNonOwnerBreakingReviveAltar(@NotNull ServerPlayerEntity serverPlayerEntity, String playerUuid, String altarOwnerUuid) {
        log(playerUuid, altarOwnerUuid);

        if (serverPlayerEntity.isCreative()) {
            BatsyHardcoreMod.LOGGER.info(" and did so because they're in creative mode");
            return true;
        }

        sendMessage(serverPlayerEntity);
        return false;
    }

    private static void log(String playerUuid, String altarOwnerUuid) {
        String logMessage = String.format("player %s tried to break revive altar of %s", playerUuid, altarOwnerUuid);
        BatsyHardcoreMod.LOGGER.info(logMessage);
    }

    private static void sendMessage(@NotNull ServerPlayerEntity serverPlayerEntity) {
        Text errorMessage = Text.literal("You do not own this revive altar.");
        serverPlayerEntity.sendMessage(errorMessage);
    }
}
