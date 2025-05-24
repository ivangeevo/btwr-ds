package org.ivangeevo.btwr_ds.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.BTWRDSModClient;

public class SettingsGUI {

    static BTWRDSSettings settingsCommon = BTWRDSMod.getInstance().settings;

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.btwr_ds.config"));

        builder.setSavingRunnable(() -> BTWRDSMod.getInstance().saveSettings());

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwr.category.general"));

        /** General Category**/

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr_ds.btwHoeFunctionality"), settingsCommon.btwHoeFunctionality)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> settingsCommon.btwHoeFunctionality = newValue)
                .setTooltip(Text.translatable("config.btwr_ds.tooltip.btwHoeFunctionality"))
                .build());


        return builder.build();
    }



}
