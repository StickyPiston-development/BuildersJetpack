package io.github.mrstickypiston.buildersjetpack;

import io.github.mrstickypiston.buildersjetpack.eventhandlers.ClientTick;
import io.github.mrstickypiston.buildersjetpack.eventhandlers.ServerTick;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BuildersJetpack implements ModInitializer {
    public static final ItemGroup BUILDERS_JETPACK_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("builders_jetpack", "builders_jetpack"))
            .icon(() -> new ItemStack(RegisterItems.JETPACK_CHESTPLATE))
            .build();

    @Override
    public void onInitialize() {
        RegisterItems.register();
        ClientTick.registerCallback();
        ServerTick.registerCallback();
    }


}