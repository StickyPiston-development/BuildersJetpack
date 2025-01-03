package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ServerTick {

    private static final HashMap<UUID, Vec3d> movementMap = new HashMap<>();
    private static final HashMap<UUID, Boolean> hasJetpackMap = new HashMap<>();
    public static void registerCallback() {
        ServerTickEvents.START_SERVER_TICK.register(ServerTick::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {
            // Check for situations without jetpack
            if (player.getAbilities().creativeMode || player.interactionManager.getGameMode() == GameMode.SPECTATOR){
                continue;
            }

            ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);

            if (!hasJetpackMap.getOrDefault(player.getUuid(), false)) {

                // player had no jetpack previous tick

                if (chestplate.getItem() == RegisterItems.JETPACK_CHESTPLATE) {
                    System.out.println("player got jetpack: " + player.getName());
                    // player does have a jetpack this tick
                    hasJetpackMap.put(player.getUuid(), true);
                } else {
                    // Player still has no jetpack
                    continue;
                }

            } else {
                // player had a jetpack last tick

                if (!(chestplate.getItem() == RegisterItems.JETPACK_CHESTPLATE)){
                    System.out.println("player no longer has jetpack: " + player.getName());
                    // player no longer has a jetpack, disable flying
                    player.getAbilities().allowFlying = false;
                    player.getAbilities().flying = false;
                    player.sendAbilitiesUpdate();

                    // update map
                    hasJetpackMap.put(player.getUuid(), false);
                    continue;
                }

                // player still has a jetpack
            }

            // Fly code

            if (getFuel(chestplate) != 0){
                player.getAbilities().allowFlying = true;

                if (player.getAbilities().flying){
                    flyTick(player);
                }

            } else {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
            }

            player.sendAbilitiesUpdate();
        }
    }

    public static void flyTick(PlayerEntity player){
        NbtCompound nbt = player.getEquippedStack(EquipmentSlot.CHEST).getOrCreateNbt();

        Vec3d currentPosition = player.getPos();
        Vec3d oldPosition = movementMap.getOrDefault(player.getUuid(), player.getPos());

        double deltaX = currentPosition.x - oldPosition.x;
        double deltaY = currentPosition.y - oldPosition.y;
        double deltaZ = currentPosition.z - oldPosition.z;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        movementMap.put(player.getUuid(), currentPosition);

        if (distance > BuildersJetpack.CONFIG.FUEL_SPEED_CAP){
            distance = BuildersJetpack.CONFIG.FUEL_SPEED_CAP;
        }

        nbt.putFloat("oldFuel", nbt.getFloat("fuel"));

        float fuel = nbt.getFloat("fuel") - BuildersJetpack.CONFIG.FLY_BASE_FUEL_COST - (float) distance/BuildersJetpack.CONFIG.FLY_MOVEMENT_FUEL_COST;

        if (fuel <= 0) {
            fuel = 0;
        }

        nbt.putFloat("fuel", fuel);
    }

    public static float getFuel(ItemStack jetpack){
        return jetpack.getOrCreateNbt().getFloat("fuel");
    }
}
