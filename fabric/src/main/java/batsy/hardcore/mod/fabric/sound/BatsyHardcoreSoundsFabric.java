package batsy.hardcore.mod.fabric.sound;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class BatsyHardcoreSoundsFabric {
    public static final SoundEvent ADVANCED_REVIVAL_LOAD_COMPLETE_SE = registerSoundEvent("revive_advanced_complete");
    public static final SoundEvent BASIC_REVIVAL_LOAD_COMPLETE_SE = registerSoundEvent("revive_basic_complete");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = BatsyHardcoreUtil.id(name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void registerSounds() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore sounds");
    }
}
