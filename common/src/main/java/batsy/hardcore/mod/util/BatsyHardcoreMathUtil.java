package batsy.hardcore.mod.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreMathUtil {
    private BatsyHardcoreMathUtil() {}

    public static @NotNull Box getBox(BlockPos blockPos, int radius) {
        return Box.of(Vec3d.ofCenter(blockPos), radius, radius, radius);
    }

    public static @NotNull Box getBox(BlockPos blockPos, double radius) {
        return Box.of(Vec3d.ofCenter(blockPos), radius, radius, radius);
    }

    public static @NotNull Box getBox(BlockPos blockPos, int dx, int dy, int dz) {
        return Box.of(Vec3d.ofCenter(blockPos), dx, dy, dz);
    }

    public static @NotNull Box getBox(BlockPos blockPos, double dx, double dy, double dz) {
        return Box.of(Vec3d.ofCenter(blockPos), dx, dy, dz);
    }
}
