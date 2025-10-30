package org.ivangeevo.btwr_ds.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import issame.material_beacons.config.BeaconConfig;
import issame.material_beacons.config.BlockOrTag;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;
import java.util.List;

public class BeaconConfigBuilder {

    private final List<String> bases = new ArrayList<>();
    private final List<List<BeaconConfig.EffectConfig>> powers = new ArrayList<>();

    public static BeaconConfigBuilder builder() {
        return new BeaconConfigBuilder();
    }

}
