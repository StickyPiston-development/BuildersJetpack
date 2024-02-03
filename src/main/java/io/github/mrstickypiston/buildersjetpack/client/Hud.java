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
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
        } if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen && BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_CHAT_FIX){
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

        float x = BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_X;
        float y = height - BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_Y;

        switch (BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_TYPE){
            case NONE -> {
                return;
            }

            case BAR -> {
                int barV = Math.round(percentage / 10);
                MutableText bar_a;

                if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_REMAINING_COLOR != Formatting.RESET) {
                    bar_a = new LiteralText(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_ICON.repeat(barV)).formatted(Formatting.WHITE).formatted(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_REMAINING_COLOR);
                } else {
                    bar_a = new LiteralText("").formatted(Formatting.WHITE);
                }

                if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_USED_COLOR != Formatting.RESET) {
                    MutableText bar_b = new LiteralText(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_ICON.repeat(10 - barV)).formatted(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_USED_COLOR);
                    bar_a.append(bar_b);
                }
                renderer.draw(
                        matrixStack,
                        bar_a,
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
