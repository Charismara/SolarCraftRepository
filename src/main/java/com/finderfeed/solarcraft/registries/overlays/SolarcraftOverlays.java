package com.finderfeed.solarcraft.registries.overlays;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.CoreShaders;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.blocks.solar_energy.Bindable;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyContainer;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_wand.IWandable;
import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.content.items.UltraCrossbowItem;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.CustomBossBarRenderer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.wand_actions.SolarCraftWandActionRegistry;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.*;

import static com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler.*;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SolarcraftOverlays {


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("solar_wand",new SolarWand());
        event.registerAboveAll("ultra_crossbow",new UltraCrossbow());
        event.registerAboveAll("runic_energy_bars",new RunicEnergyBars());
        event.registerAboveAll("flash",new Flash());
        event.registerBelow(new ResourceLocation(SolarCraft.MOD_ID,"flash"),"boss_bars",new BossBars());
    }

    public static class BossBars implements IGuiOverlay{

        private static final Map<String, CustomBossBarRenderer> BOSS_BAR_REGISTRY = new HashMap<>();
        private static final Map<UUID, ActiveBossBar> BOSS_BARS = new LinkedHashMap<>();


        @Override
        public void render(ForgeGui gui, PoseStack matrices, float partialTick, int screenWidth, int screenHeight) {
            if (!BOSS_BARS.isEmpty()) {
                Minecraft mc = gui.getMinecraft();
                Window window = mc.getWindow();
                int scaledWidth = window.getGuiScaledWidth();
                int s = gui.getBossOverlay().events.size();
                int yShift = 5 + Math.min(s, 4)*20;

                for (ActiveBossBar bossBar : BOSS_BARS.values()){
                    CustomBossBarRenderer cst = BOSS_BAR_REGISTRY.get(bossBar.getCustomBarId());
                    if (cst == null) throw new IllegalStateException("Custom boss bar not registered: " + bossBar.getCustomBarId());
                    cst.render(matrices,scaledWidth/2,yShift,bossBar.getName(),bossBar.getProgress(),bossBar.getEntity(Minecraft.getInstance().level));
                    int barHeight = cst.getHeight();
                    yShift += barHeight;

                }

            }
        }

        public static void registerCustomBossBar(String id, CustomBossBarRenderer bossBar){
            BOSS_BAR_REGISTRY.put(id,bossBar);
        }

        public static void removeBossBar(UUID uuid){
            BOSS_BARS.remove(uuid);
        }

        public static void addBossBar(ActiveBossBar bossBar){
            BOSS_BARS.put(bossBar.getUUID(),bossBar);
        }

        public static ActiveBossBar getBossBar(UUID uuid){
            return BOSS_BARS.get(uuid);
        }
    }

    public static class RunicEnergyBars implements IGuiOverlay{

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            Minecraft mc = Minecraft.getInstance();



            List<ResourceLocation> locationsToRender = new ArrayList<>();
            for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()){
                if (ability.isToggled(mc.player)){
                    locationsToRender.add(new ResourceLocation(SolarCraft.MOD_ID,"textures/abilities/" + ability.id + ".png"));
                }
            }
            int initYPos = mc.getWindow().getGuiScaledHeight()/2 - 10 - (locationsToRender.size()-1)*20;
            int initXPos = mc.getWindow().getGuiScaledWidth() - 20;
            for (int i = 0; i < locationsToRender.size();i++) {
                ResourceLocation location = locationsToRender.get(i);
                ClientHelpers.bindText(location);
                Gui.blit(poseStack, initXPos, initYPos + i * 20, 0, 0, 20, 20, 20, 20);
            }

            if (mc.player.getMainHandItem().getItem() instanceof SolarWandItem){
                int height = mc.getWindow().getGuiScaledHeight();
                int width = mc.getWindow().getGuiScaledWidth();
                RenderingTools.renderRuneEnergyOverlay(poseStack,2,height/2-43, RunicEnergy.Type.KELDA);
                RenderingTools.renderRuneEnergyOverlay(poseStack,14,height/2-43, RunicEnergy.Type.ARDO);
                RenderingTools.renderRuneEnergyOverlay(poseStack,26,height/2-43, RunicEnergy.Type.ZETA);
                RenderingTools.renderRuneEnergyOverlay(poseStack,2,height/2+15, RunicEnergy.Type.FIRA);
                RenderingTools.renderRuneEnergyOverlay(poseStack,14,height/2+15, RunicEnergy.Type.TERA);
                RenderingTools.renderRuneEnergyOverlay(poseStack,26,height/2+15, RunicEnergy.Type.URBA);
                RenderingTools.renderRuneEnergyOverlay(poseStack,38,height/2-43, RunicEnergy.Type.GIRO);
                RenderingTools.renderRuneEnergyOverlay(poseStack,38,height/2+15, RunicEnergy.Type.ULTIMA);
            }


        }
    }

    public static class Flash implements IGuiOverlay{

        @Override
        public void render(ForgeGui gui, PoseStack matrices, float partialTick, int screenWidth, int screenHeight) {
            if (currentFlashEffect == null) return;

            float scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            float scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            float alpha = 1f;
            if (currentFlashEffect.getTicker() <= currentFlashEffect.getInTime()){
                alpha = currentFlashEffect.getTicker() / (float) currentFlashEffect.getInTime();
            }else if (currentFlashEffect.getTicker() >= currentFlashEffect.getAllTime() - currentFlashEffect.getOutTime()){
                alpha = 1f - (currentFlashEffect.getTicker() - currentFlashEffect.getStayTime() - currentFlashEffect.getInTime())/(float)currentFlashEffect.getOutTime();
            }
            RenderSystem.enableBlend();
            RenderSystem.disableTexture();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            BufferBuilder b = Tesselator.getInstance().getBuilder();
            b.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            Matrix4f m = matrices.last().pose();

            b.vertex(m,0,scaledHeight,0).color(1f,1f,1f,alpha).endVertex();
            b.vertex(m,scaledWidth,scaledHeight,0).color(1f,1f,1f,alpha).endVertex();
            b.vertex(m,scaledWidth,0,0).color(1f,1f,1f,alpha).endVertex();
            b.vertex(m,0,0,0).color(1f,1f,1f,alpha).endVertex();
//        b.end();
            BufferUploader.drawWithShader(b.end());
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
        }
    }

    public static class UltraCrossbow implements IGuiOverlay{
        public static ResourceLocation LOC = new ResourceLocation("solarcraft","textures/misc/ultra_crossbow_load.png");
        public static ResourceLocation PRICEL = new ResourceLocation("solarcraft","textures/misc/solar_crossbow_pricel.png");

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            Minecraft mc = Minecraft.getInstance();
                if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {
                    PoseStack stack = poseStack;
                    Window window = gui.getMinecraft().getWindow();
                    ClientHelpers.bindText(PRICEL);

                    RenderSystem.enableBlend();

                    RenderSystem.setShaderColor(1,1,0.3f,0.5f);
                    int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                    int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                    GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                    RenderSystem.setShaderColor(1,1,1f,1f);
                }else if( Minecraft.getInstance().player.getOffhandItem().getItem() instanceof UltraCrossbowItem){
                    PoseStack stack = poseStack;
                    Window window = gui.getMinecraft().getWindow();
                    ClientHelpers.bindText(PRICEL);
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1,1,0.3f,0.5f);

                    int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                    int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                    GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                    RenderSystem.setShaderColor(1,1,1f,1f);
                }


        }
    }

    public static class SolarWand implements IGuiOverlay{

        public static final ResourceLocation LOC = new ResourceLocation("solarcraft", "textures/misc/wand_crafting_progress.png");

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            Minecraft event = gui.getMinecraft();
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player.getMainHandItem().getItem() instanceof SolarWandItem) {
                HitResult re = mc.hitResult;
                int height = event.getWindow().getGuiScaledHeight();
                int width = event.getWindow().getGuiScaledWidth();
                if (re instanceof BlockHitResult result) {
                    BlockEntity tile = player.level.getBlockEntity(result.getBlockPos());
                    Block block = player.level.getBlockState(result.getBlockPos()).getBlock();
                    if (tile instanceof InfuserTileEntity tileInfusing) {
                        ClientHelpers.bindText(LOC);
                        if (tileInfusing.RECIPE_IN_PROGRESS) {
                            double percent = (float) tileInfusing.CURRENT_PROGRESS / tileInfusing.INFUSING_TIME;
                            GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 11, 0, 9, (int) (40 * percent), 3, 40, 20);
                        }
                        GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                        GuiComponent.drawCenteredString(poseStack,mc.font,"Recipe Progress",width/2,height / 2 + 20,0xffffff);
                    }

                    WandAction<?> action = SolarWandItem.getCurrentAction(player.getMainHandItem());

                    if (((  action == SolarCraftWandActionRegistry.ON_BLOCK_USE)
                            && (block instanceof IWandable || tile instanceof IWandable))

                            || ((action == SolarCraftWandActionRegistry.CHECK_RUNIC_NETWORK_CONNECTIVITY)
                            && (tile instanceof BaseRepeaterTile || tile instanceof AbstractRunicEnergyContainer))

                            || ((action == SolarCraftWandActionRegistry.CHECK_SOLAR_ENERGY_WAND_ACTION)
                            && (tile instanceof SolarEnergyContainer))

                            || ((action == SolarCraftWandActionRegistry.SOLAR_NETWORK_BINDER_WAND_ACTION)
                            && (tile instanceof Bindable))
                    ) {
                        ItemStack stack = SolarcraftItems.SOLAR_WAND.get().getDefaultInstance();
                        SolarWandItem.setWandAction(stack,action.getRegistryName());
                        RenderingTools.renderScaledGuiItemCentered(stack,
                                width / 2f + 16, height / 2f - 1, 1f, 0);
                    }


                }

            }
        }
    }
}
