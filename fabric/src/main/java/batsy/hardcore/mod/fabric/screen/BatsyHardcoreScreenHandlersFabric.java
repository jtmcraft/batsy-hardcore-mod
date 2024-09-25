package batsy.hardcore.mod.fabric.screen;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class BatsyHardcoreScreenHandlersFabric {
    public static final ScreenHandlerType<ReviveAltarScreenHandler> REVIVE_ALTAR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    BatsyHardcoreUtil.id("revive_altar_screen_handler"),
                    new ExtendedScreenHandlerType<>(ReviveAltarScreenHandler::new));


    public static void registerScreenHandlers() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore screen handlers");
    }
}
