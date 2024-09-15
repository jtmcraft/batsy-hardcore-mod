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
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
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
        return checkType(type, BatsyHardcoreModBlockEntitiesFabric.REVIVE_ALTAR_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

//    @Override
//    public ActionResult onUse(BlockState blockState, @NotNull World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
//        if(!world.isClient && hand == Hand.MAIN_HAND) {
//            world.setBlockState(blockPos, blockState.cycle(LOADED));
//        }
//
//        return ActionResult.SUCCESS;
//    }

    public static boolean canSetSpawn(@NotNull World level) {
        boolean bedWorks = level.getDimension().bedWorks();

        if (BatsyHardcoreConfiguration.batsyHardcoreUseRespawnAnchor) {
            return bedWorks || level.getDimension().respawnAnchorWorks();
        }

        return bedWorks;
    }

    @Override
    public void onPlaced(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient || placer == null) {
            return;
        }

        if (!(placer instanceof ServerPlayerEntity serverPlayerEntity)) {
            return;
        }

        if (!(blockState.getBlock() instanceof ReviveAltarBlockFabric)) {
            return;
        }

        if (!ReviveAltarBlockFabric.canSetSpawn(world)) {
            serverPlayerEntity.sendMessage(Text.literal("You cannot set your spawn in this world."));
            return;
        }

        if (!(world.getBlockEntity(blockPos) instanceof ReviveAltarBlockEntityFabric reviveAltarBlockEntityFabric)) {
            serverPlayerEntity.sendMessage(Text.literal("Something has gone wrong. This is not a revive altar."));
            return;
        }

        startBatsyHardcoreModeForPlayer(serverPlayerEntity, reviveAltarBlockEntityFabric);
        serverPlayerEntity.sendMessage(Text.literal("You entered Batsy Hardcore mode and set your spawn here."));
    }

    private void startBatsyHardcoreModeForPlayer(@NotNull ServerPlayerEntity player, @NotNull ReviveAltarBlockEntityFabric blockEntity) {
        blockEntity.setOwnerUuid(player.getUuidAsString());
        BatsyHardcorePlayerTagsUtil.addAll(player);
    }
}
