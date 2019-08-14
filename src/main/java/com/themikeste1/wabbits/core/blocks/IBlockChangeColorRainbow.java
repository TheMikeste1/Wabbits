package com.themikeste1.wabbits.core.blocks;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

//Java
import javax.annotation.Nullable;

/**
 * Helps set up a block to change color according to
 * {@link BlockStateProperties}.RAINBOW_COLORS.
 *
 * @see com.themikeste1.wabbits.core.blockitems.IBlockItemChangeColorRainbow
 */
public interface IBlockChangeColorRainbow extends IBlockColor {

    /**
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this block. This changes the color of the pixels.
     *
     * @param blockState The current {@link BlockState} of the block.
     * @param environment The block's environment (biome, ect.)
     * @param coordinates The {@link BlockPos}.
     * @param tintIndex The current color of a pixel in the texture of the block.
     * @return The value of the current {@link BlockStateProperties} color.
     */
    @Override
    default int getColor(BlockState blockState, @Nullable IEnviromentBlockReader environment, @Nullable BlockPos coordinates, int tintIndex) {
        return blockState.get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }

    /**
     * Adds rainbow colors to a {@link BlockState} to a
     * {@link StateContainer.Builder}. Should be called in #fillStateContainer.
     *
     * @param builder The {@link StateContainer.Builder}
     */
    default void addRainbowColors(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.RAINBOW_COLORS);
    }
}