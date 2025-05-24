package org.ivangeevo.btwr_ds.config;


import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BTWRDSConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SettingsGUI::createConfigScreen;
    }

}