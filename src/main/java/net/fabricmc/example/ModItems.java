package net.fabricmc.example;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_STAFF = registerItem("wooden_staff",new ModStaffItem(ToolMaterials.WOOD,
            3, -3f, new FabricItemSettings().group(ItemGroup.MISC)));

        private static <T extends Item> T registerItem(String name, T item){
        return Registry.register(Registry.ITEM, new Identifier(StaffsInit.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        StaffsInit.LOGGER.info("Registering ModItems for " + StaffsInit.MOD_ID);
    }
}