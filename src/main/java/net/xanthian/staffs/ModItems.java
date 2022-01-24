package net.xanthian.staffs;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_STAFF = registerItem("wooden_staff",
            new ModStaffItem(ToolMaterials.WOOD, 1, -3f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));
    public static final Item STONE_STAFF = registerItem("stone_staff",
            new ModStaffItem(ToolMaterials.STONE, 1, -3f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));
    public static final Item IRON_STAFF = registerItem("iron_staff",
            new ModStaffItem(ToolMaterials.IRON, 1, -3f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));
    public static final Item GOLDEN_STAFF = registerItem("golden_staff",
            new ModStaffItem(ToolMaterials.GOLD, 3, -3f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));
    public static final Item DIAMOND_STAFF = registerItem("diamond_staff",
            new ModStaffItem(ToolMaterials.DIAMOND, 2, -3f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));
    public static final Item NETHERITE_STAFF = registerItem("netherite_staff",
            new ModStaffItem(ToolMaterials.NETHERITE, 2, -2.5f,
                    new FabricItemSettings().group(ModGroups.STAFFS)));


        private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(StaffsInit.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        StaffsInit.LOGGER.info("Registering ModItems for " + StaffsInit.MOD_ID);
    }
}