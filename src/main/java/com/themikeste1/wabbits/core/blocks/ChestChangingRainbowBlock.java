package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.client.renderer.itemstack.RendererChestChangingRainbowBlockItem;
import com.themikeste1.wabbits.Constants;
import com.themikeste1.wabbits.core.blockitems.ChestChangingRainbowBlockItem;
import com.themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;
import com.themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

//Java
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;


//TODO: Extend ContainerBlock instead of ChestBlock to give us more control
// over some of the functions of the chest. We can strip some of the functions
// of ChestBlock and use them here.

public class ChestChangingRainbowBlock extends ChestBlock implements IModHasBlockItem, IChangesColorRainbowBlock {
    private final int changeTimer;

    public ChestChangingRainbowBlock(String registryName) { this(registryName, -1); }

    public ChestChangingRainbowBlock(String registryName, int changeTimer) {
        super(Block.Properties
                .create(Material.CLAY)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.METAL)
        );
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    public ChestChangingRainbowBlock(String registryName, Block.Properties properties) {
        super(properties);
        setup(registryName);
        changeTimer = -1;
    }

    public ChestChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties) {
        super(properties);
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    private void setup(String registryName) {
        setRegistryName(Constants.MOD_ID, registryName);
        this.setDefaultState(getDefaultState()
                .with(BlockStateProperties.RAINBOW_COLORS, DyeColor.MAGENTA));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChestChangingRainbowTileEntity(changeTimer);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new ChestChangingRainbowTileEntity(changeTimer);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChestChangingRainbowTileEntity tile = (ChestChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = worldIn.getBlockState(pos);
            state = updateColor(state, worldIn, pos);
            updatePartner(state, worldIn, pos, BlockStateProperties.RAINBOW_COLORS);
            tile.resetCounter();
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChestChangingRainbowTileEntity tile = (ChestChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved()) {
            state = updateColor(state, worldIn, pos);
            updatePartner(state, worldIn, pos, BlockStateProperties.RAINBOW_COLORS);
        }
    }

    protected void updatePartner(BlockState state, World worldIn, BlockPos pos, IProperty property) {
        if (state.get(TYPE) == ChestType.SINGLE)
            return;

        assert property != null;

        Direction direction = getDirectionToAttached(state);

        pos = pos.add(
                direction.getXOffset(),
                direction.getYOffset(),
                direction.getZOffset()
        );

        try {
            state = worldIn.getBlockState(pos).with(property, state.get(property));
            worldIn.setBlockState(pos, state);
        } catch (Exception e) {
            LOGGER.warn(e);
            throw e;
        }
    }


    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        addRainbowColors(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);

        if (state.get(TYPE) != ChestType.SINGLE) {
            BlockPos pos = context.getPos();

            Direction direction = context.getPlacementHorizontalFacing();

            if (state.get(TYPE) == ChestType.RIGHT) {
                direction = direction.rotateY();
            } else {
                direction = direction.rotateYCCW();
            }

            World world = context.getWorld();
            assert world != null;

            TileEntity otherChest =
                    world.getTileEntity(
                            pos.add(
                                    direction.getXOffset(),
                                    direction.getYOffset(),
                                    direction.getZOffset()
                            )
                    );
            assert otherChest != null;
            assert otherChest instanceof ChestChangingRainbowTileEntity;

            state = state.with(BlockStateProperties.RAINBOW_COLORS,
                    otherChest.getBlockState()
                            .get(BlockStateProperties.RAINBOW_COLORS)
            );
        }

        return state;
    }


    /* ***************************************************************************
     * FalconAthenaeum
     ****************************************************************************/
    @Override
    public BlockItem generateModBlockItem() {
        Supplier sup = () -> (Callable) () -> RendererChestChangingRainbowBlockItem.INSTANCE;

        return new ChestChangingRainbowBlockItem(this,
                new Item.Properties()
                        .setTEISR(sup)
                        .group(ItemGroups.MAIN_GROUP_WABBITS)
                        .rarity(Rarity.EPIC)
        );
    }

    
    protected static final InventoryFactory<INamedContainerProvider> chestContainerFactory = new InventoryFactory<INamedContainerProvider>() {
        @Override
        public INamedContainerProvider forDouble(ChestTileEntity rightChest, ChestTileEntity leftChest) {
            final IInventory iInventory = new DoubleSidedInventory(rightChest, leftChest);
            return new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    if (rightChest.hasCustomName()) {
                        return rightChest.getDisplayName();
                    } else {
                        return (ITextComponent)(leftChest.hasCustomName() ? leftChest.getDisplayName() : new TranslationTextComponent("container.wabbits.rainbow_chest_double"));
                    }
                }

                @Nullable
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    if (rightChest.canOpen(playerEntity) && leftChest.canOpen(playerEntity)) {
                        rightChest.fillWithLoot(playerInventory.player);
                        leftChest.fillWithLoot(playerInventory.player);
                        return ChestContainer.createGeneric9X6(id, playerInventory, iInventory);
                    } else {
                        return null;
                    }
                }
            };         
        }
        
        @Override
        public INamedContainerProvider forSingle(ChestTileEntity chest) {
            return chest;
        }
    };

    interface InventoryFactory<T> {
        T forDouble(ChestTileEntity rightChest, ChestTileEntity leftChest);
        T forSingle(ChestTileEntity chest);
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return func_220106_a(state, worldIn, pos, false, chestContainerFactory);
    }

    @Nullable
    public static <T> T func_220106_a(BlockState state, IWorld world, BlockPos pos, boolean allowBlocked, InventoryFactory<T> inventoryFactory) {
        TileEntity tileEntity = world.getTileEntity(pos);

        //If it's not a Rainbow Chest, return nothing
        if (!(tileEntity instanceof ChangingRainbowTileEntity)) {
            return (T)null;
        }
        //If it's blocked and we don't allow blocked, return nothing.
        else if (!allowBlocked && isBlocked(world, pos)) {
            return (T)null;
        } else {
            //Start treating the tile entity as a chest...
            ChestTileEntity chestTileEntity = (ChestTileEntity)tileEntity;
            ChestType chestType = state.get(TYPE);
            //If single, return single name
            if (chestType == ChestType.SINGLE) {
                return inventoryFactory.forSingle(chestTileEntity);
            } else {
                BlockPos attachedPos = pos.offset(getDirectionToAttached(state));
                BlockState attachedState = world.getBlockState(attachedPos);
                //Make sure we're attached to another chest
                if (attachedState.getBlock() == state.getBlock()) {
                    ChestType attachedType = attachedState.get(TYPE);
                    //Ensure that the other chest is attached to us
                    if (attachedType != ChestType.SINGLE && chestType != attachedType && attachedState.get(FACING) == state.get(FACING)) {
                        //Return nothing if attached chest is blocked
                        if (!allowBlocked && isBlocked(world, attachedPos)) {
                            return (T)null;
                        }

                        TileEntity attachedTileEntity = world.getTileEntity(attachedPos);
                        //Ensure we are indeed working with a chest tile entity
                        if (attachedTileEntity instanceof ChestTileEntity) {
                            //Get the two tile entities
                            ChestTileEntity rightChestEntity = chestType == ChestType.RIGHT ? chestTileEntity : (ChestTileEntity)attachedTileEntity;
                            ChestTileEntity leftChestEntity = chestType == ChestType.RIGHT ? (ChestTileEntity)attachedTileEntity : chestTileEntity;
                            return inventoryFactory.forDouble(rightChestEntity, leftChestEntity);
                        }
                    }
                }
                //If we're not blocked but not double, return single
                //(We know we're double if any of the other if statements fail)
                return inventoryFactory.forSingle(chestTileEntity);
            }
        }
    }

    private static boolean isBlocked(IWorld world, BlockPos pos) {
        return isBelowSolidBlock(world, pos) || isCatSittingOn(world, pos);
    }

    private static boolean isBelowSolidBlock(IBlockReader blockReader, BlockPos pos) {
        BlockPos upPos = pos.up();
        return blockReader.getBlockState(upPos).isNormalCube(blockReader, upPos);
    }

    private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
        List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1)));
        if (!list.isEmpty()) {
            for(CatEntity catentity : list) {
                if (catentity.isSitting()) {
                    return true;
                }
            }
        }

        return false;
    }
}
