package org.btwr.data_suite.ending;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.btwr.shared_library.api.data.UpdateRequiringData;

public class LiminalPlayerState extends UpdateRequiringData<PlayerEntity> {

    private final boolean active;
    private final boolean satInChair;
    private final boolean placedBlock;
    private final boolean lookedOutside;
    private final int progression;

    public static final LiminalPlayerState DEFAULT =
            new LiminalPlayerState(
                    false,
                    false,
                    false,
                    false,
                    0
            );

    public static final Codec<LiminalPlayerState> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.BOOL
                                    .fieldOf("active")
                                    .forGetter(LiminalPlayerState::active),

                            Codec.BOOL
                                    .fieldOf("sat_in_chair")
                                    .forGetter(LiminalPlayerState::satInChair),

                            Codec.BOOL
                                    .fieldOf("placed_block")
                                    .forGetter(LiminalPlayerState::placedBlock),

                            Codec.BOOL
                                    .fieldOf("looked_outside")
                                    .forGetter(LiminalPlayerState::lookedOutside),

                            Codec.INT
                                    .fieldOf("progression")
                                    .forGetter(LiminalPlayerState::progression)
                    ).apply(instance, LiminalPlayerState::new)
            );

    public static final PacketCodec<ByteBuf, LiminalPlayerState> PACKET_CODEC =
            PacketCodecs.codec(CODEC);

    public LiminalPlayerState(
            boolean active,
            boolean satInChair,
            boolean placedBlock,
            boolean lookedOutside,
            int progression
    ) {
        this.active = active;
        this.satInChair = satInChair;
        this.placedBlock = placedBlock;
        this.lookedOutside = lookedOutside;
        this.progression = progression;
    }

    public boolean active() {
        return active;
    }

    public boolean satInChair() {
        return satInChair;
    }

    public boolean placedBlock() {
        return placedBlock;
    }

    public boolean lookedOutside() {
        return lookedOutside;
    }

    public int progression() {
        return progression;
    }
}