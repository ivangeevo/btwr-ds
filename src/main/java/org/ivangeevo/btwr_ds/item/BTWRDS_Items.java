package org.ivangeevo.btwr_ds.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public class BTWRDS_Items {

    public static final Item BTWR_ICON = registerItem("btwr_icon", new Item(new Item.Settings()));

    public static final Item BARK_BLOOD_WOOD = registerItem("bark_blood_wood", new Item(new Item.Settings()));
    public static final Item ENDER_SLAG = registerItem("ender_slag", new Item(new Item.Settings()));
    public static final Item SOUL_FLUX = registerItem("soul_flux", new Item(new Item.Settings()));
    public static final Item BRIMSTONE = registerItem("brimstone", new Item(new Item.Settings()));
    public static final Item ELEMENT = registerItem("element", new Item(new Item.Settings()));
    public static final Item REDSTONE_LATCH = registerItem("redstone_latch", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BTWRDSMod.MOD_ID, name), item);
    }

    public static void registerAndAddToGroups() {
        // log message into console
        BTWRDSMod.LOGGER.info("Registering Mod Items for " + BTWRDSMod.MOD_ID);

        // and add items to item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(BARK_BLOOD_WOOD);
            entries.add(ENDER_SLAG);
            entries.add(SOUL_FLUX);
            entries.add(BRIMSTONE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(ELEMENT);
            entries.add(REDSTONE_LATCH);
        });
    }

}
