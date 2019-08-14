package com.themikeste1.wabbits.core.blocks;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Helps set up a {@link Block} to change color according to
 * {@link BlockStateProperties}.RAINBOW_COLORS.
 *
 * @see com.themikeste1.wabbits.core.blockitems.IBlockItemChangesColorRainbow
 */
public interface IBlockChangesColorRainbow  {
    /**
     * Adds rainbow colors to a {@link BlockState} to a
     * {@link StateContainer.Builder}.
     * Should be called in {@link Block}#fillStateContainer.
     *
     * @param builder The {@link StateContainer.Builder}
     */
    default void addRainbowColors(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.RAINBOW_COLORS);
    }

    /**
     *
     * @param state Current {@link BlockState} of the block we're changing.
     * @param worldIn The {@link World}.
     * @param pos The {@link BlockPos} of the block we're changing.
     */
    default void updateColor(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos,
                state.cycle(BlockStateProperties.RAINBOW_COLORS));
    }
}