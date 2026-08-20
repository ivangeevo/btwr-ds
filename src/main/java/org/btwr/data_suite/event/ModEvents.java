package org.btwr.data_suite.event;

import org.btwr.data_suite.event.events.ModFoodComponentEvents;
import org.btwr.data_suite.event.events.ModItemCountEvents;
import org.btwr.data_suite.event.events.ModLootTableEvents;
import org.btwr.data_suite.event.events.ModServerEvents;

public class ModEvents {
    public static void register() {
        ModServerEvents.register();
        ModFoodComponentEvents.register();
        ModItemCountEvents.register();
        ModLootTableEvents.register();
        //ModTillingLootTableEvents.register();
    }
}