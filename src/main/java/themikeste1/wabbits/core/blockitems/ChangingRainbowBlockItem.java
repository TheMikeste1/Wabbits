package themikeste1.wabbits.core.blockitems;

//META
import themikeste1.wabbits.atlas.ItemGroups;
import themikeste1.wabbits.core.blocks.ChangingRainbowBlock;
import themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import themikeste1.wabbits.atlas.BlockItems;


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
 * @see BlockItems
 */
public class ChangingRainbowBlockItem extends AutoNamingBlockItem implements IChangesColorRainbowBlockItem {
    public ChangingRainbowBlockItem(Block block) {
        this(block,
                new Item.Properties()
                        .group(ItemGroups.MAIN_GROUP_WABBITS)
                        .rarity(Rarity.EPIC)
        );
    }

    public ChangingRainbowBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    /* *******************************************************************
     * hasEffect()
     * Returning true makes the item have the enchant "shimmer" effect.
     ********************************************************************/
    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

} //class BlockItemRainbowBricks
