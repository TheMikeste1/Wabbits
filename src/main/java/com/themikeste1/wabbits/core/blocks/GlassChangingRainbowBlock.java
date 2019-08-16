package com.themikeste1.wabbits.core.blocks;

import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.blockitems.ChangingRainbowBlockItem;
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GlassChangingRainbowBlock extends AbstractGlassBlock implements IChangesColorRainbowBlock, IModHasBlockItem, IBeaconBeamColorProvider {

    private int changeTimer;
    private BlockRenderLayer renderLayer;
    private DyeColor currentColor;

    public GlassChangingRainbowBlock(String registryName) {
        super(Block.Properties.create(Material.GLASS)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.3F)
        );
        setup(registryName, -1, BlockRenderLayer.CUTOUT);
    }

    public GlassChangingRainbowBlock(String registryName, int changeTimer) {
        super(Block.Properties.create(Material.GLASS)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.3F)
        );
        setup(registryName, changeTimer, BlockRenderLayer.CUTOUT);
    }

    public GlassChangingRainbowBlock(String registryName, Block.Properties properties) {
        super(properties);
        setup(registryName, -1, BlockRenderLayer.CUTOUT);
    }

    public GlassChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties) {
        super(properties);
        setup(registryName, changeTimer, BlockRenderLayer.CUTOUT);
    }

    public GlassChangingRainbowBlock(String registryName, BlockRenderLayer renderLayer) {
        super(Block.Properties.create(Material.GLASS)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.3F)
        );
        setup(registryName, -1, renderLayer);
    }

    public GlassChangingRainbowBlock(String registryName, int changeTimer, BlockRenderLayer renderLayer) {
        super(Block.Properties.create(Material.GLASS)
                .sound(SoundType.GLASS)
                .hardnessAndResistance(0.3F)
        );
        setup(registryName, changeTimer, renderLayer);
    }

    public GlassChangingRainbowBlock(String registryName, Block.Properties properties, BlockRenderLayer renderLayer) {
        super(properties);
        setup(registryName, -1, renderLayer);
    }

    public GlassChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties, BlockRenderLayer renderLayer) {
        super(properties);
        setup(registryName, changeTimer, renderLayer);
    }

    private void setup(String registryName, int changeTimer, BlockRenderLayer renderLayer) {
        setRegistryName(Constants.MOD_ID, registryName);
        this.setDefaultState(getDefaultState()
                .with(BlockStateProperties.RAINBOW_COLORS, DyeColor.MAGENTA));

        this.changeTimer = changeTimer;
        this.renderLayer = renderLayer;

        currentColor = DyeColor.MAGENTA;
    }

    public BlockRenderLayer getRenderLayer() {
        return renderLayer;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChangingRainbowTileEntity(changeTimer);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = tile.getBlockState();
            state = updateColor(state, worldIn, pos);
            currentColor = state.get(BlockStateProperties.RAINBOW_COLORS);
            tile.resetCounter();
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChangingRainbowTileEntity tile = (ChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved()) {
            state = updateColor(state, worldIn, pos);
            currentColor = state.get(BlockStateProperties.RAINBOW_COLORS);
        }
    }

    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        addRainbowColors(builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        currentColor = state.get(BlockStateProperties.RAINBOW_COLORS);
        return state;
    }




    @Override
    public BlockItem generateModBlockItem() {
        return new ChangingRainbowBlockItem(this);
    }

    @Override
    public DyeColor getColor() {
        return currentColor;
    }
}
