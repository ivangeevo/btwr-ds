package org.ivangeevo.btwr_ds.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public record CrawlToggleC2SPacket() implements CustomPayload {
    public static final CustomPayload.Id<CrawlToggleC2SPacket> ID =
            new CustomPayload.Id<>(Identifier.of(BTWRDSMod.MOD_ID, "crawl_toggle"));

    public static final PacketCodec<RegistryByteBuf, CrawlToggleC2SPacket> CODEC =
            PacketCodec.unit(new CrawlToggleC2SPacket());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }


}
