package com.themikeste1.wabbits.core.blocks;

import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.blockitems.ChestChangingRainbowBlockItem;
import com.themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


public class ChestChangingRainbowBlock extends ChestBlock implements IModHasBlockItem, IChangesColorRainbowBlock {
    private final int changeTimer;

    public ChestChangingRainbowBlock(String registryName) {
        super(Block.Properties
                .create(Material.CLAY)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.METAL)
        );
        setup(registryName);
        changeTimer = -1;
    }

    public ChestChangingRainbowBlock(String registryName, int changeTimer) {
        super(Block.Properties
                .create(Material.CLAY)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.METAL)
        );
        setup(registryName);
        this.changeTimer = changeTimer;
    }

    public ChestChangingRainbowBlock(String registryName, Block.Properties properties) {
        super(properties);
        setup(registryName);
        changeTimer = -1;
    }

    public ChestChangingRainbowBlock(String registryName, int changeTimer, Block.Properties properties) {
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
        return new ChestChangingRainbowTileEntity(changeTimer);
    }



    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChestChangingRainbowTileEntity tile = (ChestChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved() && tile.canChange()) {
            BlockState state = worldIn.getBlockState(pos);
            state = updateColor(state, worldIn, pos);
            updatePartner(state, worldIn, pos, BlockStateProperties.RAINBOW_COLORS);
            tile.resetCounter();
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChestChangingRainbowTileEntity tile = (ChestChangingRainbowTileEntity) worldIn.getTileEntity(pos);
        if (tile != null && !tile.isRemoved()) {
            state = updateColor(state, worldIn, pos);
            updatePartner(state, worldIn, pos, BlockStateProperties.RAINBOW_COLORS);
        }
    }

    protected void updatePartner(BlockState state, World worldIn, BlockPos pos, IProperty property) {
        if (state.get(TYPE) == ChestType.SINGLE)
            return;

        assert property != null;

        Direction direction = getDirectionToAttached(state);

        pos = pos.add(
                direction.getXOffset(),
                direction.getYOffset(),
                direction.getZOffset()
        );

        try {
            state = worldIn.getBlockState(pos).with(property, state.get(property));
            worldIn.setBlockState(pos, state);
        } catch (Exception e) {
            LOGGER.warn(e);
            throw e;
        }
    }


    /* ***************************************************************************
     * BlockStates
     ****************************************************************************/
    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        addRainbowColors(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);

        if (state.get(TYPE) != ChestType.SINGLE) {
            BlockPos pos = context.getPos();

            Direction direction = context.getPlacementHorizontalFacing();

            if (state.get(TYPE) == ChestType.RIGHT) {
                direction = direction.rotateY();
            } else {
                direction = direction.rotateYCCW();
            }

            World world = context.getWorld();
            assert world != null;

            TileEntity otherChest =
                    world.getTileEntity(
                            pos.add(
                                    direction.getXOffset(),
                                    direction.getYOffset(),
                                    direction.getZOffset()
                            )
                    );
            assert otherChest != null;
            assert otherChest instanceof ChestChangingRainbowTileEntity;

            state = state.with(BlockStateProperties.RAINBOW_COLORS,
                    otherChest.getBlockState()
                            .get(BlockStateProperties.RAINBOW_COLORS)
            );
        }

        return state;
    }


    /* ***************************************************************************
     * FalconAthenaeum
     ****************************************************************************/
    @Override
    public BlockItem generateModBlockItem() {
        return new ChestChangingRainbowBlockItem(this);
    }

}
