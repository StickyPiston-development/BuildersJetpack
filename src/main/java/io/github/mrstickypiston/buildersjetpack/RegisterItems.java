package io.github.mrstickypiston.buildersjetpack;

import io.github.mrstickypiston.buildersjetpack.armor.materials.JetpackArmorMaterial;
import io.github.mrstickypiston.buildersjetpack.items.JetpackFuelItem;
import io.github.mrstickypiston.buildersjetpack.items.JetpackItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static io.github.mrstickypiston.buildersjetpack.BuildersJetpack.BUILDERS_JETPACK_GROUP;

public class RegisterItems {
    public static final ArmorMaterial JETPACK_ARMOR_MATERIAL = new JetpackArmorMaterial();
    public static final Item JETPACK_CHESTPLATE = new JetpackItem(JETPACK_ARMOR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(BUILDERS_JETPACK_GROUP));
    public static final Item JETPACK_FUEL = new JetpackFuelItem(new FabricItemSettings().group(BUILDERS_JETPACK_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, "builders_jetpack:jetpack", JETPACK_CHESTPLATE);
        Registry.register(Registry.ITEM, "builders_jetpack:jetpack_fuel", JETPACK_FUEL);
    }
}
