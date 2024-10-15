package io.github.mrstickypiston.buildersjetpack.items;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import java.util.List;

public class JetpackItem extends ArmorItem {
    float fuel;

    public JetpackItem(RegistryEntry<ArmorMaterial> material, ArmorItem.Type type, net.minecraft.item.Item.Settings settings) {
        super(material, type, BuildersJetpack.CONFIG.JETPACK_FIRE_PROOF ? settings.fireproof() : settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);

        tooltip.add(Text.of("Allows you to fly, consuming fuel"));

        if (component == null){
            tooltip.add(Text.of("Fuel:  Empty"));
            return;
        }

        NbtCompound data = component.copyNbt();
        fuel = data.getFloat("fuel");

        tooltip.add(Text.of(String.format("Fuel:  %.02f/%d (%.02f%%)", fuel, BuildersJetpack.CONFIG.MAX_FUEL, fuel / BuildersJetpack.CONFIG.MAX_FUEL * 100)));
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return true;
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {

        return enchantment.value().isAcceptableItem(Items.NETHERITE_CHESTPLATE.getDefaultStack()) && enchantment.value().isSupportedItem(Items.NETHERITE_CHESTPLATE.getDefaultStack());
    }
}
