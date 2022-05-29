package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class DisarmAbility extends AbstractAbility{
    public DisarmAbility() {
        super("solar_stun",new RunicEnergyCost()
        .set(RunicEnergy.Type.URBA,450)
        .set(RunicEnergy.Type.KELDA,600),45000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AABB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new MobEffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
            AbilityHelper.spendAbilityEnergy(entity,this);
        }else {
            if (AbilityHelper.isAbilityBought(entity,this)){
                AbilityHelper.notEnoughEnergyMessage(entity,this);
            }
        }
    }
}
