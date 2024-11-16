package batsy.hardcore.mod.fabric.datagen;

import batsy.hardcore.mod.fabric.datagen.BatsyHardcoreAdvancementProvider;
import batsy.hardcore.mod.fabric.datagen.BatsyHardcoreModBlockLootTableProvider;
import batsy.hardcore.mod.fabric.datagen.BatsyHardcoreModBlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

public class BatsyHardcoreModDataGeneratorFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BatsyHardcoreAdvancementProvider::new);
        pack.addProvider(BatsyHardcoreModBlockLootTableProvider::new);
        pack.addProvider(BatsyHardcoreModBlockTagProvider::new);
    }
}
