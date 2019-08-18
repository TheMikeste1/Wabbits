package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.core.blockitems.TestBlockItem;
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;




public class TestBlock extends FacedBlock implements IModHasBlockItem {

    public TestBlock() {
        super("test_block",
                Block.Properties.create(Material.ROCK, MaterialColor.STONE));
    } //BlockTest()

    @Override
    public BlockItem generateModBlockItem() {
        return new TestBlockItem();
    }
} //class BlockTest
