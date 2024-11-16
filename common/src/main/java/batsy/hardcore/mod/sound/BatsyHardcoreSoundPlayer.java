package batsy.hardcore.mod.sound;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreSoundPlayer {
    private BatsyHardcoreSoundPlayer() {}

    public static void playBlockSound(@NotNull World world, BlockPos blockPos, SoundEvent soundEvent) {
        world.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 0.5f, 1f);
    }
}
