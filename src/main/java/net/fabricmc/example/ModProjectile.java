package net.fabricmc.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;

import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ModProjectile extends ThrownEntity {
    public ModProjectile(EntityType<? extends ModProjectile>type, World world) {
        super(type, world);
    }
    public ModProjectile(EntityType<? extends ModProjectile>type, LivingEntity owner, World world) {
        super(type, owner,world);
    }
    public ModProjectile(EntityType<? extends ModProjectile>type, double x, double y, double z, World world) {
        super(type,x,y,z,world);
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

    @Override
    protected void initDataTracker() {

    }
}
