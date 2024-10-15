package io.github.mrstickypiston.buildersjetpack.eventhandlers;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import io.github.mrstickypiston.buildersjetpack.Utils;
import io.github.mrstickypiston.buildersjetpack.client.BuildersJetpackClient;
import io.github.mrstickypiston.buildersjetpack.client.config.ClientConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class ClientTick {
    public static void registerCallback() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientTick::onClientTick);
    }

    private static void onClientTick(MinecraftClient minecraftClient) {
        PlayerEntity player = minecraftClient.player;
        if (player != null){
            if (player.isSpectator()){
                return;
            }

            if (player.getAbilities().flying && player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(RegisterItems.JETPACK_CHESTPLATE)){
                player.getWorld().addParticle(Utils.parseParticle(BuildersJetpackClient.CLIENT_CONFIG.PARTICLE), player.getX(), player.getY() -0.2, player.getZ(), 0, -0.05, 0);
            }

            ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

            float fuel = 0;
            float oldFuel = 0;

            NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);

            if (component != null){
                NbtCompound data = component.copyNbt();
                fuel = data.getFloat("fuel");
                oldFuel = data.getFloat("oldFuel");
            }

            if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_WARNING_TYPE != ClientConfig.fuelWarningType.DISABLED && player.getAbilities().flying && !player.isCreative()) {
                boolean actionBar = BuildersJetpackClient.CLIENT_CONFIG.FUEL_WARNING_TYPE == ClientConfig.fuelWarningType.ACTIONBAR;

                for (int alert : BuildersJetpackClient.CLIENT_CONFIG.FUEL_WARNINGS) {
                    if (fuel < alert && alert <= oldFuel) {
                        player.sendMessage(Text.of(String.format("[§c§lBuilders jetpack§r] %d fuel left (%.02f%%)", alert, alert / (float) BuildersJetpack.CONFIG.MAX_FUEL * 100)), actionBar);
                    }
                }

                if (fuel <= 0) {
                    player.sendMessage(Text.of("[§c§lBuilders jetpack§r] Out of fuel"), actionBar);
                }
            }
        }
    }
}