package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import com.themikeste1.wabbits.core.blockitems.IBlockItemChangesColorRainbow;
import com.themikeste1.wabbits.core.blocks.IBlockChangesColorRainbow;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * Helps set up a {@link net.minecraft.tileentity.TileEntity} to change color
 * according to {@link BlockStateProperties}.RAINBOW_COLORS.
 *
 * Should be used in conjunction with {@link IBlockChangesColorRainbow}.
 *
 * @see IBlockChangesColorRainbow
 * @see IBlockItemChangesColorRainbow
 */
public interface ITileEntityChangesColorsRainbow {
    default void updateColor(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos,
                state.cycle(BlockStateProperties.RAINBOW_COLORS));
    }
}
