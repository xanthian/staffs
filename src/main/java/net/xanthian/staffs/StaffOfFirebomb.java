package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;


public class StaffOfFirebomb extends SwordItem {


    public StaffOfFirebomb(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("☠").formatted(Formatting.DARK_RED));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        final float f = -MathHelper.sin(player.getYaw() * ((float)Math.PI / 180)) * MathHelper.cos(player.getPitch() * ((float)Math.PI / 180));
        final float g = -MathHelper.sin(player.getPitch() * ((float)Math.PI / 180));
        final float h = MathHelper.cos(player.getYaw() * ((float)Math.PI / 180)) * MathHelper.cos(player.getPitch() * ((float)Math.PI / 180));
        if (!player.world.isClient()) {
            if(stack.getMaxDamage() - stack.getDamage() > 51) {
                player.getItemCooldownManager().set(this, 240);
                FireballEntity fireballEntity = new FireballEntity( world, player,f,g,h,5);
                fireballEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0F,2,0.5F);
                world.spawnEntity(fireballEntity);
                world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 0.2f, 1);
                //50 Durability reduction on use
                stack.setDamage(stack.getDamage() + 50);
            }

            //Break at 0 durability
            if (stack.getDamage() > stack.getMaxDamage()) {
                stack.setCount(0);
            }
        }
        return super.use(world, player, hand);
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