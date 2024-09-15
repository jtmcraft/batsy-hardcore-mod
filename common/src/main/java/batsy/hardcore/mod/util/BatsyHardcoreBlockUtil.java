package batsy.hardcore.mod.util;

import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreBlockUtil {
    private BatsyHardcoreBlockUtil() {}

    public static boolean isTagged(@NotNull BlockState blockState, String name) {
        return blockState.isIn(TagKey.of(RegistryKeys.BLOCK, BatsyHardcoreUtil.id(name)));
    }
}
