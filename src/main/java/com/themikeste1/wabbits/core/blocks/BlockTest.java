package com.themikeste1.wabbits.core.blocks;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

//FalconCore
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.core.Constants;


public class BlockTest extends Block implements IModBlock {

    public BlockTest() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE));
        setup();
    } //BlockTest()

    protected void setup() {
        setRegistryName(Constants.MOD_ID, "test_block");
    }

    @Override
    public BlockItem getModBlockItem() {
        return new BlockItemTest();
    }

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
            super(BlockTest.this,
                    new Item.Properties());
            setup();
        }

        protected void setup() {
            ResourceLocation name = BlockTest.this.getRegistryName();
            assert name != null;
            setRegistryName(name);
        } //setup()
    } //class BlockItemTest
} //class BlockTest
