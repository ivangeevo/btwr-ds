package org.btwr.data_suite.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.btwr.data_suite.util.ItemDespawnType;

public record ItemDespawnAttachedData(ItemDespawnType type) {
    public static final Codec<ItemDespawnAttachedData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemDespawnType.CODEC.fieldOf("type").forGetter(ItemDespawnAttachedData::type)
    ).apply(inst, ItemDespawnAttachedData::new));

    public static final ItemDespawnAttachedData DEFAULT = new ItemDespawnAttachedData(ItemDespawnType.DEFAULT);
    public static final ItemDespawnAttachedData FULL_DAY = new ItemDespawnAttachedData(ItemDespawnType.FULL_DAY);
}