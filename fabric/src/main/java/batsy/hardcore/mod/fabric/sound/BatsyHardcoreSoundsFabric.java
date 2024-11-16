package batsy.hardcore.mod.fabric.sound;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreIdentifierProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class BatsyHardcoreSoundsFabric {
    public static final SoundEvent REVIVE_PLAYER_SE = registerSoundEvent("revive_player");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = BatsyHardcoreIdentifierProvider.create(name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void registerSounds() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore sounds");
    }
}
