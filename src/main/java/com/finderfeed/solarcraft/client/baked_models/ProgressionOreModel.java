package com.finderfeed.solarcraft.client.baked_models;



import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.IProgressionBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.data.ModelData;


import javax.annotation.Nonnull;
import java.util.List;


public class ProgressionOreModel implements BakedModel {

    public BakedModel model;

    public ProgressionOreModel(BakedModel model){
        this.model = model;
    }




//    @Nonnull
//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
//
//        if (state != null && (Minecraft.getInstance().player != null) ) {
//            if ((state.getBlock() instanceof IProgressionBlock) && !Helpers.hasPlayerCompletedProgression(((IProgressionBlock) state.getBlock()).getRequiredProgression(), Minecraft.getInstance().player)) {
//                BlockState lockedState = ((IProgressionBlock) state.getBlock()).getLockedBlock().defaultBlockState();
//
//                return Minecraft.getInstance().getBlockRenderer().getBlockModel(lockedState)
//                        .getQuads(lockedState,side,rand,extraData);
//            }
//        }
//        return model.getQuads(state,side,rand,extraData);
//    }
//
//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
//
//        return getQuads(state,side,rand,EmptyModelData.INSTANCE);
//    }

    @Override
    public List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction direction, RandomSource src) {
        if (state != null && (Minecraft.getInstance().player != null) ) {
            if ((state.getBlock() instanceof IProgressionBlock) && !Helpers.hasPlayerCompletedProgression(((IProgressionBlock) state.getBlock()).getRequiredProgression(), Minecraft.getInstance().player)) {
                BlockState lockedState = ((IProgressionBlock) state.getBlock()).getLockedBlock().defaultBlockState();

                return Minecraft.getInstance().getBlockRenderer().getBlockModel(lockedState)
                        .getQuads(lockedState,direction,src, ModelData.EMPTY,null);
            }
        }
        return model.getQuads(state,direction,src, ModelData.EMPTY,null);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getParticleIcon(ModelData.EMPTY);
    }


    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull ModelData data) {

        return model.getParticleIcon(data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return model.getOverrides();
    }
}
