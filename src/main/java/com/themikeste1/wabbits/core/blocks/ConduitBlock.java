package com.themikeste1.wabbits.core.blocks;

import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.blockitems.AutoNamingBlockItem;
import com.themikeste1.wabbits.core.tileentities.ConduitTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ConduitBlock extends FacedBlock implements IModHasBlockItem {
    public static final String regName = "conduit";

    public ConduitBlock() {
        super(regName, Properties.create(Material.CLAY)
                .hardnessAndResistance(2.5f, 30f)
        );
    }

    @Override
    public BlockItem generateModBlockItem() { return new AutoNamingBlockItem(this, new Item.Properties().group(ItemGroups.MAIN_GROUP_WABBITS)); }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new ConduitTileEntity(); }
}
