package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.network.StartManualCombiningPacket;
import me.shortman.bunker_additions.common.network.UpdateManualCombiningPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketRegistrationEvents {

    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(BunkerAdditions.MOD_ID);

        // Register packets that go TO the client (server -> client)
        registrar.playToClient(StartManualCombiningPacket.TYPE, StartManualCombiningPacket.STREAM_CODEC, StartManualCombiningPacket::handle);
        registrar.playToClient(UpdateManualCombiningPacket.TYPE, UpdateManualCombiningPacket.STREAM_CODEC, UpdateManualCombiningPacket::handle);
    }
}
