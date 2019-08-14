package com.themikeste1.wabbits.api.state.properties;

import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;

/**
 *  Contains all the custom {@link net.minecraft.block.BlockState}
 *  {@link net.minecraft.state.Property}'s.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see net.minecraft.state.properties.BlockStateProperties
 */
public class BlockStateProperties {
    public static final EnumProperty<DyeColor> RAINBOW_COLORS = EnumProperty.create(
            "color", DyeColor.class, DyeColor.MAGENTA, DyeColor.PURPLE,
            DyeColor.BLUE, DyeColor.GREEN, DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED);
}
