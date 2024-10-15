package io.github.mrstickypiston.buildersjetpack;

import io.github.mrstickypiston.buildersjetpack.items.JetpackFuelItem;
import io.github.mrstickypiston.buildersjetpack.items.JetpackItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class RegisterItems {
    public static final Item JETPACK_CHESTPLATE = new JetpackItem(
            ArmorMaterials.JETPACK.value(),
            EquipmentSlot.CHEST,
            new Item.Settings()
    );
    public static final Item JETPACK_FUEL = new JetpackFuelItem(new Item.Settings());

    public static final RegistryKey<ItemGroup> BUILDERS_JETPACK_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FabricDocsReference.MOD_ID, "item_group"));
    public static final ItemGroup BUILDERS_JETPACK_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(JETPACK_CHESTPLATE.asItem()))
            .displayName(Text.translatable("itemGroup.builders_jetpack.builders_jetpack"))
            .build();

    public static void register() {
        ArmorMaterials.initialize();
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, BUILDERS_JETPACK_GROUP_KEY, BUILDERS_JETPACK_GROUP);

        ItemGroupEvents.modifyEntriesEvent(BUILDERS_JETPACK_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(JETPACK_CHESTPLATE);
            itemGroup.add(JETPACK_FUEL);
        });

        Registry.register(Registries.ITEM, "builders_jetpack:jetpack", JETPACK_CHESTPLATE);
        Registry.register(Registries.ITEM, "builders_jetpack:jetpack_fuel", JETPACK_FUEL);
    }
}
