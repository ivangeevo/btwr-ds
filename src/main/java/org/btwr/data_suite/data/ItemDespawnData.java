package org.btwr.data_suite.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.btwr.data_suite.util.ItemDespawnType;

public record ItemDespawnData(ItemDespawnType type) {

    public static final Codec<ItemDespawnData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemDespawnType.CODEC.fieldOf("type").forGetter(ItemDespawnData::type)
    ).apply(inst, ItemDespawnData::new));

    public static final ItemDespawnData DEFAULT = new ItemDespawnData(ItemDespawnType.DEFAULT);
    public static final ItemDespawnData FULL_DAY = new ItemDespawnData(ItemDespawnType.FULL_DAY);

}