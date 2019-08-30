package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.core.blockitems.TestBlockItem;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.util.BlockRenderLayer;


public class TestBlock extends Block implements IModHasBlockItem {
    public static final String regName = "test_block";
    public TestBlock() {
        super(/*"test_block",*/
                Block.Properties.create(Material.ROCK, MaterialColor.STONE));
        setRegistryName(regName);
    } //BlockTest()

    @Override
    public BlockItem generateModBlockItem() {
        return new TestBlockItem();
    }
} //class BlockTest
