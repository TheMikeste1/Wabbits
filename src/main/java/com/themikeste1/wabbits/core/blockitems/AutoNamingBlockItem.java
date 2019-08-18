package com.themikeste1.wabbits.core.blockitems;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;


/**
 * Generates a {@link BlockItem} that has the same name as the {@link Block}
 * given to it.
 */
public class AutoNamingBlockItem extends BlockItem {
    public AutoNamingBlockItem(Block block) {
        super(block, new Item.Properties());
        setup(block.getRegistryName());
    }

    public AutoNamingBlockItem(Block block, Properties properties) {
        super(block, properties);
        setup(block.getRegistryName());
    }

    private void setup(ResourceLocation registryName) {
        assert registryName != null;
        setRegistryName(registryName);
    } //setup()
}
