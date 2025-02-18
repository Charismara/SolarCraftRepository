package com.finderfeed.solarcraft.content.commands;


import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarcraft")
public class ServerStartEvent {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event){
        SolarCraftCommands.register(event.getDispatcher());
    }
}
