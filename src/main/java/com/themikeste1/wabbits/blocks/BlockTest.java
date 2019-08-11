package com.themikeste1.wabbits.blocks;

//Minecraft
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;

//FalconCore
import com.themikeste1.falconcore.blocks.ModBlock;

public class BlockTest extends ModBlock {

    public BlockTest() {
        super(ModBlock.Properties.create(Material.ROCK, MaterialColor.STONE));
    } //BlockTest()

    @Override
    protected void setup() {
        setRegistryName("test_block");
    }

    @Override
    public ModBlockItem getModBlockItem() {
        return new BlockItemTest();
    }

    /**
     * Manages the {@link BlockItem} of {@link BlockTest}. All properties of
     * the item can be set in the constructor. This could be done as a
     * function, but making a class allows adding custom functionality to the
     * {@link BlockItem} (e.g. making it explode after being held for too long).
     *
     * @version 1.0
     * @since 0.0.0.0
     * @author TheMikeste1
     * @see BlockTest
     * @see ModBlockItem
     */
    public class BlockItemTest extends ModBlockItem {
        public BlockItemTest() {
            super();
        } //BlockItemTest()

        @Override
        protected void setup() {
            ResourceLocation name = BlockTest.this.getRegistryName();
            assert name != null;
            setRegistryName(name);
        } //setup()
    } //class BlockItemTest
} //class BlockTest
