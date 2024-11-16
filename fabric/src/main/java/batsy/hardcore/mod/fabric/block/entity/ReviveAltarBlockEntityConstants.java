package batsy.hardcore.mod.fabric.block.entity;

public class ReviveAltarBlockEntityConstants {
    public static final int TOTEM_SLOT = 0;
    public static final int INITIAL_LOADING_PROGRESS = 0;
    public static final int MAX_LOADING_PROGRESS = 72;
    public static final int INVENTORY_SIZE = 1;
    
    public static class Properties {
        public static final int SIZE = 3;
        public static final int LOADING_PROGRESS_INDEX = 0;
        public static final int MAX_LOADING_PROGRESS_INDEX = 1;
        public static final int LOADED_INDEX = 2;
        public static final int UNLOADED_VALUE = 0;
        public static final int LOADED_VALUE = 1;
    }

    public static class Particles {
        public static double XZ_OFFSET = 0.25d;
        public static double Y_OFFSET = 0.15d;
        public static float SPEED = 5f;
    }
}
