package io.github.mrstickypiston.buildersjetpack;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.ConfigHolder;
import dev.toma.configuration.config.format.ConfigFormats;
import io.github.mrstickypiston.buildersjetpack.config.ModConfig;
import io.github.mrstickypiston.buildersjetpack.eventhandlers.ServerTick;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.StringJoiner;

public class BuildersJetpack implements ModInitializer {
    public static final String MOD_ID = "builders_jetpack";
    public static ConfigHolder<ModConfig> CONFIG_BASE;
    public static ModConfig CONFIG;

    public static ModContainer MOD_CONTAINER;
    public static String AUTHORS;
    public static String VERSION;

    @Override
    public void onInitialize() {
        FabricLoader fabricLoader = FabricLoader.getInstance();

        CONFIG_BASE = Configuration.registerConfig(ModConfig.class, ConfigFormats.YAML);
        CONFIG = CONFIG_BASE.getConfigInstance();

        MOD_CONTAINER = fabricLoader.getModContainer(MOD_ID).orElse(null);

        if (MOD_CONTAINER != null) {
            Collection<Person> authors = MOD_CONTAINER.getMetadata().getAuthors();
            StringJoiner sj = new StringJoiner(",");

            for (Person author : authors){
                sj.add(author.getName());
            }

            AUTHORS = sj.toString();
            VERSION = BuildersJetpack.MOD_CONTAINER.getMetadata().getVersion().getFriendlyString();
        }

        RegisterItems.register();
        ServerTick.registerCallback();
    }


}