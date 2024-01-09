package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ServerTick {

    private static final HashMap<UUID, Vec3d> movementMap = new HashMap<>();

    public static final int[] fuelAlerts = {100, 50, 25, 10, 5, 4, 3, 2, 1};
    public static final int maxFuel = 1000;
    public static void registerCallback() {
        ServerTickEvents.START_SERVER_TICK.register(ServerTick::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        for (ServerPlayerEntity player : players) {

            ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);

            if (chestplate.getItem() == RegisterItems.JETPACK_CHESTPLATE && player.interactionManager.getGameMode() != GameMode.SPECTATOR){
                player.sendMessage(Text.of(String.format("Jetpack fuel: %.02f%%", getFuel(chestplate)/maxFuel*100)), true);
            }

            // Check for situations without jetpack
            if (player.getAbilities().creativeMode || player.interactionManager.getGameMode() == GameMode.SPECTATOR){
                continue;
            }

            if (!(chestplate.getItem() == RegisterItems.JETPACK_CHESTPLATE)){
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
                continue;
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

        float fuel = nbt.getFloat("fuel") - 0.002F - (float) distance/40F;

        for (int alert : fuelAlerts){
            if (fuel < alert && alert <= nbt.getFloat("fuel")){
                player.sendMessage(Text.of(String.format("[§c§lBuilders jetpack§r] %d fuel left (%.02f%%)", alert, alert/(float) maxFuel*100)), false);
            }
        }

        if (fuel <= 0){
            fuel = 0;
            player.sendMessage(Text.of("[§c§lBuilders jetpack§r] Out of fuel"), false);
        }

        nbt.putFloat("fuel", fuel);
    }

    public static float getFuel(ItemStack jetpack){
        return jetpack.getOrCreateNbt().getFloat("fuel");
    }
}
