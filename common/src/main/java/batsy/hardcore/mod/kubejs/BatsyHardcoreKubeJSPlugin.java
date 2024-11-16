package batsy.hardcore.mod.kubejs;

import batsy.hardcore.mod.kubejs.api.BatsyHardcoreConfigurator;
import batsy.hardcore.mod.kubejs.api.PlayerRevivingEventJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import org.jetbrains.annotations.NotNull;

public class BatsyHardcoreKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerClasses(ScriptType type, @NotNull ClassFilter filter) {
        filter.allow("batsy.hardcore.mod.kubejs.api");
    }

    @Override
    public void registerBindings(@NotNull BindingsEvent event) {
        event.add("BatsyHardcoreConfigurator", BatsyHardcoreConfigurator.class);
        event.add("playerReviving", PlayerRevivingEventJS.class);
    }

    @Override
    public void registerEvents() {
        BatsyHardcoreEventsKubeJS.EVENT_GROUP.register();
    }
}
