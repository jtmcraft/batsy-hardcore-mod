package batsy.hardcore.mod.fabric.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class BatsyHardcoreModEventHandlingFabric {
    public static void registerEvents() {
        PlayerBlockBreakEvents.AFTER.register(new BatsyHardcoreModAfterPlayerBlockBreakHandler());
        PlayerBlockBreakEvents.BEFORE.register(new BatsyHardcoreModBeforePlayerBlockBreakHandler());
    }
}
