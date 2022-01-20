package com.finderfeed.solarforge.client.screens.buttons;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class RuneButtonRunicTable extends Button {
    public boolean turnedOff = false;
    public static final ResourceLocation RUNIC_BUTTONS = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/rune_buttons.png");
    public int rune;

    public RuneButtonRunicTable(int xPos, int yPos, int xSize, int ySize, OnPress onPress, OnTooltip onTooltip, int xoffs) {
        super(xPos, yPos, xSize, ySize, new TextComponent(""), onPress, onTooltip);
        this.rune = xoffs;
    }


    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float pticks) {
        ClientHelpers.bindText(RUNIC_BUTTONS);
        int i = this.isHoveredOrFocused() ? 15 : 0;
        matrices.pushPose();

        blit(matrices,x,y,rune*15,i,15,15,176*15/22,44*15/22);
        matrices.popPose();
    }


    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }
}
