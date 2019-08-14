package com.themikeste1.wabbits.core.blockitems;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import com.themikeste1.wabbits.core.blocks.IBlockChangesColorRainbow;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/**
 * Helps set up a {@link BlockItem} to change color according to
 * {@link BlockStateProperties}.RAINBOW_COLORS.
 *
 * Should be used in conjunction with {@link IBlockChangesColorRainbow}.
 *
 * @see IBlockChangesColorRainbow
 */
public interface IBlockItemChangesColorRainbow extends IItemColor {

    /**
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this block. This changes the color of the pixels.
     *
     * @param itemStack The {@link ItemStack} we're looking at.
     * @param tintIndex The current color of a pixel in the texture of the block.
     *
     * @return The value of the current {@link BlockStateProperties} color.
     */
    @Override
    default int getColor(ItemStack itemStack, int tintIndex) {
        net.minecraft.block.Block block = ((BlockItem) itemStack.getItem()).getBlock();

        return block.getStateContainer().getBaseState()
                .get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }
}
