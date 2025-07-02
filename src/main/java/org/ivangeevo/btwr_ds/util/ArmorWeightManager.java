package org.ivangeevo.btwr_ds.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class ArmorWeightManager
{

    private static final ArmorWeightManager INSTANCE = new ArmorWeightManager();
    private ArmorWeightManager() {}
    public static ArmorWeightManager getInstance() {
        return INSTANCE;
    }

    public void addExhaustionFromArmorWeight() {}

    public void sinkIfTooHeavy() {}

    public float getArmorWeight(PlayerEntity player) {
        float weight = 0.0f;
        for (ItemStack stack : player.getArmorItems()) {
            if (!(stack.getItem() instanceof ArmorItem armorItem)) continue;
            weight += ArmorWeightHelper.getWeight(armorItem);
        }
        return weight;
    }

}
