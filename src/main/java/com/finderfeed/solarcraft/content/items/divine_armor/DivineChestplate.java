package com.finderfeed.solarcraft.content.items.divine_armor;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DivineChestplate extends BaseDivineArmor{
    public DivineChestplate(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, p_40387_, p_40388_, fragmentSupplier);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity e, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, world, e, p_41407_, p_41408_);
        if (world.isClientSide) return;
        if (e instanceof Player player){
            if (player.getAbilities().flying){
                tick(stack);
            }else {
                tickBackwards(stack);
            }
        }
    }



    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (level.isClientSide) return;
        if (level.getGameTime() % 20 != 0) return;
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 1, false, false));

    }

    @Override
    public float getMaxRunicEnergyCapacity() {
        return 2000;
    }
}
