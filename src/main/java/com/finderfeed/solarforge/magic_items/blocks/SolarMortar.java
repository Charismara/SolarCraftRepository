package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SolarMortar extends Block {
    public SolarMortar(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.MORTAR_TILE_ENTITY.get().create();
    }



}
