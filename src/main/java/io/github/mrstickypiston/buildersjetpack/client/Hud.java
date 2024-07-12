package io.github.mrstickypiston.buildersjetpack.client;

import com.mojang.brigadier.LiteralMessage;
import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.*;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;

public class Hud {
    public static void registerCallback(){
        HudRenderCallback.EVENT.register(Hud::renderHud);
    }

    private static void renderHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
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

        float fuel = 0;

        NbtComponent component = chestplate.get(DataComponentTypes.CUSTOM_DATA);

        if (component != null){
            NbtCompound data = component.copyNbt();
            fuel = data.getFloat("fuel");
        }

        float percentage = fuel/(float) BuildersJetpack.CONFIG.MAX_FUEL*100;

        if (percentage > 100){
            percentage = 100;
        }

        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        float x = BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_X;
        float y = height - BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_Y;

        switch (BuildersJetpackClient.CLIENT_CONFIG.FUEL_HUD_TYPE){
            case DISABLED -> {
                return;
            }

            case BAR -> {
                int barV = Math.round(percentage / 10);
                MutableText bar_a;

                if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_REMAINING_COLOR != Formatting.RESET) {
                   bar_a = MutableText.of(
                           new PlainTextContent.Literal(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_ICON.repeat(barV))
                   ).formatted(
                           Formatting.WHITE
                   ).formatted(
                           BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_REMAINING_COLOR
                   );
                } else {
                    bar_a = Text.literal("").formatted(Formatting.WHITE);
                }

                if (BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_USED_COLOR != Formatting.RESET) {
                    MutableText bar_b = Text.literal(
                            BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_ICON.repeat(10 - barV)
                    ).formatted(BuildersJetpackClient.CLIENT_CONFIG.FUEL_BAR_USED_COLOR);
                    bar_a.append(bar_b);
                }
                drawContext.drawText(renderer, bar_a.asOrderedText(), (int) x, (int) y, 0, false);
            }
            case PERCENTAGE -> drawContext.drawText(
                    renderer,
                    Text.of(String.format(
                            "§f%.2f%%", percentage
                    )), (int) x, (int) y, 0, false);
            case NUMBER -> drawContext.drawText(
                    renderer,
                    Text.of(String.format(
                            "§f%.2f/%d", fuel, BuildersJetpack.CONFIG.MAX_FUEL
                    )), (int) x, (int) y, 0, false);
        }

        drawContext.drawText(renderer, Text.of("§fJetpack fuel:"), (int) x, (int) y - 10, 0, false);
    }
}
