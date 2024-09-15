package batsy.hardcore.mod.util;

import batsy.hardcore.mod.BatsyHardcoreMod;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreUtil {
    private BatsyHardcoreUtil() {}

    public static @NotNull Identifier id(String name) {
        return new Identifier(BatsyHardcoreMod.MOD_ID, name);
    }
}
