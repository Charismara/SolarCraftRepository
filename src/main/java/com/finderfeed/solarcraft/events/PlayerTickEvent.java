package com.finderfeed.solarcraft.events;


import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvent {

//    @SubscribeEvent
//    public void replaceIncineratedForestEnter(BlockEvent.BreakEvent event){
//        Player p = event.getPlayer();
//        if (event.getState().is(Blocks.DIAMOND_BLOCK)){
//            Helpers.fireProgressionEvent(p, Progression.FIND_INCINERATED_FOREST);
//        }
//    }

    @SubscribeEvent
    public void enterIncineratedForest(final TickEvent.PlayerTickEvent event){
        Player entity = event.player;
        if (!entity.level.isClientSide && event.phase == TickEvent.Phase.START){

                //TODO:return this when adding biomes to overworld will be possible and also correct things in en_ru.json
//            Optional<? extends Registry<Biome>> reg = entity.level.registryAccess().registry(Registry.BIOME_REGISTRY);
//            if ( reg.isPresent() && entity.level.getBiome(entity.blockPosition()).equals(reg.get().get(FeaturesRegistry.MOLTEN_BIOME_KEY))){
//
//                Helpers.fireProgressionEvent(entity, Progression.FIND_INCINERATED_FOREST);
//
//            }
            if (entity.level.getGameTime() % 200 == 0){
                Helpers.updateFragmentsOnClient((ServerPlayer) entity);
                Helpers.updateProgression((ServerPlayer) entity);
            }
            if (entity.level.getGameTime() % 20 == 0) {
                if (entity.level.dimension() == Level.NETHER) {
                    Helpers.fireProgressionEvent(entity, Progression.ENTER_NETHER);
                }
            }
        }


    }
}
