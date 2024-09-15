package batsy.hardcore.mod.util;

import batsy.hardcore.mod.data.ReviveAltarDataFile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreAfterPlayerBlockBreakUtil {
    private BatsyHardcoreAfterPlayerBlockBreakUtil() {}

    public static void afterBlockBreak(@NotNull ServerPlayerEntity serverPlayerEntity, String configDirectory) {
        String playerUuid = serverPlayerEntity.getUuidAsString();

        if (!ReviveAltarDataFile.delete(configDirectory, playerUuid)) {
            serverPlayerEntity.sendMessage(Text.literal("Failed to remove data. Please report."));
        }

        BatsyHardcorePlayerTagsUtil.removeAll(serverPlayerEntity);
        serverPlayerEntity.sendMessage(Text.literal("You have been removed from Batsy Hardcore mode."));
    }
}
