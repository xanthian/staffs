package net.xanthian.staffs;


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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
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

        //Projectile
        if (!player.world.isClient) {
            ModProjectile projectile = new ModProjectile(ModEntities.MOD_PROJECTILE, player.getX(), player.getY() + 1.5f, player.getZ(), world);
            projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2F, 0.5F);
            world.spawnEntity(projectile);
        }

        //Play sound on attack
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_SHOOT,
                SoundCategory.PLAYERS, 0.2F, 3.0F);

        //Particle effect on entity hit
        world.addParticle(ParticleTypes.SMALL_FLAME, player.getX(), player.getY() + 1, player.getZ(),
                0, 0.5, 0.5);

        //6 tick attack cooldown
        int cooldown = 60;
        player.getItemCooldownManager().set(this, cooldown);

        //Durability loss on Right Click attack
        String ID = Registry.ITEM.getId(stack.getItem()).toString();
        if (ID.contentEquals("staffs:golden_staff")) {
            stack.setDamage(stack.getDamage() + 1);
        } else {
            stack.setDamage(stack.getDamage() + 5);
        }
        System.out.println(stack.getMaxDamage());
        System.out.println(stack.getDamage());



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