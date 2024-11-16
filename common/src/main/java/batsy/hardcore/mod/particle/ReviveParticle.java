package batsy.hardcore.mod.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ReviveParticle extends SpriteBillboardParticle {
    public ReviveParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider, double dx, double dy, double dz) {
        super(clientWorld, x, y, z, dx, dy, dz);

        velocityMultiplier = 0.5f;
        velocityX = dx;
        velocityY = dy;
        velocityZ = dz;
        scale *= 0.75f;
        maxAge = 10;
        setSpriteForAge(spriteProvider);

        red = 1f;
        green = 1f;
        blue = 1f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double dx, double dy, double dz) {
            return new ReviveParticle(clientWorld, x, y, z, spriteProvider, dx, dy, dz);
        }
    }
}
