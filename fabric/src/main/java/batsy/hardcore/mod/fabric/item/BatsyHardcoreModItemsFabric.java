package batsy.hardcore.mod.fabric.item;

import batsy.hardcore.mod.BatsyHardcoreMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BatsyHardcoreModItemsFabric {
    public static final Item SIMPLE_REVIVE_TOTEM = registerItem("simple_revive_totem", new SimpleReviveToken(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BatsyHardcoreMod.MOD_ID, name), item);
    }

    public static void registerItems() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore items");
    }
}
