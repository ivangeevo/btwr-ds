package org.ivangeevo.btwr_ds.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class MagneticPointAttachedData {

    private boolean hasValid;
    private int posX;
    private int posZ;

    public MagneticPointAttachedData() {
        this(false, 0, 0);
    }

    public MagneticPointAttachedData(boolean hasValid, int x, int z) {
        this.hasValid = hasValid;
        this.posX = x;
        this.posZ = z;
    }

    public static final Codec<MagneticPointAttachedData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL
                            .fieldOf("hasValid")
                            .forGetter(MagneticPointAttachedData::hasValid),
                    Codec.INT
                            .fieldOf("x")
                            .forGetter(MagneticPointAttachedData::getPosX),
                    Codec.INT
                            .fieldOf("z")
                            .forGetter(MagneticPointAttachedData::getPosZ)
            ).apply(instance, MagneticPointAttachedData::new)
    );

    public static PacketCodec<ByteBuf, MagneticPointAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);


    public boolean hasValid() {
        return hasValid;
    }

    public void setHasValid(boolean valid) {
        hasValid = valid;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int x) {
        posX = x;
    }

    public int getPosZ() {
        return posX;
    }

    public void setPosZ(int z) {
        posZ = z;
    }

    // A default value we can use as an "empty" or reset data component
    public static MagneticPointAttachedData DEFAULT = new MagneticPointAttachedData(false, 0, 0);

}
