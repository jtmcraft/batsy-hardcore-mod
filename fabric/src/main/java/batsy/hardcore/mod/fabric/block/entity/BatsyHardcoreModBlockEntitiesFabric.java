package batsy.hardcore.mod.fabric.block.entity;

import batsy.hardcore.mod.BatsyHardcoreMod;
import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BatsyHardcoreModBlockEntitiesFabric {
    public static final BlockEntityType<ReviveAltarBlockEntityFabric> REVIVE_ALTAR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(BatsyHardcoreMod.MOD_ID,
                            "revive_altar_block_entity"),
                            FabricBlockEntityTypeBuilder.create(ReviveAltarBlockEntityFabric::new,
                            BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK).build(null));

    public static void registerBlockEntities() {
        BatsyHardcoreMod.LOGGER.info("Registering Batsy Hardcore block entities");
    }
}
