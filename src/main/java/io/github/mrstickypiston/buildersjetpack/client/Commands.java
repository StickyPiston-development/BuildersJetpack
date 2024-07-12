package io.github.mrstickypiston.buildersjetpack.client;

import io.github.mrstickypiston.buildersjetpack.client.commands.BuildersJetpackCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class Commands {
    public static void registerCommands() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> BuildersJetpackCommand.register(dispatcher));
    }
}
