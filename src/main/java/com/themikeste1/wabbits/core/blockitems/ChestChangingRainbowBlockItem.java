package com.themikeste1.wabbits.core.blockitems;

import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.client.renderer.itemstack.RendererChestChangingRainbowBlockItem;
import com.themikeste1.wabbits.client.renderer.tileentity.RendererChestChangingRainbowTileEntity;
import com.themikeste1.wabbits.core.blocks.ChestChangingRainbowBlock;
import com.themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

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
