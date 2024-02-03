package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import io.github.mrstickypiston.buildersjetpack.Utils;
import io.github.mrstickypiston.buildersjetpack.client.BuildersJetpackClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class ClientTick {
    public static void registerCallback() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientTick::onClientTick);
    }

    private static void onClientTick(MinecraftClient minecraftClient) {
        PlayerEntity player = minecraftClient.player;
        if (player != null){
            if (player.getAbilities().flying && player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(RegisterItems.JETPACK_CHESTPLATE)){
                player.world.addParticle(Utils.parseParticle(BuildersJetpackClient.CLIENT_CONFIG.PARTICLE), player.getX(), player.getY() -0.2, player.getZ(), 0, -0.05, 0);
            }

            NbtCompound nbt = player.getEquippedStack(EquipmentSlot.CHEST).getOrCreateNbt();

            float fuel = nbt.getFloat("fuel");
            float oldFuel = nbt.getFloat("oldFuel");

            nbt.putFloat("oldFuel", 0);

            if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_WARNING && oldFuel != 0) {
                for (int alert : BuildersJetpackClient.CLIENT_CONFIG.FUEL_WARNINGS) {
                    if (fuel < alert && alert <= oldFuel) {
                        player.sendMessage(Text.of(String.format("[§c§lBuilders jetpack§r] %d fuel left (%.02f%%)", alert, alert / (float) BuildersJetpack.CONFIG.MAX_FUEL * 100)), true);
                    }
                }

                if (fuel <= 0) {
                    player.sendMessage(Text.of("[§c§lBuilders jetpack§r] Out of fuel"), true);
                }
            }
        }
    }
}