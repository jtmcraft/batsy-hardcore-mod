package batsy.hardcore.mod.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreAfterPlayerBlockBreakUtil {
    private BatsyHardcoreAfterPlayerBlockBreakUtil() {}

    public static void afterBlockBreak(@NotNull ServerPlayerEntity serverPlayerEntity) {
        BatsyHardcorePlayerTagsUtil.removeAll(serverPlayerEntity);
        serverPlayerEntity.sendMessage(Text.literal("You have been removed from Batsy Hardcore mode."));
    }
}
