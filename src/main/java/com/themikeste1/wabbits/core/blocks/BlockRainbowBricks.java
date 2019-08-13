package com.themikeste1.wabbits.core.blocks;

//Java
import javax.annotation.Nullable;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.core.Constants;

import java.util.Random;

/**
 *
 */
public class BlockRainbowBricks extends Block implements IBlockColor, IModBlock {

    private int colorTimer = 0;

    public BlockRainbowBricks() {
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
        );
        setup();
    }

    protected void setup() {
        setRegistryName(Constants.MOD_ID, "rainbow_bricks");
        this.setDefaultState(getDefaultState()
                .with(BlockStateProperties.RAINBOW_COLORS, DyeColor.MAGENTA));
    }

/* ***************************************************************************
 * BlockStates
 ****************************************************************************/
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.RAINBOW_COLORS);
    }

/* ***************************************************************************
 * ModBlock
 ****************************************************************************/
    @Override
    public BlockItem getModBlockItem() { return new BlockItemRainbowBricks(); }

/* ***************************************************************************
 * IBlockColor
 ****************************************************************************/
    /* ***********************************************************************
     * getColor()
     * Returns an int which is multiplied to the color value of the pixels
     * of the texture of this block. This changes the color of the pixels.
     ************************************************************************/
    @Override
    public int getColor(BlockState blockState, @Nullable IEnviromentBlockReader environment, @Nullable BlockPos coordinates, int tintIndex) {
        return blockState.get(BlockStateProperties.RAINBOW_COLORS)
                .getMapColor().colorValue;
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
       // if (colorTimer <= 0) {
            updateColor(worldIn, pos);
        //    colorTimer = 1;
       // }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        updateColor(worldIn, pos);
        super.onBlockClicked(state, worldIn, pos, player);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (colorTimer > 0)
            colorTimer--;
    }

    private void updateColor(World worldIn, BlockPos pos) {
        DyeColor color = worldIn.getBlockState(pos)
                .cycle(BlockStateProperties.RAINBOW_COLORS)
                .get(BlockStateProperties.RAINBOW_COLORS);

        worldIn.setBlockState(pos,
                worldIn.getBlockState(pos)
                .with(BlockStateProperties.RAINBOW_COLORS, color));
    }

    /*-----------------------------Nested Classes-------------------------------*/

    /**
     * Manages the {@link net.minecraft.item.BlockItem} of {@link BlockRainbowBricks}.
     * All properties of the item can be set in the constructor. This could be
     * done as a function, but making a class allows adding custom functionality
     * to the {@link net.minecraft.item.BlockItem}
     * (e.g. making it explode after being held for too long).
     *
     * @version 1.0
     * @since 0.0.0.0
     * @author TheMikeste1
     * @see BlockRainbowBricks
     * @see BlockItem
     */
    public class BlockItemRainbowBricks extends BlockItem implements IItemColor {
        public BlockItemRainbowBricks() {
            super(BlockRainbowBricks.this,
                    new Item.Properties()
                    .group(ItemGroup.BUILDING_BLOCKS)
                    .rarity(Rarity.EPIC)
            );
            setup();
        }

        protected void setup() {
            ResourceLocation name = BlockRainbowBricks.this.getRegistryName();
            assert name != null;
            setRegistryName(name);
        } //setup()

        /* *******************************************************************
         * hasEffect()
         * Returning true makes the item have the enchant "shimmer" effect.
         ********************************************************************/
        @Override
        public boolean hasEffect(ItemStack itemStack) {
            return true;
        }

        /* *******************************************************************
         * getColor()
         * Returns an int which is multiplied to the color value of the pixels
         * of the texture of this item. This changes the color of the pixels.
         ********************************************************************/
        @Override
        public int getColor(ItemStack itemStack, int tintIndex) {
            return BlockRainbowBricks.this.getStateContainer().getBaseState()
                    .get(BlockStateProperties.RAINBOW_COLORS)
                    .getMapColor().colorValue;
        }
    } //class BlockItemRainbowBricks
} //class BlockRainbowBricks
