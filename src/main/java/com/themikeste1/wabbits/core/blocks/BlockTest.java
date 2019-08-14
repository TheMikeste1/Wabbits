package com.themikeste1.wabbits.core.blocks;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.core.blockitems.BlockItemTest;
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;




public class BlockTest extends Block implements IModBlock {

    public BlockTest() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE));
        setup();
    } //BlockTest()

    private void setup() {
        setRegistryName(Constants.MOD_ID, "test_block");
    }

    @Override
    public BlockItem getModBlockItem() {
        return new BlockItemTest();
    }
} //class BlockTest
