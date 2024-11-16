package batsy.hardcore.mod.util;

import batsy.hardcore.mod.BatsyHardcoreMod;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreIdentifierProvider {
    private BatsyHardcoreIdentifierProvider() {}

    public static @NotNull Identifier create(String name) {
        return new Identifier(BatsyHardcoreMod.MOD_ID, name);
    }
}
