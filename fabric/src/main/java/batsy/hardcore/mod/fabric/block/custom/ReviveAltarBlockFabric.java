package batsy.hardcore.mod.fabric.block.custom;

import batsy.hardcore.mod.BatsyHardcoreConfiguration;
import batsy.hardcore.mod.fabric.block.entity.BatsyHardcoreModBlockEntitiesFabric;
import batsy.hardcore.mod.fabric.block.entity.ReviveAltarBlockEntityFabric;
import batsy.hardcore.mod.util.BatsyHardcorePlayerTagsUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReviveAltarBlockFabric extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty LOADED = BooleanProperty.of("loaded");
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public ReviveAltarBlockFabric(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LOADED, false));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(LOADED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ReviveAltarBlockEntityFabric(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BatsyHardcoreModBlockEntitiesFabric.REVIVE_ALTAR_BE,
                (tickingWorld, tickingBlockPos, tickingBlockState, tickingBlockEntity) ->
                        tickingBlockEntity.tick(tickingWorld, tickingBlockState, tickingBlockEntity));
    }

    @Override
    public void onPlaced(@NotNull World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient || !(placer instanceof ServerPlayerEntity serverPlayerEntity)) {
            return;
        }

        if (world.getBlockEntity(blockPos) instanceof ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
            if (BatsyHardcorePlayerTagsUtil.isBatsyHardcore(serverPlayerEntity)) {
                serverPlayerEntity.sendMessage(Text.literal("This altar supersedes any other altars you've placed."));
            }

            placeReviveAltar(serverPlayerEntity, reviveAltarBlockEntityFabric);
        }
    }

    private void placeReviveAltar(ServerPlayerEntity serverPlayerEntity, ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
        startBatsyHardcoreModeForPlayer(serverPlayerEntity, reviveAltarBlockEntityFabric);
        serverPlayerEntity.sendMessage(Text.literal("You entered Batsy Hardcore mode and set your spawn here."));
    }

    @Override
    public ActionResult onUse(BlockState blockState, @NotNull World world, BlockPos blockPos, @NotNull PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        if (!world.isClient) {
            if (world.getBlockEntity(blockPos) instanceof ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric) {
                playerEntity.openHandledScreen(reviveAltarBlockEntityFabric);
            }
        }

        return ActionResult.SUCCESS;
    }

    public static int getLightLevel(@NotNull BlockState blockState) {
        return blockState.get(LOADED) ?
                BatsyHardcoreConfiguration.reviveAltarLoadedLightLevel :
                BatsyHardcoreConfiguration.reviveAltarUnloadedLightLevel;
    }

    private void startBatsyHardcoreModeForPlayer(@NotNull ServerPlayerEntity serverPlayerEntity, @NotNull ReviveAltarBlockEntityFabric blockEntity) {
        blockEntity.setOwnerUuid(serverPlayerEntity.getUuidAsString());
        BatsyHardcorePlayerTagsUtil.removeAll(serverPlayerEntity);
        BatsyHardcorePlayerTagsUtil.addAll(serverPlayerEntity);
    }
}
