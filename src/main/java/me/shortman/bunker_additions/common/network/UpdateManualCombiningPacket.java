package me.shortman.bunker_additions.common.network;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.client.animation.ClientManualCombiningAnimation;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// Packet to update crafting progress
public record UpdateManualCombiningPacket(float progress, boolean completed, boolean cancelled) implements CustomPacketPayload {
    public static final Type<UpdateManualCombiningPacket> TYPE = new Type<>(BunkerAdditions.resource("update_crafting"));

    public static final StreamCodec<FriendlyByteBuf, UpdateManualCombiningPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, UpdateManualCombiningPacket::progress,
            ByteBufCodecs.BOOL, UpdateManualCombiningPacket::completed,
            ByteBufCodecs.BOOL, UpdateManualCombiningPacket::cancelled,
            UpdateManualCombiningPacket::new
    );

    @Override
    public Type<UpdateManualCombiningPacket> type() {
        return TYPE;
    }

    public static void handle(UpdateManualCombiningPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientManualCombiningAnimation.updateCrafting(packet.progress, packet.completed, packet.cancelled);
        });
    }
}
