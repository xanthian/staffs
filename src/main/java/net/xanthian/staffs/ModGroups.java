package net.xanthian.staffs;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import net.xanthian.staffs.StaffsInit;

public class ModGroups {

        public static final ItemGroup STAFFS = FabricItemGroupBuilder.build(new Identifier(StaffsInit.MOD_ID, "staffs"),
                () -> new ItemStack(ModItems.DIAMOND_STAFF));
    }


