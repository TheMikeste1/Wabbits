package themikeste1.wabbits.core.blocks;

import themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

import themikeste1.wabbits.atlas.ItemGroups;
import themikeste1.wabbits.core.blockitems.AutoNamingBlockItem;
import themikeste1.wabbits.core.tileentities.GrinderTileEntity;

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
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;



public class GrinderBlock extends HorizontalFacedBlock implements IModHasBlockItem {
    public static final String regName = "grinder";

    public GrinderBlock() {
        super(regName, Block.Properties
                .create(Material.CLAY)
                .hardnessAndResistance(2.5f, 30f)
        );

        setDefaultState(getDefaultState()
                .with(BlockStateProperties.POWERED, false)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new GrinderTileEntity(); }

    @Override
    public BlockItem generateModBlockItem() {
        return new AutoNamingBlockItem(this, new Item.Properties().group(ItemGroups.MAIN_GROUP_WABBITS));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof GrinderTileEntity) {
                NetworkHooks.openGui(
                        (ServerPlayerEntity) player,
                        (INamedContainerProvider) tileEntity,
                        tileEntity.getPos());
            }
        }

        //Returning "true" blocks any other interactions from happening, such as
        //placing a block or using the wrench.
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        TileEntity tileEntity = world.getTileEntity(pos);
        NonNullList<ItemStack> stacks = NonNullList.create();
        if (tileEntity instanceof GrinderTileEntity) {
            GrinderTileEntity grinderEntity = ((GrinderTileEntity) tileEntity);
            stacks.add(grinderEntity.getStackInSlot(0));
            stacks.add(grinderEntity.getStackInSlot(1));
        }

        boolean removed = super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
        if (removed) {
            InventoryHelper.dropItems(world, pos, stacks);
        }

        return removed;
    }
}
