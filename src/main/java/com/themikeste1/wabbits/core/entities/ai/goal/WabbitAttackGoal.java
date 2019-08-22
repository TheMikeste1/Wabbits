package com.themikeste1.wabbits.core.entities.ai.goal;

import com.themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class WabbitAttackGoal extends MeleeAttackGoal {
    private final WabbitEntity wabbit;

    public WabbitAttackGoal(WabbitEntity wabbit, double speed, boolean useLongMemory) {
        super(wabbit, speed, useLongMemory);
        this.wabbit = wabbit;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
        this.wabbit.setAggroed(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        if (this.attackTick < 10) {
            this.wabbit.setAggroed(true);
        } else {
            this.wabbit.setAggroed(false);
        }

    }
}
