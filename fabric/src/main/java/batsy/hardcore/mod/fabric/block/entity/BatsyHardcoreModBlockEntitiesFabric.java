package batsy.hardcore.mod.fabric.block.entity;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import batsy.hardcore.mod.util.BatsyHardcoreIdentifierProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BatsyHardcoreModBlockEntitiesFabric {
    public static final BlockEntityType<ReviveAltarBlockEntityFabric> REVIVE_ALTAR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                            BatsyHardcoreIdentifierProvider.create("revive_altar_block_entity"),
                            FabricBlockEntityTypeBuilder.create(ReviveAltarBlockEntityFabric::new,
                            BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK).build(null));

    public static void registerBlockEntities() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore block entities");
    }
}
