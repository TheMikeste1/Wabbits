package com.themikeste1.wabbits.core.blocks;

//META
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;
import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.blockitems.AutoNamingBlockItem;
import com.themikeste1.wabbits.core.tileentities.GeneratorRainbowShardTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//Forge
import net.minecraftforge.fml.network.NetworkHooks;

//Java
import javax.annotation.Nullable;


public class GeneratorRainbowShardBlock extends HorizontalFacedBlock implements IModHasBlockItem {
    public GeneratorRainbowShardBlock() {
        super("generator_rainbow_shard",
                Block.Properties
                        .create(Material.CLAY)
                        .hardnessAndResistance(2.5f, 30f));
        setDefaultState(
                getDefaultState()
                        .with(BlockStateProperties.POWERED, false)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GeneratorRainbowShardTileEntity();
    }

    @Override
    public BlockItem generateModBlockItem() {
        return new AutoNamingBlockItem(this, new Item.Properties().group(ItemGroups.MAIN_GROUP_WABBITS));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof GeneratorRainbowShardTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            }
        }

        //Returning "true" blocks any other interactions from happening, such as
        //placing a block or using the wrench.
        return true;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        getBlock().onBlockHarvested(world, pos, state, player);
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof GeneratorRainbowShardTileEntity) {
            InventoryHelper
                    .spawnItemStack(
                            world,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            ((GeneratorRainbowShardTileEntity) tileentity)
                            .getStackInSlot(0)
                    );
        }

        return world.removeBlock(pos, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED);
    }
}
