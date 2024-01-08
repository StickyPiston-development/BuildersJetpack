package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;

public class ClientTick {
    public static void registerCallback() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientTick::onClientTick);
    }

    private static void onClientTick(MinecraftClient minecraftClient) {
        PlayerEntity player = minecraftClient.player;
        if (player != null){
            if (player.getAbilities().flying && player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(RegisterItems.JETPACK_CHESTPLATE)){
                player.world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() -0.2, player.getZ(), 0, -0.05, 0);
            }
        }
    }
}
