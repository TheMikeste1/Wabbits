package com.themikeste1.wabbits.api.state.properties;

import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;

/**
 *
 *
 * @see net.minecraft.state.properties.BlockStateProperties
 */
public class BlockStateProperties {
    public static final EnumProperty<DyeColor> RAINBOW_COLORS = EnumProperty.create(
            "color", DyeColor.class, DyeColor.MAGENTA, DyeColor.PURPLE,
            DyeColor.BLUE, DyeColor.GREEN, DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED);
}
