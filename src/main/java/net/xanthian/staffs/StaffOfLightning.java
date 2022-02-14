package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.List;


public class StaffOfLightning extends SwordItem {


    public StaffOfLightning(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("⚡").formatted(Formatting.BLUE));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (!player.world.isClient()) {
            player.getItemCooldownManager().set(this, 240);
            if(stack.getMaxDamage() - stack.getDamage() > 51) {
            ServerWorld world = ((ServerWorld) player.world);
            BlockPos position = target.getBlockPos();
            EntityType.LIGHTNING_BOLT.spawn(world, null, null, player, position, SpawnReason.TRIGGERED, true, true);
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 0.2f, 1);

            //50 Durability reduction on use
            stack.setDamage(stack.getDamage() + 50);
        }

            //Break at 0 durability
            if (stack.getDamage() > stack.getMaxDamage()) {
                stack.setCount(0);
            }
        }
        return super.useOnEntity(stack,player,target, hand);
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