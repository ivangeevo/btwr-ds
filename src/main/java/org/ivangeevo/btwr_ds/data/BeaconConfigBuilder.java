package org.ivangeevo.btwr_ds.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import issame.material_beacons.config.BeaconConfig;

import java.util.ArrayList;
import java.util.List;

public class BeaconConfigBuilder {

    private final List<String> bases = new ArrayList<>();
    private final List<List<BeaconConfig.EffectConfig>> powers = new ArrayList<>();

    public static BeaconConfigBuilder builder() {
        return new BeaconConfigBuilder();
    }

    /** Add a base block or tag (tag starts with #) */
    public BeaconConfigBuilder addBase(String base) {
        this.bases.add(base);
        return this;
    }

    /** Add a group of powers for one level */
    public BeaconConfigBuilder addPower(List<BeaconConfig.EffectConfig> powerGroup) {
        this.powers.add(powerGroup);
        return this;
    }

    /** Build the BeaconConfig record */
    public BeaconConfig build() {
        return new BeaconConfig(List.copyOf(bases), List.copyOf(powers));
    }

    /** Convert to JSON (for datagen) */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        // Bases
        JsonArray baseArray = new JsonArray();
        for (String base : bases) {
            baseArray.add(base);
        }
        json.add("bases", baseArray);

        // Powers
        JsonArray powersArray = new JsonArray();
        for (List<BeaconConfig.EffectConfig> group : powers) {
            JsonArray groupArray = new JsonArray();
            for (BeaconConfig.EffectConfig effect : group) {
                JsonObject effectJson = new JsonObject();
                effectJson.addProperty("effect", effect.effect());
                effectJson.addProperty("duration", effect.duration());
                effectJson.addProperty("amplifier", effect.amplifier());
                effectJson.addProperty("range", effect.range());
                groupArray.add(effectJson);
            }
            powersArray.add(groupArray);
        }
        json.add("powers", powersArray);

        return json;
    }
}
