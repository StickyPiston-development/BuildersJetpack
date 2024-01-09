package io.github.mrstickypiston.buildersjetpack.items;

import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JetpackFuelItem extends Item {

    public JetpackFuelItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack stack = player.getStackInHand(hand);
        float fuel = chestplate.getOrCreateNbt().getFloat("fuel");

        if (world.isClient()){
            if (chestplate.getItem() != RegisterItems.JETPACK_CHESTPLATE){
                return TypedActionResult.fail(stack);

            } else if (fuel >= 1000){
                return TypedActionResult.fail(stack);
            }

            player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1, 1);
            return TypedActionResult.success(stack);

        }

        if (chestplate.getItem() != RegisterItems.JETPACK_CHESTPLATE){
            player.sendMessage(Text.of("[§c§lBuilders jetpack§r] No jetpack equipped"), false);
            return TypedActionResult.fail(stack);

        } else if (fuel >= 1000){
            player.sendMessage(Text.of("[§c§lBuilders jetpack§r] Jetpack fuel is already full"), false);
            return TypedActionResult.fail(stack);
        }

        fuel += 60;

        if (fuel > 1000){
            fuel = 1000;
        }

        chestplate.getOrCreateNbt().putFloat("fuel", fuel);
        stack.decrement(1);

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Adds 60 fuel to a backpack"));
    }
}
