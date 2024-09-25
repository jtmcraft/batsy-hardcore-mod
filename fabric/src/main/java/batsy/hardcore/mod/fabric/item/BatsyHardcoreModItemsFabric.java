package batsy.hardcore.mod.fabric.item;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BatsyHardcoreModItemsFabric {
    public static final Item REVIVE_TOTEM_BASIC = registerItem(BatsyHardcoreConfiguration.REVIVE_TOKEN_BASIC, new ReviveTotemBasic(new FabricItemSettings()));
    public static final Item REVIVE_TOTEM_ADVANCED = registerItem(BatsyHardcoreConfiguration.REVIVE_TOKEN_ADVANCED, new ReviveTotemAdvanced(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BatsyHardcoreUtil.id(name), item);
    }

    public static void registerItems() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore items");
    }
}
