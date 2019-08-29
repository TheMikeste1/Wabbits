package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.Constants;
import com.themikeste1.wabbits.core.blockitems.ChangingRainbowBlockItem;
import com.themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//Java
import javax.annotation.Nullable;


/**
 * Creates {@link Block}s that change color upon walking on them or clicking
 * on them.
 *
 * @see ChangingRainbowBlockItem
 * @see ChangingRainbowTileEntity
 * @see com.themikeste1.wabbits.atlas.Blocks
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 */
public class ChangingRainbowBlock extends Block implements IChangesColorRainbowBlock, IModHasBlockItem {

    /**
     * The time (in ticks) until the block can change color again.
     *
     * @see ChangingRainbowTileEntity#canChange()
     *
     * @since 0.0.0.0
     */
    private int changeTimer;

    /**
     * Most basic constructor for the {@link Block}. It defaults to using
     * {@link Material#ROCK}, hardness of 1.5, and resistance of 6.0 (the same
     * as {@link net.minecraft.block.Blocks#STONE_BRICKS}).
     *
     * The timer is set to the tile entity's default value.
     *
     * @param registryName The to-be registry name
     *
     * @since 0.0.0.0
     * @author TheMikeste1
     */
    public ChangingRainbowBlock(String registryName) { this(registryName, -1); }
    public ChangingRainbowBlock(String registryName, int changeTimer) { this(registryName, changeTimer, Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)); }
    public ChangingRainbowBlock(String registryName, Block.Properties properties) { this(registryName, -1, properties); }

    public ChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties) {
        super(properties);
        setup(changeTimer, registryName);
    }

    /**
     * Setups up the {@link Block} so I don't have to write this in multiple
     * constructors.
     *
     * @param changeTimer The default timer for the tile entity that controls
     *                    the color change cooldown.
     */
    private void setup(int changeTimer, String registryName) {
        this.setDefaultState(getDefaultState()
                .with(BlockStateProperties.RAINBOW_COLORS, DyeColor.MAGENTA)
        );
        this.changeTimer = changeTimer;

        setRegistryName(Constants.MOD_ID, registryName);
    }

    /**
     * @param state The {@link BlockState} of the {@link net.minecraft.block.Block}
     *              being tested.
     *
     * @return Whether or not the {@link Block} has a tile entity (should always be true in this case).
     */
    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    /**
     * Generates a {@link TileEntity} for this {@link Block}. Should always be
     * a {@link ChangingRainbowTileEntity} for this {@link Block}.
     *
     * @param state The {@link BlockState} of the {@link Block} being tested.
     * @param world The {@link World} of the {@link Block} being tested.
     * @return The {@link TileEntity} for this {@link Block}. Should always be a {@link ChangingRainbowTileEntity}.
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChangingRainbowTileEntity(changeTimer);
    }

    /**
     * Dictates the what happens when the {@link Block} is walked on. In this
     * case, it changes the color of the {@link Block} (if the timer is at 0)
     * and resets its the timer.
     *
     * @param worldIn The {@link World} of the {@link Block}.
     * @param pos The {@link BlockPos} of the {@link Block}.
     * @param entityIn The {@link Entity} walking on the {@link Block}.
     */
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = tile.getBlockState();
            state = updateColor(state, worldIn, pos);
            tile.setCounter(changeTimer);
        }
    }

    /**
     * Dictates the what happens when the {@link Block} is left clicked.
     * In this case, it changes the color of the {@link Block}.
     *
     * @param state The {@link BlockState} of the {@link Block} being tested.
     * @param worldIn The {@link World} of the {@link Block} being tested.
     * @param pos The {@link BlockPos} of the {@link Block}.
     * @param player The {@link PlayerEntity} clicking on the {@link Block}.
     */
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved())
            state = updateColor(state, worldIn, pos);
    }

    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/

    /**
     * Fills the {@link StateContainer} with {@link net.minecraft.state.Property}s
     * of the {@link Block}. In this case, it only gives it {@link BlockStateProperties#RAINBOW_COLORS}
     *
     * @param builder The container holding the properties.
     */
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        addRainbowColors(builder);
    }

    /* ***************************************************************************
     * FalconAthenaeum
     ****************************************************************************/
    @Override
    public BlockItem generateModBlockItem() {
        return new ChangingRainbowBlockItem(this);
    }
} //class BlockRainbowBricks
