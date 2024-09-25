package batsy.hardcore.mod.fabric.block;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.custom.ReviveAltarBlockFabric;
import batsy.hardcore.mod.util.BatsyHardcoreUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BatsyHardcoreModBlocksFabric {
    public static final Block REVIVE_ALTAR_BLOCK = registerBlock(BatsyHardcoreConfiguration.REVIVE_ALTAR,
            new ReviveAltarBlockFabric(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.BLACK)
                            .instrument(Instrument.BASEDRUM)
                            .requiresTool()
                            .hardness(50.0f)
                            .resistance(1200.0f)
                            .luminance(ReviveAltarBlockFabric::getLightLevel)
                            .pistonBehavior(PistonBehavior.IGNORE)
            )
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, BatsyHardcoreUtil.id(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(BatsyHardcoreMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore blocks");
    }
}
