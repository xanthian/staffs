package net.xanthian.staffs;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;


public class ModStaffItem extends SwordItem {
    public ModStaffItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

    }
    //Right Click Attack
     @Override
     public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        String ID = Registry.ITEM.getId(stack.getItem()).toString();

        //Spawn Projectile
        if (!player.world.isClient) {
            player.getItemCooldownManager().set(this, 60);
            if (stack.getMaxDamage() - stack.getDamage() > 5) {
                ModProjectile projectile = new ModProjectile(ModEntities.MOD_PROJECTILE, player.getX(), player.getY() + 1.5f, player.getZ(), world);
                projectile.setStaff(this);
                projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2F, 0.5F);
                world.spawnEntity(projectile);
                if (ID.contentEquals("staffs:golden_staff") || ID.contentEquals("staffs:wooden_staff")) {
                    stack.setDamage(stack.getDamage() + 1);
                } else if (ID.contentEquals("staffs:stone_staff") || ID.contentEquals("staffs:iron_staff")) {
                    stack.setDamage(stack.getDamage() + 2);
                } else {
                    stack.setDamage(stack.getDamage() + 5);
                }
            }
        }

        //Play sound on attack
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_SHOOT,
                SoundCategory.PLAYERS, 0.2F, 3.0F);


        //Break at 0 dura
        if (stack.getDamage() > stack.getMaxDamage()){
            stack.setCount(0);
        }
        return TypedActionResult.success(stack);
    }
    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context)
    { if (Screen.hasShiftDown()) {
        list.add(new LiteralText("LMB. 25% to add glowing on melee hit").formatted(Formatting.GRAY));
        list.add(new LiteralText("RMB. Fire a projectile").formatted(Formatting.GRAY));
    }

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