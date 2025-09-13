package me.shortman.bunker_additions.common.network;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.client.animation.ClientManualCombiningAnimation;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// Packet to start crafting animation on client
public record StartManualCombiningPacket(int totalTicks, float progress) implements CustomPacketPayload {
    public static final Type<StartManualCombiningPacket> TYPE = new Type<>(BunkerAdditions.resource("start_crafting"));

    public static final StreamCodec<FriendlyByteBuf, StartManualCombiningPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, StartManualCombiningPacket::totalTicks,
            ByteBufCodecs.FLOAT, StartManualCombiningPacket::progress,
            StartManualCombiningPacket::new
    );

    @Override
    public Type<StartManualCombiningPacket> type() {
        return TYPE;
    }

    public static void handle(StartManualCombiningPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientManualCombiningAnimation.startCrafting(packet.totalTicks, packet.progress);
        });
    }
}

