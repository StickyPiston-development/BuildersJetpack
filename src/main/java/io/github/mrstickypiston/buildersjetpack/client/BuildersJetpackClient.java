package io.github.mrstickypiston.buildersjetpack.client;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.format.ConfigFormats;
import io.github.mrstickypiston.buildersjetpack.client.config.ClientConfig;
import io.github.mrstickypiston.buildersjetpack.eventhandlers.ClientTick;
import net.fabricmc.api.ClientModInitializer;

public class BuildersJetpackClient implements ClientModInitializer {
    public static ConfigHolder<ClientConfig> CLIENT_CONFIG_BASE;
    public static ClientConfig CLIENT_CONFIG;

    @Override
    public void onInitializeClient() {
        CLIENT_CONFIG_BASE = Configuration.registerConfig(ClientConfig.class, ConfigFormats.YAML);
        CLIENT_CONFIG = CLIENT_CONFIG_BASE.getConfigInstance();

        ClientTick.registerCallback();
        Hud.registerCallback();
        Commands.registerCommands();
    }
}
