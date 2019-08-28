package com.themikeste1.wabbits.core;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.IProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

//TODO: Figure out how to move this to my own library without crashing when
// the functions are called.

public class Functions {
    public static Direction getDirectionAwayFromEntity(BlockPos testingBlockPosition, LivingEntity entity) {
        return Direction.getFacingFromVector(
                (float) (testingBlockPosition.getX() - entity.posX),
                (float) (testingBlockPosition.getY() - entity.posY),
                (float) (testingBlockPosition.getZ() - entity.posZ)
        );
    }

    public static Direction getDirectionTowardsEntity(BlockPos testingBlockPosition, LivingEntity entity) {
        return Direction.getFacingFromVector(
                (float) (entity.posX - testingBlockPosition.getX()),
                (float) (entity.posY - testingBlockPosition.getY()),
                (float) (entity.posZ - testingBlockPosition.getZ())
        );
    }

    public static Direction getHorizontalDirectionAwayFromEntity(BlockPos testingBlockPosition, LivingEntity entity) {
        return Direction.getFacingFromVector(
                (float) (testingBlockPosition.getX() - entity.posX),
                0.0,
                (float) (testingBlockPosition.getZ() - entity.posZ)
        );
    }

    public static Direction getHorizontalDirectionTowardsEntity(BlockPos testingBlockPosition, LivingEntity entity) {
        return Direction.getFacingFromVector(
                (float) (entity.posX - testingBlockPosition.getX()),
                0.0,
                (float) (entity.posZ - testingBlockPosition.getZ())
        );
    }

    public static BlockState cycleBackwards(BlockState state, IProperty property) {
        //There's probably a better way to do this..
        BlockState newState = state.cycle(property);

        while (newState.cycle(property).get(property) != state.get(property)) {
            newState = newState.cycle(property);
        }

        state = newState;

        return state;
    }

    public static void setRotationOffset(RendererModel renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }
}
