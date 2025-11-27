package org.btwr.data_suite.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class CrawlingToggleData {

    public static Codec<CrawlingToggleData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("is_crawling").forGetter(CrawlingToggleData::getCrawling)
            ).apply(instance, CrawlingToggleData::new)
    );

    boolean isCrawling;

    public CrawlingToggleData(boolean isCrawling) {
        this.isCrawling = isCrawling;
    }

    public static PacketCodec<ByteBuf, CrawlingToggleData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean getCrawling() {
        return isCrawling;
    }

    public void setCrawling(boolean isCrawling) {
        this.isCrawling = isCrawling;
    }

}