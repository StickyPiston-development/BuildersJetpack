package io.github.mrstickypiston.buildersjetpack;

import io.github.mrstickypiston.buildersjetpack.armor.materials.JetpackArmorMaterial;
import io.github.mrstickypiston.buildersjetpack.items.JetpackFuelItem;
import io.github.mrstickypiston.buildersjetpack.items.JetpackItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.mrstickypiston.buildersjetpack.BuildersJetpack.BUILDERS_JETPACK_GROUP;

public class RegisterItems {
    public static final ArmorMaterial JETPACK_ARMOR_MATERIAL = new JetpackArmorMaterial();
    public static final Item JETPACK_CHESTPLATE = new JetpackItem(JETPACK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final Item JETPACK_FUEL = new JetpackFuelItem(new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.ITEM, "builders_jetpack:jetpack", JETPACK_CHESTPLATE);
        Registry.register(Registries.ITEM, "builders_jetpack:jetpack_fuel", JETPACK_FUEL);
        Registry.register(Registries.ITEM_GROUP, Identifier.of("builders_jetpack", "builders_jetpack"), BUILDERS_JETPACK_GROUP);
    }
}
