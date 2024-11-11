package org.ivangeevo.btwr_ds.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDS;

public class BTWRDS_Items
{

    public static final Item BLOOD_WOOD_BARK = registerItem("blood_wood_bark", new Item(new Item.Settings()));



    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(BTWRDS.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        BTWRDS.LOGGER.info("Registering Mod Items for " + BTWRDS.MOD_ID);
    }
}
