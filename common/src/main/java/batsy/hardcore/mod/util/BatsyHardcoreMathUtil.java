package batsy.hardcore.mod.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreMathUtil {
    private BatsyHardcoreMathUtil() {}

    public static @NotNull Box getBox(BlockPos blockPos, int dx, int dy, int dz) {
        return Box.of(Vec3d.ofCenter(blockPos), dx, dy, dz);
    }
}
