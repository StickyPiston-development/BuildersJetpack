package io.github.mrstickypiston.buildersjetpack.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JetpackItem extends ArmorItem {

    int maxFuel = 1000;
    float fuel;

    public JetpackItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        fuel = stack.getOrCreateNbt().getFloat("fuel");

        tooltip.add(Text.of("Allows you to fly consuming 0.1 fuel/s"));
        tooltip.add(Text.of(String.format("Fuel:  %.02f/%d (%.02f%%)", fuel, maxFuel, fuel / maxFuel * 100)));
    }
}
