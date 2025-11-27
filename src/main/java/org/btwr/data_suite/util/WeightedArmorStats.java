package org.btwr.data_suite.util;

public enum WeightedArmorStats {

    GOLD(5, 8, 7, 4),
    CHAIN(3, 4, 4, 2),
    IRON(5, 8, 7, 4),
    DIAMOND(5, 8, 7, 4),
    PLATE(10, 14, 12, 8);

    final int helmet;
    final int chestplate;
    final int leggings;
    final int boots;

    WeightedArmorStats(int helmet, int chestplate, int leggings, int boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public int forHelmet() {
        return helmet;
    }

    public int forChestPlate() {
        return chestplate;
    }

    public int forLeggings() {
        return leggings;
    }

    public int forBoots() {
        return boots;
    }

}