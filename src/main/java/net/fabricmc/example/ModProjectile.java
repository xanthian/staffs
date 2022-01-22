package net.fabricmc.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ModProjectile extends ThrownItemEntity {
    public ModProjectile(EntityType<? extends ModProjectile>type, World world) {
        super(type, world);
    }
    public ModProjectile(EntityType<? extends ModProjectile>type, LivingEntity thrower, World world) {
        super(type, thrower,world);
    }
    public ModProjectile(EntityType<? extends ModProjectile>type, double x, double y, double z, World world) {
        super(type, x,y,z,world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    @Override
    protected void onCollision(HitResult result)
    {
        Entity thrower = getOwner();
        if (result.getType() == HitResult.Type.ENTITY)
        {
            Entity entityHit = ((EntityHitResult) result).getEntity();
            entityHit.damage(DamageSource.thrownProjectile(this, thrower), 1.0F);
        }
    }
}
