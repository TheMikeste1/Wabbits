package themikeste1.wabbits.core.blockitems;

import themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ChestChangingRainbowBlockItem extends ChangingRainbowBlockItem {

    /*public ChestChangingRainbowBlockItem(Block block) {
        super(block,
        );

        Supplier test = ;
    }*/

    public ChestChangingRainbowBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public void renderByItem(ItemStack itemStackIn) {
        TileEntityRendererDispatcher.instance.renderAsItem(new ChestChangingRainbowTileEntity());
    }
}
