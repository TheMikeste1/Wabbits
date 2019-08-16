package com.themikeste1.wabbits.client.renderer.tileentity;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;

//Minecraft
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.ChestModel;
import net.minecraft.client.renderer.tileentity.model.LargeChestModel;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

//Forge
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererChestChangingRainbowTileEntity extends ChestTileEntityRenderer {
    private static final String TEXTURE_RAINBOW = "wabbits:textures/entity/rainbowchest/rainbow";
    private static final String TEXTURE_RAINBOW_DOUBLE = "wabbits:textures/entity/rainbowchest/rainbow_double";

    @Override
    public void render(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        //Get the chest's blockstate if it has one, set it otherwise.
        BlockState blockstate = tileEntityIn.hasWorld() ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        //Is the chest Left, Right, or Single?
        ChestType chestType = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;

        //If the chest a Single or Right chest...
        if (chestType != ChestType.LEFT) {
            //Is the chest single or right?
            boolean isDouble = chestType != ChestType.SINGLE;
            //Get the model for the chest.
            ChestModel chestModel = this.getChestModel(tileEntityIn, destroyStage, isDouble);

            //If the chest being destroyed
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(isDouble ? 8.0F : 4.0F, 4.0F, 1.0F);
                GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            } else {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
            GlStateManager.scalef(1.0F, -1.0F, -1.0F);

            float f = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
            if ((double)Math.abs(f) > 1.0E-5D) {
                GlStateManager.translatef(0.5F, 0.5F, 0.5F);
                GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
                GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            }

            //Opening or closing?
            this.applyLidRotation(tileEntityIn, partialTicks, chestModel);

            chestModel.renderAll();

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            //If the chest being destroyed
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }
        } //if (chestType != ChestType.LEFT)
    } //render()

    private ChestModel getChestModel(TileEntity tileEntityIn, int destroyStage, boolean doubleChest) {
        //If the chest being destroyed
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
        } else {
            String resourceLocation;
            BlockState state = tileEntityIn.getBlockState();
            DyeColor color;

            try {
                color = state.get(BlockStateProperties.RAINBOW_COLORS);
            } catch (Exception e) {
                resourceLocation = doubleChest ? TEXTURE_RAINBOW_DOUBLE + ".png" : TEXTURE_RAINBOW + ".png";
                this.bindTexture(ResourceLocation.tryCreate(resourceLocation));
                return doubleChest ? new LargeChestModel() : new ChestModel();
            }

            resourceLocation = doubleChest ? TEXTURE_RAINBOW_DOUBLE + "_" + color.toString() + ".png"
                    : TEXTURE_RAINBOW + "_" + color.toString() + ".png";

            this.bindTexture(ResourceLocation.tryCreate(resourceLocation));
        }

        return doubleChest ? new LargeChestModel() : new ChestModel();
    }

    private void applyLidRotation(TileEntity p_199346_1_, float p_199346_2_, ChestModel p_199346_3_) {
        float f = ((IChestLid)p_199346_1_).getLidAngle(p_199346_2_);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        p_199346_3_.getLid().rotateAngleX = -(f * ((float)Math.PI / 2F));
    }
}
