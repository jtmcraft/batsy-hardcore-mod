package batsy.hardcore.mod.fabric.particle;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BatsyHardcoreParticlesFabric {
    public static final DefaultParticleType ADVANCED_REVIVE_PARTICLE = registerParticle("advanced_revival_particle", FabricParticleTypes.simple());
    public static final DefaultParticleType BASIC_REVIVE_PARTICLE = registerParticle("basic_revival_particle", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType defaultParticleType) {
        return Registry.register(Registries.PARTICLE_TYPE, BatsyHardcoreUtil.id(name), defaultParticleType);
    }

    public static void registerParticles() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore particles");
    }
}
