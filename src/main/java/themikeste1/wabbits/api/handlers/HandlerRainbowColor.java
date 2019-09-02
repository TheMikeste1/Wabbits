package themikeste1.wabbits.api.handlers;

//META
import themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

//Java
import javax.annotation.Nullable;


/**
 * Handles the coloring of Rainbow objects.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 */
public class HandlerRainbowColor implements IBlockColor, IItemColor {

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
    public int getColor(BlockState blockState, @Nullable IEnviromentBlockReader environment, @Nullable BlockPos coordinates, int tintIndex) {
        return blockState.get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }

    /**
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this item. This changes the color of the pixels.
     *
     * @param itemStack The {@link ItemStack} we're looking at.
     * @param tintIndex The current color of a pixel in the texture of the block.
     *
     * @return The value of the current {@link BlockStateProperties} color.
     */
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        net.minecraft.block.Block block = null;
        try {
            block = ((BlockItem) itemStack.getItem()).getBlock();
        } catch (Exception e) {
            return 0; //Return something better once I decide how Items will be colored.
        }

        return block.getStateContainer().getBaseState()
                .get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }
}
