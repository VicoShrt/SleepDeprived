package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.drug.alcohol.AlcoholEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;


@EventBusSubscriber(modid = BunkerAdditions.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        AlcoholEvents.onLevelTick(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        DebugEvents.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        AttributeEvents.modifyDefaultAttributes(event);
    }



    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        TooltipEvents.onItemTooltip(event);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public static void onItemAttributeModifierEvent(ItemAttributeModifierEvent event) {

    }





}
