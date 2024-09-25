package batsy.hardcore.mod.fabric.item;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class BatsyHardcoreItemGroupFabric {
    public static final ItemGroup BATSY_HARDCORE_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            BatsyHardcoreUtil.id("batsy_hardcore_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.batsy_hardcore_group"))
                    .icon(() -> new ItemStack(BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK))
                    .entries((displayContext, entries) -> {
                        entries.add(BatsyHardcoreModItemsFabric.REVIVE_TOTEM_BASIC);
                        entries.add(BatsyHardcoreModItemsFabric.REVIVE_TOTEM_ADVANCED);
                        entries.add(BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK);
                    })
                    .build()
    );

    public static void registerItemGroups() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore item groups");
    }
}
