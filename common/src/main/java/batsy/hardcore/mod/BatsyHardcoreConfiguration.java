package batsy.hardcore.mod;

public final class BatsyHardcoreConfiguration {
    private BatsyHardcoreConfiguration() {}

    public static final String batsyHardcoreModeTag = "batsy_hardcore_mode";
    public static final String batsyHardcoreModeAliveTag = "batsy_hardcore_alive";
    public static final String batsyHardcoreModeAltarLoaded = "batsy_hardcore_loaded";
    public static final String batsyHardcoreModeKeepInventoryTag = "batsy_hardcore_keepinventory";

    public static final int reviveAltarUnloadedLightLevel = 0;
    public static final int reviveAltarLoadedLightLevel = 1;
    public static final int reviveAltarDetectionBoxDx = 0;
    public static final int reviveAltarDetectionBoxDy = 2;
    public static final int reviveAltarDetectionBoxDz = 0;

    public static final String REVIVE_TOTEM = "revive_totem";
    public static final String REVIVE_ALTAR = "revive_altar";

    public static boolean SHOW_REVIVE_PARTICLES = false;
    public static boolean PLAY_REVIVE_SOUND = false;
}
