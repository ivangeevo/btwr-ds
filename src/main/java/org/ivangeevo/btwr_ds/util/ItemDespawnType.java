package org.ivangeevo.btwr_ds.util;

import com.mojang.serialization.Codec;

public enum ItemDespawnType {


    DEFAULT(6000),
    FULL_DAY(24000),
    PERSIST_UNTIL_PLAYER_REDEATH(1),
    NEVER(-1);

    private final int despawnTime;

    ItemDespawnType(int despawnTime) {
        this.despawnTime = despawnTime;
    }

    public int getDespawnTime() {
        return despawnTime;
    }

    public static final Codec<ItemDespawnType> CODEC = Codec.STRING.xmap(
        ItemDespawnType::valueOf,
        Enum::name
    );
}
