package com.finderfeed.solarforge.magic_items.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWithGlint extends Item {
    public ItemWithGlint(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return true;
    }


}
