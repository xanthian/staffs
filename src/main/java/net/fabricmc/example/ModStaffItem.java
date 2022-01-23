package net.fabricmc.example;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;


public class ModStaffItem extends SwordItem {
    public ModStaffItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

    }
    @Override
    //Right Click Attack
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        //Vec3d lookPos = Vec3d.ofCenter(raycast(world,player, RaycastContext.FluidHandling.NONE).getBlockPos());

        //Projectile
        if (!player.world.isClient) {
            ModProjectile projectile = new ModProjectile(ModEntities.MOD_PROJECTILE, player.getX(), player.getY() + 1.5f, player.getZ(), world);
            projectile.setNoGravity(false);
           // projectile.setPosition(player.getX()+lookPos.x *1.5D, player.getY()+lookPos.y *1.5D, player.getZ()+lookPos.z*1.5D);
            projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.5F, 0.5F);
            world.spawnEntity(projectile);
        }

        //5 tick attack cooldown
        int cooldown = 50;
        player.getItemCooldownManager().set(this, cooldown);

        //Durability loss on Right Click attack
        stack.setDamage(stack.getDamage() + 0);

        //Break at 0 durability
        stack.getDamage();

        //Play sound on attack
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SHULKER_SHOOT,
                SoundCategory.PLAYERS,0.2F, 3.0F);

        return TypedActionResult.success(stack);
    }
    //Disable Sword bonus against cobwebs
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 0F;
        } else {
            Material material = state.getMaterial();
            return 0.5F;
        }
    }

    //25% Glowing Effect on left click
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random random = new Random();
        if (random.nextFloat() <= 0.25f) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60, 1, false, false, false));
        }
        return super.postHit(stack, target, attacker);
    }

}