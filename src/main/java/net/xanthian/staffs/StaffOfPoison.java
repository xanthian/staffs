package net.xanthian.staffs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;



public class StaffOfPoison extends SwordItem {


    public StaffOfPoison(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    //Tooltip
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context) {
        list.add(new LiteralText("☣").formatted(Formatting.DARK_GREEN));
    }

    //@Override
    //  public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        //PENDING
    //}

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

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random random = new Random();
        if (random.nextFloat() <= .05f) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3));

        }
        return super.postHit(stack, target, attacker);
    }
}