package batsy.hardcore.mod.fabric.item;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.util.BatsyHardcoreIdentifierProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BatsyHardcoreModItemsFabric {
    public static final Item REVIVE_TOTEM = registerItem(BatsyHardcoreConfiguration.REVIVE_TOTEM, new ReviveTotem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BatsyHardcoreIdentifierProvider.create(name), item);
    }

    public static void registerItems() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore items");
    }
}
