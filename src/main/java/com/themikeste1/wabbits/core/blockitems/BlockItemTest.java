package com.themikeste1.wabbits.core.blockitems;

//Meta
import com.themikeste1.wabbits.atlas.Blocks;
import com.themikeste1.wabbits.core.blocks.BlockTest;

//Minecraft
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;


/**
 * Manages the {@link net.minecraft.item.BlockItem} of {@link BlockTest}. All properties of
 * the item can be set in the constructor. This could be done as a
 * function, but making a class allows adding custom functionality to the
 * {@link net.minecraft.item.BlockItem} (e.g. making it explode after being held for too long).
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see BlockTest
 * @see BlockItem
 */
public class BlockItemTest extends BlockItem {
    public BlockItemTest() {
        super(Blocks.test_block,
                new Item.Properties());
        setup();
    }

    void setup() {
        ResourceLocation name = Blocks.test_block.getRegistryName();
        assert name != null;
        setRegistryName(name);
    } //setup()
} //class BlockItemTest
