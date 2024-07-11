package io.github.mrstickypiston.buildersjetpack.items;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import io.github.mrstickypiston.buildersjetpack.RegisterItems;
import io.github.mrstickypiston.buildersjetpack.Utils;
import io.github.mrstickypiston.buildersjetpack.client.BuildersJetpackClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JetpackFuelItem extends Item {

    public JetpackFuelItem(net.minecraft.item.Item.Settings settings) {
        super(BuildersJetpack.CONFIG.JETPACK_FIRE_PROOF ? settings.fireproof() : settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack stack = player.getStackInHand(hand);
        float fuel = 0;

        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (component != null){
            NbtCompound data = component.copyNbt();
            fuel = data.getFloat("fuel");
        }

        if (world.isClient()){
            if (chestplate.getItem() != RegisterItems.JETPACK_CHESTPLATE){
                return TypedActionResult.fail(stack);

            } else if (fuel >= BuildersJetpack.CONFIG.MAX_FUEL){
                return TypedActionResult.fail(stack);
            }

            player.playSound(Utils.parseSoundEvent(BuildersJetpackClient.CLIENT_CONFIG.FUEL_ITEM_SUCCESS), 1, 1);
            return TypedActionResult.success(stack);

        }

        if (chestplate.getItem() != RegisterItems.JETPACK_CHESTPLATE){
            player.sendMessage(Text.of("[§c§lBuilders jetpack§r] No jetpack equipped"), false);
            return TypedActionResult.fail(stack);

        } else if (fuel >= BuildersJetpack.CONFIG.MAX_FUEL){
            player.sendMessage(Text.of("[§c§lBuilders jetpack§r] Jetpack fuel is already full"), false);
            return TypedActionResult.fail(stack);
        }

        fuel += BuildersJetpack.CONFIG.FUEL_ITEM_AMOUNT;

        if (fuel > BuildersJetpack.CONFIG.MAX_FUEL){
            fuel = BuildersJetpack.CONFIG.MAX_FUEL;
        }

        float finalFuel = fuel;

        chestplate.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, nbtComponent -> nbtComponent.apply(currentNbt -> currentNbt.putFloat("fuel", finalFuel)));
        stack.decrement(1);

        return TypedActionResult.success(stack);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, net.minecraft.item.Item.TooltipContext context) {
        tooltip.add(Text.of(String.format("Adds %d fuel to the equipped jetpack", BuildersJetpack.CONFIG.FUEL_ITEM_AMOUNT)));
    }
}
