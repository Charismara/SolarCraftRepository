package com.finderfeed.solarcraft.client.toasts;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;

public class ProgressionToast implements Toast {

    public Progression progression;

    public ProgressionToast(Progression a){
        this.progression = a;
    }


    public static ResourceLocation LOC = new ResourceLocation("solarcraft","textures/gui/solar_forge_toasts.png");

    @Override
    public Visibility render(PoseStack matrices, ToastComponent gui, long timer) {
        Minecraft mc = gui.getMinecraft();

        ClientHelpers.bindText(LOC);

        gui.blit(matrices, 0, 0, 0, 32, this.width(), this.height());
        mc.getItemRenderer().renderGuiItem(progression.getIcon(),8,8);
        mc.font.draw(matrices, progression.getTranslation(),30,8,0xffffff);
        mc.font.draw(matrices,Component.translatable("ach.completed"),30,17,0xffffff);
        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }
    }



    public static void addOrUpdate(ToastComponent gui, Progression ach){

        gui.addToast(new ProgressionToast(ach));

    }
}
