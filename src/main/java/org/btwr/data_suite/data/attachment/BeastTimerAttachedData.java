package org.btwr.data_suite.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.random.Random;
import org.btwr.data_suite.entity.ModEntityTypes;
import org.btwr.data_suite.entity.entities.BeastEntity;
import org.btwr.shared_library.api.data.UpdateRequiringData;

public class BeastTimerAttachedData extends UpdateRequiringData<WolfEntity> {
    private boolean ateRottenFlesh;
    private int timer;

    // how long it takes to convert a wolf to a beast after eating rotten flesh
    //public static final int CONVERSION_TIME = 24000 / 4; // 5 mins
    public static final int CONVERSION_TIME = 60; // 3 secs

    private static final int MINIMUM_INFECTION_TIME = 12000;
    private static final int INFECTION_TIME_VARIANCE = 12000;

    public BeastTimerAttachedData(boolean ateRottenFlesh, int timer) {
        this.ateRottenFlesh = ateRottenFlesh;
        this.timer = timer;
    }

    public static final Codec<BeastTimerAttachedData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.BOOL.fieldOf("ateRottenFlesh").forGetter(BeastTimerAttachedData::getAteRottenFlesh),
            Codec.INT.fieldOf("timer").forGetter(BeastTimerAttachedData::getTimer)
    ).apply(inst, BeastTimerAttachedData::new));

    public static PacketCodec<ByteBuf, BeastTimerAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public boolean getAteRottenFlesh() {
        return ateRottenFlesh;
    }

    public void setAteRottenFlesh(boolean value) {
        ateRottenFlesh = value;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int value) {
        timer = value;
    }

    @Override
    public void tick(WolfEntity wolf) {
        if (ateRottenFlesh) {
            timer--;

            if (timer <= 0) {
                tryConvertToBeast(wolf);
            }
        }
    }

    public static int randomConversionTime(Random rand) {
        return MINIMUM_INFECTION_TIME + rand.nextInt(INFECTION_TIME_VARIANCE);
    }

    public void tryConvertToBeast(WolfEntity wolf) {
        if (wolf.getWorld().isClient) return; // Only run on the server

        // Play transformation particle/sound effect
        wolf.getWorld().sendEntityStatus(wolf, (byte) 60); // optional: custom byte for your effect

        // Remove the original wolf
        wolf.remove(Entity.RemovalReason.DISCARDED);

        // Create the Beast entity
        BeastEntity beast = ModEntityTypes.BEAST.create(wolf.getWorld());
        if (beast == null) return;

        // Set Beast position and rotation to match Wolf
        beast.refreshPositionAndAngles(wolf.getX(), wolf.getY(), wolf.getZ(), wolf.getYaw(), wolf.getPitch());
        beast.setYaw(wolf.getYaw());
        beast.setPitch(wolf.getPitch());

        // Make persistent so it doesn’t despawn
        beast.setPersistent();

        // Spawn the Beast in the world
        wolf.getWorld().spawnEntity(beast);
    }
}
