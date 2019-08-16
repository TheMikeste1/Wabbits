package com.themikeste1.wabbits.core.blockitems;

//META
import com.themikeste1.wabbits.atlas.ItemGroups;

//Minecraft
import com.themikeste1.wabbits.core.blocks.ChangingRainbowBlock;
import com.themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

/**
 * Manages the {@link net.minecraft.item.BlockItem} of {@link ChangingRainbowBlock}.
 * All properties of the item can be set in the constructor. This could be
 * done as a function, but making a class allows adding custom functionality
 * to the {@link net.minecraft.item.BlockItem}
 * (e.g. making it explode after being held for too long).
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see ChangingRainbowBlock
 * @see ChangingRainbowTileEntity
 * @see com.themikeste1.wabbits.atlas.BlockItems
 */
public class ChangingRainbowBlockItem extends BlockItem implements IChangesColorRainbowBlockItem {
    public ChangingRainbowBlockItem(Block block) {
        super(block,
                new Item.Properties()
                        .group(ItemGroups.MAIN_GROUP_WABBITS)
                        .rarity(Rarity.EPIC)
        );
        setup(block.getRegistryName());
    }

    private void setup(ResourceLocation registryName) {
        assert registryName != null;
        setRegistryName(registryName);
    } //setup()

    /* *******************************************************************
     * hasEffect()
     * Returning true makes the item have the enchant "shimmer" effect.
     ********************************************************************/
    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

} //class BlockItemRainbowBricks
