package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class StaffOfFireball extends SwordItem {


    public StaffOfFireball(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("🔥").formatted(Formatting.DARK_RED));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!player.world.isClient()) {
            if(stack.getMaxDamage() - stack.getDamage() > 51) {
                player.getItemCooldownManager().set(this, 240);
                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(world, player.getX(), player.getY() + 1f, player.getZ(), 0, 0, 0);
                smallFireballEntity.setVelocity(player, player.getPitch() - 10f, player.getYaw(), 0.0f, 3f, 2.0f);
                world.spawnEntity(smallFireballEntity);
                timerMethod(smallFireballEntity);
                //50 Durability reduction on use
                stack.setDamage(stack.getDamage() + 50);
            }
            //Break at 0 durability
            if (stack.getDamage() > stack.getMaxDamage())  {
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
    private void timerMethod(SmallFireballEntity smallFireballEntity)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                smallFireballEntity.discard();
            }
        }, 1800);
    }

}