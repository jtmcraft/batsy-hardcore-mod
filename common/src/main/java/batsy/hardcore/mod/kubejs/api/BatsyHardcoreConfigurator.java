package batsy.hardcore.mod.kubejs.api;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;

public class BatsyHardcoreConfigurator {
    public static void setPlayReviveSound(boolean shouldPlaySound) {
        BatsyHardcoreConfiguration.PLAY_REVIVE_SOUND = shouldPlaySound;
    }

    public static boolean getPlayReviveSound() {
        return BatsyHardcoreConfiguration.PLAY_REVIVE_SOUND;
    }

    public static void setShowReviveParticles(boolean shouldShowParticles) {
        BatsyHardcoreConfiguration.SHOW_REVIVE_PARTICLES = shouldShowParticles;
    }

    public static boolean getShowReviveParticles() {
        return BatsyHardcoreConfiguration.SHOW_REVIVE_PARTICLES;
    }
}
