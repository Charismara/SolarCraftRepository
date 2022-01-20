package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.magic_items.items.RuneItem;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RunicTableContainer extends AbstractContainerMenu {

    public IItemHandler inventory;
    public RunicTableTileEntity tile;
    public boolean hideRuneButtons;

    public RunicTableContainer(int windowId, Inventory playerInv,BlockPos tilepos,boolean hideRuneButtons) {
        super(Containers.RUNIC_TABLE_CONTAINER.get(), windowId);
        this.hideRuneButtons = hideRuneButtons;
        Level world= playerInv.player.level;
        this.tile = (RunicTableTileEntity)world.getBlockEntity(tilepos);
        this.inventory = tile.getInventory();


        this.addSlot(new FragmentSlot(inventory,0,8,24));
        int idx = 1;
        for (int g = 0; g < 2;g++) {
            for (int i = 0; i < 4; i++) {
                this.addSlot(new SlotItemHandler(inventory,idx,8+i*18,-14+g*18){
                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        return stack.getItem() instanceof RuneItem;
                    }
                });
                idx++;
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9,   8+j1 * 18, 103 + l * 18 -19));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1,  8+ i1 * 18, 161 -19));
        }


    }

    public RunicTableContainer(int p_38852_, Inventory inv, FriendlyByteBuf buf) {
        this(p_38852_,inv,buf.readBlockPos(),buf.readBoolean());
    }
    @Override
    public boolean stillValid(Player player) {
        if (player.isDeadOrDying()){
            return false;
        }
        return player.distanceToSqr(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ()) <= 100;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < inventory.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, inventory.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, inventory.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public static class Provider implements MenuProvider{

        private final RunicTableTileEntity tile;
        private final boolean hideButtons;

        public Provider(RunicTableTileEntity tile,boolean hideButtons) {
            this.tile = tile;
            this.hideButtons = hideButtons;
        }

        @Override
        public Component getDisplayName() {
            return new TextComponent("");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
            return new RunicTableContainer(id,inventory,tile.getBlockPos(),hideButtons);
        }
    }



}
class RuneSlot extends Slot{

    public RuneSlot(Container p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }


    @Override
    public boolean mayPlace(ItemStack stack) {
        Item item = stack.getItem();
        return (item == ItemsRegister.SOLAR_RUNE_ARDO.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_ZETA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_TERA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_URBA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_KELDA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_FIRA.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_GIRO.get()) ||
                (item == ItemsRegister.SOLAR_RUNE_ULTIMA.get());
    }
}
class FragmentSlot extends SlotItemHandler {

    public FragmentSlot(IItemHandler p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }


    @Override
    public boolean mayPlace(ItemStack stack) {
        Item item = stack.getItem();
        return (item == ItemsRegister.INFO_FRAGMENT.get()) && (stack.getTagElement(ProgressionHelper.TAG_ELEMENT) == null);
    }
}
