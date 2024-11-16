package batsy.hardcore.mod.fabric.datagen;

import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class BatsyHardcoreModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public BatsyHardcoreModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK);
    }
}
