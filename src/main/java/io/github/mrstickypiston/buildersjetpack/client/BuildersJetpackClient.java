package io.github.mrstickypiston.buildersjetpack.client;

import io.github.mrstickypiston.buildersjetpack.eventhandlers.ClientTick;
import net.fabricmc.api.ClientModInitializer;

public class BuildersJetpackClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTick.registerCallback();
    }
}
