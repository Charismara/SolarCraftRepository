package com.finderfeed.solarforge.other_events;

import com.finderfeed.solarforge.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.capability_mana.SolarManaCapability;
import com.finderfeed.solarforge.hotkey_use_event.HotkeyEventListener;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//,value = Dist.CLIENT
@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HudRenderMana {

    @SubscribeEvent
    public static void gameOverlay(final RenderGameOverlayEvent event){
        Minecraft mc = Minecraft.getInstance();




        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
           if (!mc.player.isDeadOrDying() && mc.player.getMainHandItem().getItem() instanceof ManaConsumer) {
               int height = event.getWindow().getGuiScaledHeight();
               int width = event.getWindow().getGuiScaledWidth();
               double mana = CapabilitySolarMana.getSolarMana(mc.player).orElseThrow(RuntimeException::new).getMana();
               double percent = mana / 3000;
               mc.textureManager.bind(new ResourceLocation("solarforge","textures/misc/mana_hud.png"));
               //1 x 2 y 3 x move 4 y move 5 x cut 6 y cut 7 8 texture size x y
               AbstractGui.blit(event.getMatrixStack(),width-32,height/2-30,100,22,0,10,(int)(22*percent),40,40);
               AbstractGui.blit(event.getMatrixStack(),width-38,height/2-37,100,0,0,22,40,40,40);

               AbstractGui.drawCenteredString(event.getMatrixStack(), mc.gui.getFont(), Integer.toString((int)mana), width-25,height/2, 0xff002b);
               AbstractGui.drawString(event.getMatrixStack(), mc.gui.getFont(), "/3000", width-43,height/2+10, 0xff002b);
           }
            if (mc.player.getPersistentData().getBoolean("is_alchemist_toggled")){
                int height = event.getWindow().getGuiScaledHeight();
                int width = event.getWindow().getGuiScaledWidth();
                mc.textureManager.bind(new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png"));
                AbstractGui.blit(event.getMatrixStack(),0,height/2-30,100,0,27,27,38,180,185);
            }

        }


    }


}
