package com.themikeste1.wabbits.core.blocks;

//Java
import javax.annotation.Nullable;

//Minecraft
import com.themikeste1.wabbits.core.blockitems.BlockItemRainbowBricks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.Constants;



/**
 *
 */
public class BlockRainbowBricks extends Block implements IBlockColor, IModBlock {

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

/* ***************************************************************************
 * BlockStates
 ****************************************************************************/
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.RAINBOW_COLORS);
    }

/* ***************************************************************************
 * ModBlock
 ****************************************************************************/
    @Override
    public BlockItem getModBlockItem() { return new BlockItemRainbowBricks(); }

/* ***************************************************************************
 * IBlockColor
 ****************************************************************************/
    /* ***********************************************************************
     * getColor()
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this block. This changes the color of the pixels.
     ************************************************************************/
    @Override
    public int getColor(BlockState blockState, @Nullable IEnviromentBlockReader environment, @Nullable BlockPos coordinates, int tintIndex) {
        return blockState.get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        updateColor(worldIn, pos);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        updateColor(worldIn, pos);
        super.onBlockClicked(state, worldIn, pos, player);
    }

    private void updateColor(World worldIn, BlockPos pos) {
        DyeColor color = worldIn.getBlockState(pos)
                .cycle(BlockStateProperties.RAINBOW_COLORS)
                .get(BlockStateProperties.RAINBOW_COLORS);

        worldIn.setBlockState(pos,
                worldIn.getBlockState(pos)
                .with(BlockStateProperties.RAINBOW_COLORS, color));
    }
} //class BlockRainbowBricks
