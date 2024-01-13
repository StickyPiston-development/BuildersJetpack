package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import io.github.mrstickypiston.buildersjetpack.Utils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;

public class ClientTick {
    public static void registerCallback() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientTick::onClientTick);
    }

    private static void onClientTick(MinecraftClient minecraftClient) {
        PlayerEntity player = minecraftClient.player;
        if (player != null){
            if (player.getAbilities().flying && player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(RegisterItems.JETPACK_CHESTPLATE)){
                player.world.addParticle(Utils.parseParticle(BuildersJetpack.CONFIG.PARTICLE), player.getX(), player.getY() -0.2, player.getZ(), 0, -0.05, 0);
            }
        }
    }
}