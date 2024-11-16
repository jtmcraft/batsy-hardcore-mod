package batsy.hardcore.mod.fabric.datagen;

import batsy.hardcore.mod.fabric.block.BatsyHardcoreModBlocksFabric;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BatsyHardcoreModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BatsyHardcoreModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        List.of(BlockTags.DRAGON_IMMUNE,
                        BlockTags.HOGLIN_REPELLENTS,
                        BlockTags.NEEDS_DIAMOND_TOOL,
                        BlockTags.PICKAXE_MINEABLE,
                        BlockTags.PIGLIN_REPELLENTS,
                        BlockTags.WITHER_IMMUNE)
                .forEach(tag -> getOrCreateTagBuilder(tag).add(BatsyHardcoreModBlocksFabric.REVIVE_ALTAR_BLOCK));
    }
}
