package batsy.hardcore.mod.fabric.client;

import batsy.hardcore.mod.fabric.block.entity.BatsyHardcoreModBlockEntitiesFabric;
import batsy.hardcore.mod.fabric.client.render.ReviveAltarBlockEntityRendererFabric;
import batsy.hardcore.mod.fabric.particle.BatsyHardcoreParticlesFabric;
import batsy.hardcore.mod.fabric.screen.BatsyHardcoreScreenHandlersFabric;
import batsy.hardcore.mod.fabric.screen.ReviveAltarScreen;
import batsy.hardcore.mod.particle.ReviveParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public final class BatsyHardcoreModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(BatsyHardcoreScreenHandlersFabric.REVIVE_ALTAR_SCREEN_HANDLER, ReviveAltarScreen::new);
        BlockEntityRendererFactories.register(BatsyHardcoreModBlockEntitiesFabric.REVIVE_ALTAR_BE, ReviveAltarBlockEntityRendererFabric::new);
        ParticleFactoryRegistry.getInstance().register(BatsyHardcoreParticlesFabric.REVIVE_PARTICLE, ReviveParticle.Factory::new);
    }
}
