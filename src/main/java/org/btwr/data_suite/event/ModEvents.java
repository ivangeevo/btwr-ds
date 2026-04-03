package org.btwr.data_suite.event;

import org.btwr.data_suite.event.events.FoodComponentModifierEvents;
import org.btwr.data_suite.event.events.ItemCountModificationEvents;
import org.btwr.data_suite.event.events.ModLootTableEvents;

public class ModEvents {

    public static void register() {
        FoodComponentModifierEvents.register();
        ItemCountModificationEvents.register();
        ModLootTableEvents.register();
        //ModTillingLootTableEvents.register();
    }
}
