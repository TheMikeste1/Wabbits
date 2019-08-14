package com.themikeste1.wabbits.atlas;

//META
import com.themikeste1.wabbits.core.blockitems.IBlockItemChangesColorRainbow;

//Minecraft
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

public class BlockItemColors {
    private static final net.minecraft.client.renderer.color.ItemColors itemColors = Minecraft.getInstance().getItemColors();

    public static void registerColors() {
        registerRainbowColors();
    }

    private static void registerRainbowColors() {
        itemColors.register(
                (IBlockItemChangesColorRainbow) Item.BLOCK_TO_ITEM.get(Blocks.rainbow_bricks),
                Item.BLOCK_TO_ITEM.get(Blocks.rainbow_bricks));
    }
}
