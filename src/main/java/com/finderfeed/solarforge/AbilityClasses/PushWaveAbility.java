package com.finderfeed.solarforge.AbilityClasses;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class PushWaveAbility extends AbstractAbility{
    public PushWaveAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        super.cast(entity, world);
    }
}
