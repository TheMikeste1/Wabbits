package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.blockitems.BlockItemChangingRainbow;
import com.themikeste1.wabbits.core.Constants;
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
 *
 * @see BlockItemChangingRainbow
 * @see ChangingRainbowTileEntity
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class ChangingRainbowBlock extends Block implements IChangesColorRainbowBlock, IModHasBlockItem {

    private final int changeTimer;

    public ChangingRainbowBlock(String registryName) {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
        );
        setup(registryName);
        changeTimer = -1;
    }

    public ChangingRainbowBlock(String registryName, int changeTimer) {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
        );
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    public ChangingRainbowBlock(String registryName, Block.Properties properties) {
        super(properties);
        setup(registryName);
        changeTimer = -1;
    }

    public ChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties) {
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
        return new ChangingRainbowTileEntity(changeTimer);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = tile.getBlockState();
            updateColor(state, worldIn, pos);
            tile.resetCounter();
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved())
            updateColor(state, worldIn, pos);
    }

    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        addRainbowColors(builder);
    }

    /* ***************************************************************************
     * FalconAthenaeum
     ****************************************************************************/
    @Override
    public BlockItem generateModBlockItem() {
        return new BlockItemChangingRainbow(this);
    }
} //class BlockRainbowBricks
