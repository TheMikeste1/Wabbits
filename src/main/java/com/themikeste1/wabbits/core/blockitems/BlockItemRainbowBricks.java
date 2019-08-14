package com.themikeste1.wabbits.core.blockitems;

//Minecraft
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.atlas.Blocks;
import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.blocks.BlockRainbowBricks;


/**
 * Manages the {@link net.minecraft.item.BlockItem} of {@link BlockRainbowBricks}.
 * All properties of the item can be set in the constructor. This could be
 * done as a function, but making a class allows adding custom functionality
 * to the {@link net.minecraft.item.BlockItem}
 * (e.g. making it explode after being held for too long).
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see BlockRainbowBricks
 * @see BlockItem
 */
public class BlockItemRainbowBricks extends BlockItem implements IItemColor {
    public BlockItemRainbowBricks() {
        super(Blocks.rainbow_bricks,
                new Item.Properties()
                        .group(ItemGroups.MAIN_GROUP_WABBITS)
                        .rarity(Rarity.EPIC)
        );
        setup();
    }

    void setup() {
        ResourceLocation name = Blocks.rainbow_bricks.getRegistryName();
        assert name != null;
        setRegistryName(name);
    } //setup()

    /* *******************************************************************
     * hasEffect()
     * Returning true makes the item have the enchant "shimmer" effect.
     ********************************************************************/
    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

    /* *******************************************************************
     * getColor()
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this item. This changes the color of the pixels.
     ********************************************************************/
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        return Blocks.rainbow_bricks.getStateContainer().getBaseState()
                .get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }
} //class BlockItemRainbowBricks
