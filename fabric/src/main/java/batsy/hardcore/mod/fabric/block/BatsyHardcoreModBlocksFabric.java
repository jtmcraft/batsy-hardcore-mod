package batsy.hardcore.mod.fabric.block;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.custom.ReviveAltarBlockFabric;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BatsyHardcoreModBlocksFabric {
    public static final Block REVIVE_ALTAR_BLOCK = registerBlock("revive_altar",
            new ReviveAltarBlockFabric(FabricBlockSettings.copyOf(Blocks.BLACKSTONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(BatsyHardcoreMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(BatsyHardcoreMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore blocks");
    }
}
