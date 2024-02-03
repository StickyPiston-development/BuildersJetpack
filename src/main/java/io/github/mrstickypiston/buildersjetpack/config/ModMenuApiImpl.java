package io.github.mrstickypiston.buildersjetpack.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import dev.toma.configuration.Configuration;
import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> Configuration.getConfigScreenByGroup(BuildersJetpack.MOD_ID, parent);
    }
}
