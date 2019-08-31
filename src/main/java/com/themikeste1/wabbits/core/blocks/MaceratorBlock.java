package com.themikeste1.wabbits.core.blocks;

import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.blockitems.AutoNamingBlockItem;
import com.themikeste1.wabbits.core.tileentities.MaceratorTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;



public class MaceratorBlock extends HorizontalFacedBlock implements IModHasBlockItem {
    public static final String regName = "macerator";

    public MaceratorBlock() {
        super(regName, Block.Properties
                .create(Material.CLAY)
                .hardnessAndResistance(2.5f, 30f)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return new MaceratorTileEntity(); }

    @Override
    public BlockItem generateModBlockItem() {
        return new AutoNamingBlockItem(this, new Item.Properties().group(ItemGroups.MAIN_GROUP_WABBITS));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof MaceratorTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            }
        }

        //Returning "true" blocks any other interactions from happening, such as
        //placing a block or using the wrench.
        return true;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        onBlockHarvested(world, pos, state, player);
        TileEntity tileEntity = world.getTileEntity(pos);
        ItemStack stack = ItemStack.EMPTY;
        if (tileEntity instanceof MaceratorTileEntity) {
//            stack = ((MaceratorTileEntity) tileEntity).getStackInSlot(0);
        }

        boolean removed = world.removeBlock(pos, false);
        if (removed) {
            InventoryHelper.spawnItemStack(
                    world,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    stack
            );
        }

        return removed;
    }
}
