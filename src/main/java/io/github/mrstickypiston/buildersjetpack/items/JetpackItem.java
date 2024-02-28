package io.github.mrstickypiston.buildersjetpack.items;

import io.github.mrstickypiston.buildersjetpack.BuildersJetpack;
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
    float fuel;

    public JetpackItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, BuildersJetpack.CONFIG.JETPACK_FIRE_PROOF ? settings.fireproof() : settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        fuel = stack.getOrCreateNbt().getFloat("fuel");

        tooltip.add(Text.of("Allows you to fly consuming fuel"));
        tooltip.add(Text.of(String.format("Fuel:  %.02f/%d (%.02f%%)", fuel, BuildersJetpack.CONFIG.MAX_FUEL, fuel / BuildersJetpack.CONFIG.MAX_FUEL * 100)));
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return true;
    }
}
