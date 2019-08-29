package com.themikeste1.wabbits.atlas;

//META
import com.themikeste1.wabbits.Constants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Minecraft
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;


@OnlyIn(Dist.CLIENT)
public class ItemGroups {
    public static ItemGroup MAIN_GROUP_WABBITS =
            new ItemGroup(Constants.MOD_ID) {
                @Override
                public ItemStack createIcon() {
                    return new ItemStack(Blocks.test_block);
                }
            };
}
