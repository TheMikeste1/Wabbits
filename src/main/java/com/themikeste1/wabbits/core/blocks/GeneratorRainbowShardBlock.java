package com.themikeste1.wabbits.core.blocks;

//META
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;
import com.themikeste1.wabbits.core.blockitems.AutoNamingBlockItem;
import com.themikeste1.wabbits.core.tileentities.GeneratorRainbowShardTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

//Java
import javax.annotation.Nullable;


public class GeneratorRainbowShardBlock extends HorizontalFacedBlock implements IModHasBlockItem {
    public GeneratorRainbowShardBlock() {
        super("generator_rainbow_shard",
                Block.Properties
                        .create(Material.CLAY)
                        .hardnessAndResistance(2.5f, 30f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GeneratorRainbowShardTileEntity();
    }

    @Override
    public BlockItem generateModBlockItem() {
        return new AutoNamingBlockItem(this);
    }
}
