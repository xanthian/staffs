package net.xanthian.staffs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;


public class ModProjectile extends ThrownEntity {
    public ModProjectile(EntityType<? extends ModProjectile> type, World world) {
        super(type, world);
    }

    public ModProjectile(EntityType<? extends ModProjectile> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    public ModProjectile(EntityType<? extends ModProjectile> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    private ModStaffItem staff;
    public void setStaff(ModStaffItem staff) {
        this.staff = staff;
        System.out.println(this.staff);
    }
    //String ID = Registry.ITEM.getId(staff.getName().toString());


    @Override
    protected void onCollision(HitResult result) {
        Entity thrower = getOwner();
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entityHit = ((EntityHitResult) result).getEntity();
            //Damage Entity
            entityHit.damage(DamageSource.thrownProjectile(this, thrower), 1.0F);
            //Add Particle effect
            world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(),
                    (double) (0xFF) / 255.0D, (double) (0x6A) / 255.0D, (double) (0x00) / 255.0D);
        }

        //Stops the projectile lasting forever
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }
    @Override
    protected void initDataTracker() {

    }
}


