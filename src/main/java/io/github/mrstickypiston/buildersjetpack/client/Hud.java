package io.github.mrstickypiston.buildersjetpack.client;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class Hud {
    public static void registerCallback(){
        HudRenderCallback.EVENT.register(Hud::renderHud);
    }

    private static void renderHud(MatrixStack matrixStack, float v) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) {
            return;
        } if (player.isSpectator()){
            return;
        } if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen && BuildersJetpack.CONFIG.FUEL_HUD_CHAT_FIX){
            return;
        }

        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);

        if (chestplate.getItem() != RegisterItems.JETPACK_CHESTPLATE){
            return;
        }

        float fuel = chestplate.getOrCreateNbt().getFloat("fuel");
        float percentage = fuel/(float) BuildersJetpack.CONFIG.MAX_FUEL*100;

        if (percentage > 100){
            percentage = 100;
        }

        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        float x = BuildersJetpack.CONFIG.FUEL_HUD_X;
        float y = height - BuildersJetpack.CONFIG.FUEL_HUD_Y;

        switch (BuildersJetpack.CONFIG.FUEL_HUD_TYPE){
            case NONE -> {
                return;
            }

            case BAR -> {
                percentage = percentage / 10;

                renderer.draw(matrixStack,
                        Text.of(String.format(
                                "%s", BuildersJetpack.CONFIG.FUEL_BAR_REMAINING_COLOR +
                                        BuildersJetpack.CONFIG.FUEL_BAR_ICON.repeat((int) Math.ceil(percentage)) +
                                        BuildersJetpack.CONFIG.FUEL_BAR_USED_COLOR +
                                        BuildersJetpack.CONFIG.FUEL_BAR_ICON.repeat((int) Math.floor(10-percentage))
                        )),
                        x, y, 0
                );
            }
            case PERCENTAGE -> renderer.draw(matrixStack,
                    Text.of(String.format(
                            "§f%.2f%%", percentage
                    )),
                    x, y, 0
            );
            case NUMBER -> renderer.draw(matrixStack,
                    Text.of(String.format(
                            "§f%.2f/%d", fuel, BuildersJetpack.CONFIG.MAX_FUEL
                    )),
                    x, y, 0
            );
        }

        renderer.draw(matrixStack,
                Text.of("§fJetpack fuel:"),
                x, y-10, 0
        );
    }
}
