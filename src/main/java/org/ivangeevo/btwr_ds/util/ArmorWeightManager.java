package org.ivangeevo.btwr_ds.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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


    /**
    public float getArmorWeight(PlayerEntity player) {
        float weight = 0.0f;
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;

            if (stack.getItem() == Items.NETHERITE_HELMET) weight += 10;
            else if (stack.getItem() == Items.NETHERITE_CHESTPLATE) weight += 14;
            else if (stack.getItem() == Items.NETHERITE_LEGGINGS) weight += 12;
            else if (stack.getItem() == Items.NETHERITE_BOOTS) weight += 8;

            else if (stack.getItem() == Items.DIAMOND_HELMET) weight += 5;
            else if (stack.getItem() == Items.DIAMOND_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.DIAMOND_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.DIAMOND_BOOTS) weight += 4;

            else if (stack.getItem() == Items.IRON_HELMET) weight += 5;
            else if (stack.getItem() == Items.IRON_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.IRON_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.IRON_BOOTS) weight += 4;

            else if (stack.getItem() == Items.GOLDEN_HELMET) weight += 5;
            else if (stack.getItem() == Items.GOLDEN_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.GOLDEN_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.GOLDEN_BOOTS) weight += 4;

            else if (stack.getItem() == Items.CHAINMAIL_HELMET) weight += 3;
            else if (stack.getItem() == Items.CHAINMAIL_CHESTPLATE) weight += 4;
            else if (stack.getItem() == Items.CHAINMAIL_LEGGINGS) weight += 4;
            else if (stack.getItem() == Items.CHAINMAIL_BOOTS) weight += 2;

            // Add custom materials as needed
        }
        return weight;
    }
     **/


}
