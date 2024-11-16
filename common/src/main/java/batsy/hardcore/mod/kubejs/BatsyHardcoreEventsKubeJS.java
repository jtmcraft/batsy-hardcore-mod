package batsy.hardcore.mod.kubejs;

import batsy.hardcore.mod.kubejs.api.PlayerRevivingEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface BatsyHardcoreEventsKubeJS {
    EventGroup EVENT_GROUP = EventGroup.of("BatsyHardcoreEvents");
    EventHandler PLAYER_REVIVING = EVENT_GROUP.server("playerReviving", () -> PlayerRevivingEventJS.class);
}
