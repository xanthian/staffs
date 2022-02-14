package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;


public class StaffOfTeleportation extends SwordItem {


    public StaffOfTeleportation(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("⏭").formatted(Formatting.DARK_GREEN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getMainHandStack();

        if (player.getEntityWorld().isClient) {
            if (stack.getMaxDamage() - stack.getDamage() > 11) {
                Vec3d lookPos = Vec3d.ofCenter(raycast(world, player, RaycastContext.FluidHandling.NONE).getBlockPos());
                player.setPosition(lookPos.x, lookPos.y, lookPos.z);

                //Sound
                world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SHULKER_TELEPORT,
                        SoundCategory.PLAYERS, 0.2F, 3.0F);

                stack.setDamage(stack.getDamage() + 10);
            }
        }
        //Fall damage cancel (blocks)
        player.fallDistance = -2F;

        //Break at 0 durability
        if (stack.getDamage() > stack.getMaxDamage()) {
            stack.setCount(0);
        }

        return super.use(world, player, hand);
    }

    //copied from item.raycast
    protected static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        double range = 20;
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * ((float) Math.PI / 180F) - (float) Math.PI);
        float i = MathHelper.sin(-g * ((float) Math.PI / 180F) - (float) Math.PI);
        float j = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float k = MathHelper.sin(-f * ((float) Math.PI / 180F));
        float l = i * j;
        float n = h * j;
        double d = 5.0D;
        Vec3d vec3d2 = vec3d.add((double) l * range, (double) k * range, (double) n * range);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
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