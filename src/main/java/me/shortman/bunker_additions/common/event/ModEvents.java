package me.shortman.bunker_additions.common.event;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.client.animation.ClientManualCombiningAnimation;
import me.shortman.bunker_additions.common.drug.alcohol.AlcoholEvents;
import me.shortman.bunker_additions.integration.ThirstIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;


@EventBusSubscriber(modid = BunkerAdditions.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        AlcoholEvents.onLevelTick(event);
    }

    @SubscribeEvent
    public static void  onServerTick(ServerTickEvent.Post event) {
        ManualCombiningEvents.onServerTick(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(ClientTickEvent.Post event) {
        ManualCombiningEvents.onClientTick(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderGui(RenderGuiEvent.Post event) {
        ManualCombiningEvents.onRenderGui(event);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        RegisterCapabilitiesEvents.registerAll(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        DebugEvents.onRightClickItem(event);
        ManualCombiningEvents.onRightClickItems(event);
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

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PacketRegistrationEvents.registerPayloadHandlers(event);
    }

    @SubscribeEvent
    public static void registerThirstValues(RegisterThirstValueEvent event) {
        ThirstIntegration.registerThirstItems(event);
    }
}
