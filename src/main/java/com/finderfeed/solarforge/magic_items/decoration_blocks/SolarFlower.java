package com.finderfeed.solarforge.magic_items.decoration_blocks;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class SolarFlower extends DeadBushBlock {
    public SolarFlower(Properties p_i48418_1_) {
        super(p_i48418_1_);
    }


    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_4_.nextInt(9) == 0) {
            Direction direction = Direction.getRandom(p_180655_4_);
            if (direction != Direction.UP) {
                BlockPos blockpos = p_180655_3_.relative(direction);
                BlockState blockstate = p_180655_2_.getBlockState(blockpos);
                if (!p_180655_1_.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, direction.getOpposite())) {

                    p_180655_2_.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(), (double)p_180655_3_.getX()+0.5f +(p_180655_2_.random.nextFloat()*0.4)-0.2, (double)p_180655_3_.getY() +0.5+(p_180655_2_.random.nextFloat()*0.4)-0.2, (double)p_180655_3_.getZ()+0.5f +(p_180655_2_.random.nextFloat()*0.4)-0.2, 0.0D, 0.01D, 0.0D);
                }
            }
        }
    }
}
