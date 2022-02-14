package net.xanthian.staffs;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities
{

    public static final EntityType<ModProjectile> MOD_PROJECTILE = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(StaffsInit.MOD_ID, "mod_projectile"),
            FabricEntityTypeBuilder.<ModProjectile>create( SpawnGroup.MISC, ModProjectile::new).build()
    );

      public static void registerModEntities()
    {
        StaffsInit.LOGGER.info("Registering Entities for " + StaffsInit.MOD_ID);

    }
}
