package io.github.mrstickypiston.buildersjetpack;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.format.ConfigFormats;
import io.github.mrstickypiston.buildersjetpack.eventhandlers.ServerTick;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BuildersJetpack implements ModInitializer {

    public static final String MOD_ID = "builders_jetpack";

    public static ModConfig CONFIG;
    public static final ItemGroup BUILDERS_JETPACK_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("builders_jetpack", "builders_jetpack"))
            .icon(() -> new ItemStack(RegisterItems.JETPACK_CHESTPLATE))
            .build();

    @Override
    public void onInitialize() {
        CONFIG = Configuration.registerConfig(ModConfig.class, ConfigFormats.yaml()).getConfigInstance();

        RegisterItems.register();
        ServerTick.registerCallback();
    }


}