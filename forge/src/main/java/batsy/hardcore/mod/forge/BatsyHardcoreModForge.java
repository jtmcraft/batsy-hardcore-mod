package batsy.hardcore.mod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import batsy.hardcore.mod.BatsyHardcoreMod;

@Mod(BatsyHardcoreMod.MOD_ID)
public final class BatsyHardcoreModForge {
    public BatsyHardcoreModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(BatsyHardcoreMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        BatsyHardcoreMod.init();
    }
}
