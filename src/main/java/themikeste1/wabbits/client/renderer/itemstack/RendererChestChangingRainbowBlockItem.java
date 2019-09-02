package themikeste1.wabbits.client.renderer.itemstack;

import themikeste1.wabbits.atlas.BlockItems;
import themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class RendererChestChangingRainbowBlockItem extends ItemStackTileEntityRenderer {
    public static final RendererChestChangingRainbowBlockItem INSTANCE = new RendererChestChangingRainbowBlockItem();

    private final ChestChangingRainbowTileEntity rainbowChest = new ChestChangingRainbowTileEntity();

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        if (itemStackIn.getItem() != BlockItems.rainbow_chest)
            return;
        TileEntityRendererDispatcher.instance.renderAsItem(rainbowChest);
    }
}
