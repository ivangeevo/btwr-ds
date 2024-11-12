package org.ivangeevo.btwr_ds.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDS;

public class BTWRDS_Items
{

    public static final Item BARK_BLOOD_WOOD = registerItem("bark_blood_wood", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(BTWRDS.MOD_ID, name), item);
    }

    public static void registerAndAddToGroups()
    {
        // log message into console
        BTWRDS.LOGGER.info("Registering Mod Items for " + BTWRDS.MOD_ID);

        // and add items to item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->
        {
            entries.add(BTWRDS_Items.BARK_BLOOD_WOOD);

        });
    }

}
