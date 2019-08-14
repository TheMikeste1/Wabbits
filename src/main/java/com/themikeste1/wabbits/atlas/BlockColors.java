package com.themikeste1.wabbits.atlas;

//META
import com.themikeste1.wabbits.core.blocks.IBlockChangesColorRainbow;

//Minecraft
import net.minecraft.client.Minecraft;

public class BlockColors {
    private static final net.minecraft.client.renderer.color.BlockColors blockColors = Minecraft.getInstance().getBlockColors();

    public static void registerColors() {
        registerRainbowColors();
    }

    private static void registerRainbowColors() {
        blockColors.register(
                (IBlockChangesColorRainbow) Blocks.rainbow_bricks, Blocks.rainbow_bricks);
    }
}
