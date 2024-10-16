package io.github.mrstickypiston.buildersjetpack.client;

import io.github.mrstickypiston.buildersjetpack.client.commands.BuildersJetpackCommand;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Commands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> BuildersJetpackCommand.register(dispatcher));
    }
}
