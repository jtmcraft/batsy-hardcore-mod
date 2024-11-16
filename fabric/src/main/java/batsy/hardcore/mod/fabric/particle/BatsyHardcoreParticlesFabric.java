package batsy.hardcore.mod.fabric.particle;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreIdentifierProvider;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BatsyHardcoreParticlesFabric {
    public static final DefaultParticleType REVIVE_PARTICLE = registerParticle("revive_particle", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType defaultParticleType) {
        return Registry.register(Registries.PARTICLE_TYPE, BatsyHardcoreIdentifierProvider.create(name), defaultParticleType);
    }

    public static void registerParticles() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore particles");
    }
}
