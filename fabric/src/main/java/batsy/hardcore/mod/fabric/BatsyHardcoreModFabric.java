package batsy.hardcore.mod.fabric;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import batsy.hardcore.mod.fabric.block.entity.BatsyHardcoreModBlockEntitiesFabric;
import batsy.hardcore.mod.fabric.event.BatsyHardcoreModEventHandlingFabric;
import batsy.hardcore.mod.fabric.item.BatsyHardcoreItemGroupFabric;
import batsy.hardcore.mod.fabric.item.BatsyHardcoreModItemsFabric;
import batsy.hardcore.mod.fabric.particle.BatsyHardcoreParticlesFabric;
import batsy.hardcore.mod.fabric.screen.BatsyHardcoreScreenHandlersFabric;
import batsy.hardcore.mod.fabric.sound.BatsyHardcoreSoundsFabric;
import batsy.hardcore.mod.stat.BatsyHardcoreModPlayerStats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

public final class BatsyHardcoreModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BatsyHardcoreMod.init();
        BatsyHardcoreItemGroupFabric.registerItemGroups();
        BatsyHardcoreModPlayerStats.registerPlayerStats();
        BatsyHardcoreModItemsFabric.registerItems();
        BatsyHardcoreModBlocksFabric.registerBlocks();
        BatsyHardcoreModBlockEntitiesFabric.registerBlockEntities();
        BatsyHardcoreScreenHandlersFabric.registerScreenHandlers();
        BatsyHardcoreModEventHandlingFabric.registerEvents();
        BatsyHardcoreSoundsFabric.registerSounds();
        BatsyHardcoreParticlesFabric.registerParticles();
    }

    public static @NotNull String getReviveAltarDataDirectory() {
        return FabricLoader.getInstance()
                .getConfigDir()
                .resolve("batsy_hardcore_mod")
                .toString();
    }
}
