package com.themikeste1.wabbits.atlas.color;

//META
import com.themikeste1.wabbits.api.handlers.HandlerRainbowColor;

//Minecraft
import com.themikeste1.wabbits.atlas.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class BlockItemColors {
    private static final net.minecraft.client.renderer.color.ItemColors itemColors = Minecraft.getInstance().getItemColors();

    public static void registerColors() {
        registerRainbowColors();
    }

    private static void registerRainbowColors() {
        itemColors.register(new HandlerRainbowColor(),
                Item.BLOCK_TO_ITEM.get(Blocks.rainbow_bricks),
                Item.BLOCK_TO_ITEM.get(Blocks.rainbow_glass),
                Item.BLOCK_TO_ITEM.get(Blocks.stained_rainbow_glass),
                Item.BLOCK_TO_ITEM.get(Blocks.rainbow_chest)
        );
    }
}
