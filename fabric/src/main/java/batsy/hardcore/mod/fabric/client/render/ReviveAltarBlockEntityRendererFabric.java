package batsy.hardcore.mod.fabric.client.render;

import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityConstants;
import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import batsy.hardcore.mod.fabric.item.BatsyHardcoreModItemsFabric;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ReviveAltarBlockEntityRendererFabric implements BlockEntityRenderer<ReviveAltarBlockEntityFabric> {
    private static final ItemStack basicLoadedItem = new ItemStack(BatsyHardcoreModItemsFabric.REVIVE_TOTEM_BASIC);
    private static final ItemStack advancedLoadedItem = new ItemStack(BatsyHardcoreModItemsFabric.REVIVE_TOTEM_ADVANCED);

    public ReviveAltarBlockEntityRendererFabric(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(@NotNull ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric, float tickDelta, @NotNull MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = reviveAltarBlockEntityFabric.getWorld();
        if (world == null || !reviveAltarBlockEntityFabric.isLoaded()) {
            return;
        }

        prepareMatricesForRendering(matrices, world, tickDelta);
        renderTotemOnAltar(reviveAltarBlockEntityFabric, matrices, vertexConsumers, world);
        matrices.pop();
    }

    private void prepareMatricesForRendering(@NotNull MatrixStack matrices, World world, float tickDelta) {
        matrices.push();
        double offset = calculateOffset(world, tickDelta);
        matrices.translate(0.5, 1.25 + offset, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((world.getTime() + tickDelta) * 4));
    }

    private double calculateOffset(@NotNull World world, float tickDelta) {
        return Math.sin((world.getTime() + tickDelta) / 8.0) / 4.0;
    }

    private void renderTotemOnAltar(@NotNull ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric, @NotNull MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world) {
        int lightMapCoordsAboveRenderedEntity = WorldRenderer.getLightmapCoordinates(world, reviveAltarBlockEntityFabric.getPos().up());
        ItemStack itemToRender = getItemToRender(reviveAltarBlockEntityFabric);
        MinecraftClient.getInstance().getItemRenderer().renderItem(itemToRender, ModelTransformationMode.GROUND, lightMapCoordsAboveRenderedEntity, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, world, 0);
    }

    @NotNull
    private static ItemStack getItemToRender(@NotNull ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
        int loadedValue = reviveAltarBlockEntityFabric.getLoaded();

        return switch (loadedValue) {
            case ReviveAltarBlockEntityConstants.Properties.LOADED_BASIC_VALUE -> basicLoadedItem;
            case ReviveAltarBlockEntityConstants.Properties.LOADED_ADVANCED_VALUE -> advancedLoadedItem;
            default -> ItemStack.EMPTY;
        };
    }
}
