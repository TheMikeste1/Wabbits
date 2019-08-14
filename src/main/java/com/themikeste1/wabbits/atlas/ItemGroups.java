package com.themikeste1.wabbits.atlas;

//Meta
import com.themikeste1.wabbits.core.Constants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {
    public static ItemGroup MAIN_GROUP_WABBITS =
            new ItemGroup(Constants.MOD_ID) {
                @Override
                public ItemStack createIcon() {
                    return new ItemStack(Blocks.test_block);
                }
            };
}
