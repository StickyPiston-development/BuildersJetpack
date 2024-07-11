package io.github.mrstickypiston.buildersjetpack;

import io.github.mrstickypiston.buildersjetpack.armor.materials.ArmorMaterials;
import io.github.mrstickypiston.buildersjetpack.items.JetpackFuelItem;
import io.github.mrstickypiston.buildersjetpack.items.JetpackItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.mrstickypiston.buildersjetpack.BuildersJetpack.BUILDERS_JETPACK_GROUP;

public class RegisterItems {
    public static final Item JETPACK_CHESTPLATE = new JetpackItem(ArmorMaterials.JETPACK, ArmorItem.Type.CHESTPLATE, new Item.Settings());
    public static final Item JETPACK_FUEL = new JetpackFuelItem(new Item.Settings());

    public static void register() {
        Registry.register(Registries.ITEM, "builders_jetpack:jetpack", JETPACK_CHESTPLATE);
        Registry.register(Registries.ITEM, "builders_jetpack:jetpack_fuel", JETPACK_FUEL);
        Registry.register(Registries.ITEM_GROUP, Identifier.of("builders_jetpack", "builders_jetpack"), BUILDERS_JETPACK_GROUP);
    }
}
