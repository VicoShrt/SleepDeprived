package me.shortman.bunker_additions;

import me.shortman.bunker_additions.client.gui.creative_tabs.ModCreativeModeTabs;
import me.shortman.bunker_additions.common.Configuration;
import me.shortman.bunker_additions.common.entity.renderer.DryingTableEntityRenderer;
import me.shortman.bunker_additions.common.item.properties.ModItemProperties;
import me.shortman.bunker_additions.common.registry.*;
import me.shortman.bunker_additions.integration.FarmersDelightIntegration;
import me.shortman.bunker_additions.common.effect.ModEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BunkerAdditions.MOD_ID)
public class BunkerAdditions {
    public static final String MOD_ID = "bunker_additions";

    public static final Logger LOGGER = LogUtils.getLogger();

    public BunkerAdditions(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModSounds.register(modEventBus);

        ModDataComponents.register(modEventBus);

        ModAttributes.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        FarmersDelightIntegration.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.debug(MOD_ID + " server started...");
    }


    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.DRYING_TABLE_BLOCK_ENTITY.get(), DryingTableEntityRenderer::new);
        }
    }

}
