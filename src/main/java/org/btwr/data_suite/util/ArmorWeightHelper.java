package org.btwr.data_suite.util;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.IdentityHashMap;
import java.util.Map;

public class ArmorWeightHelper {

    private static final Map<ArmorMaterial, WeightedArmorStats> WEIGHT_MAP = new IdentityHashMap<>();

    static {
        register(ArmorMaterials.GOLD, new WeightedArmorStats(5, 8, 7, 4));
        register(ArmorMaterials.CHAIN, new WeightedArmorStats(3, 4, 4, 2));
        register(ArmorMaterials.IRON, new WeightedArmorStats(5, 8, 7, 4));
        register(ArmorMaterials.DIAMOND, new WeightedArmorStats(5, 8, 7, 4));
        register(ArmorMaterials.NETHERITE, new WeightedArmorStats(10, 14, 12, 8));
    }

    private static void register(RegistryEntry<ArmorMaterial> material, WeightedArmorStats stats) {
        WEIGHT_MAP.put(material.value(), stats);
    }

    public static int getWeight(ArmorItem item) {
        ArmorMaterial mat = item.getMaterial().value();
        WeightedArmorStats stats = WEIGHT_MAP.get(mat);
        if (stats == null) return 0;
        return stats.get(item.getType());
    }

    public record WeightedArmorStats(int helmet, int chestplate, int leggings, int boots) {
        public int get(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> helmet;
                case CHESTPLATE -> chestplate;
                case LEGGINGS -> leggings;
                case BOOTS -> boots;
                default -> 0;
            };
        }
    }

}