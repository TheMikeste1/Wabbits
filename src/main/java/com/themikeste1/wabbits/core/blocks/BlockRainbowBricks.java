package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;
import com.themikeste1.falconathenaeum.core.blocks.IModHasTileEntity;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.blockitems.BlockItemRainbowBricks;
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.TileEntityRainbowBricks;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//Java
import javax.annotation.Nullable;


/**
 *
 * @see BlockItemRainbowBricks
 * @see TileEntityRainbowBricks
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class BlockRainbowBricks extends Block implements IBlockChangeColorRainbow, IModHasBlockItem, IModHasTileEntity {

    public BlockRainbowBricks() {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
        );
        setup();
    }

    private void setup() {
        setRegistryName(Constants.MOD_ID, "rainbow_bricks");
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
        return new TileEntityRainbowBricks();
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved()) {
            BlockState state = worldIn.getBlockState(pos);
            ((TileEntityRainbowBricks) tile).walked(state, worldIn, pos);
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved())
            ((TileEntityRainbowBricks) tile).clicked(state, worldIn, pos);
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
        return new BlockItemRainbowBricks();
    }

    @Override
    public TileEntityType generateModTileEntityType() {
        return TileEntityType.Builder
                .create(TileEntityRainbowBricks::new, this)
                .build(null).setRegistryName(this.getRegistryName());
    }
} //class BlockRainbowBricks
