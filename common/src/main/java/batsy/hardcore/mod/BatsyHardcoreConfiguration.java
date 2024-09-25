package batsy.hardcore.mod;

// TODO move to a configuration file
public final class BatsyHardcoreConfiguration {
    private BatsyHardcoreConfiguration() {}

    public static final String batsyHardcoreModeTag = "batsy_hardcore_mode";
    public static final String batsyHardcoreModeAliveTag = "batsy_hardcore_alive";
    public static final String batsyHardcoreModeKeepInventoryTag = "batsy_hardcore_keepinventory";

    public static final int reviveAltarUnloadedLightLevel = 0;
    public static final int reviveAltarLoadedLightLevel = 1;
    public static int reviveAltarLoadedComparator = 15;
    public static int reviveAltarUnloadedComparator = 0;
    public static final int reviveAltarDetectionBoxDx = 0;
    public static final int reviveAltarDetectionBoxDy = 2;
    public static final int reviveAltarDetectionBoxDz = 0;

    public static final String REVIVE_TOKEN_BASIC = "revive_totem_basic";
    public static final String REVIVE_TOKEN_ADVANCED = "revive_totem_advanced";
    public static final String REVIVE_ALTAR = "revive_altar";
}
