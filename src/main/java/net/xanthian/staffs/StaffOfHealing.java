package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.*;

import static net.minecraft.entity.effect.StatusEffects.*;
import static net.minecraft.sound.SoundEvents.*;


public class StaffOfHealing extends SwordItem {


    public StaffOfHealing(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("♥").formatted(Formatting.LIGHT_PURPLE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        String ID = Registry.ITEM.getId(stack.getItem()).toString();

        if (!player.world.isClient()) {
                //prevent use if dura <51
            if (stack.getMaxDamage() - stack.getDamage() > 51) {
                //6 Tick Cooldown
                player.getItemCooldownManager().set(this, 120);
                //1.5 Heart heal
            if (player.getHealth() != player.getMaxHealth()) {
                player.heal(2.0F);
                //3 tick regen I
                player.addStatusEffect(new StatusEffectInstance(REGENERATION, 60, 0, false, false, true));
                //50 Durability reduction on use
                stack.setDamage(stack.getDamage() + 50);
            }
        }
    }
            //Play Sound
            world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), ENTITY_VILLAGER_WORK_CLERIC,
                SoundCategory.PLAYERS, 0.5F, 3.0F);

            //Add particle
            world.addParticle(ParticleTypes.HEART, player.getX()+0.5, player.getY()+1D , player.getZ()+1, 0d, 0.05D, 0d);
            world.addParticle(ParticleTypes.HEART, player.getX()-0.5, player.getY()+1D, player.getZ()+1, 0d, 0.05D, 0d);
            world.addParticle(ParticleTypes.HEART, player.getX()+0, player.getY() +2D, player.getZ()+1, 0d, 0.05D, 0d);

            //Break at 0 durability
            if (stack.getDamage() > stack.getMaxDamage()) {
                stack.setCount(0);
            }
        return super.use(world, player, hand);
        }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!player.world.isClient()) {
            if (stack.getMaxDamage() - stack.getDamage() > 25) {
                BlockPos position = target.getBlockPos();
                if (target.getHealth() != target.getMaxHealth()) {
                    //3s  cooldown
                    player.getItemCooldownManager().set(this, 60);
                    target.heal(3.0F);
                    target.addStatusEffect(new StatusEffectInstance(REGENERATION, 100, 0, false, false, true));
                }
            }
            //Add particle
            target.world.addParticle(ParticleTypes.HEART, target.getX() + 0.5, target.getY() + 1D, target.getZ() + 1, 0d, 0.05D, 0d);
            target.world.addParticle(ParticleTypes.HEART, target.getX() - 0.5, target.getY() + 1D, target.getZ() + 1, 0d, 0.05D, 0d);
            target.world.addParticle(ParticleTypes.HEART, target.getX() + 0, target.getY() + 2D, target.getZ() + 1, 0d, 0.05D, 0d);

            //Play Sound
            target.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), ENTITY_VILLAGER_WORK_CLERIC, SoundCategory.PLAYERS, 0.5F, 3.0F);

            //Break at 0 durability
            if (stack.getDamage() > stack.getMaxDamage()) {
                stack.setCount(0);
            }

        }
        return super.useOnEntity(stack, player, target, hand);
    }

    //Disable Sword bonus against cobwebs
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
        }
        return 0F;
    }
    @Override
    public boolean isSuitableFor(BlockState state) {
        return false;
    }
}