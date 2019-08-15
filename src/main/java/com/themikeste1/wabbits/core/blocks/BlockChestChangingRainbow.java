package com.themikeste1.wabbits.core.blocks;

import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.blockitems.BlockItemChangingRainbow;
import com.themikeste1.wabbits.core.tileentities.TileEntityChestChangingRainbow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockChestChangingRainbow extends ChestBlock implements IModHasBlockItem, IBlockChangesColorRainbow {
    private final int changeTimer;

    public BlockChestChangingRainbow(String registryName) {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.METAL)
        );
        setup(registryName);
        changeTimer = -1;
    }

    public BlockChestChangingRainbow(String registryName, int changeTimer) {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
        );
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    public BlockChestChangingRainbow(String registryName, Block.Properties properties) {
        super(properties);
        setup(registryName);
        changeTimer = -1;
    }

    public BlockChestChangingRainbow(String registryName, int changeTimer, Block.Properties properties) {
        super(properties);
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    private void setup(String registryName) {
        setRegistryName(Constants.MOD_ID, registryName);
        this.setDefaultState(getDefaultState()
                .with(BlockStateProperties.RAINBOW_COLORS, DyeColor.MAGENTA));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityChestChangingRainbow(changeTimer);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        TileEntityChestChangingRainbow tile = (TileEntityChestChangingRainbow) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = worldIn.getBlockState(pos);
            updateColor(state, worldIn, pos);
            tile.resetCounter();
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        TileEntityChestChangingRainbow tile = (TileEntityChestChangingRainbow) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved())
            updateColor(state, worldIn, pos);
    }

    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        addRainbowColors(builder);
    }

    /* ***************************************************************************
     * FalconAthenaeum
     ****************************************************************************/
    @Override
    public BlockItem generateModBlockItem() {
        return new BlockItemChangingRainbow(this);
    }
}
