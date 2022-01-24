package net.xanthian.staffs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class ModRenderer2 extends EntityRenderer<ModProjectile> {


    public static final ItemStack STACK = new ItemStack(Items.DIAMOND_BLOCK);

    public ModRenderer2(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ModProjectile projectile, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider consumerProvider, int light) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(projectile.age));
        MinecraftClient.getInstance().getItemRenderer().renderItem (STACK, ModelTransformation.Mode.FIXED,
                light, OverlayTexture.DEFAULT_UV, matrixStack, consumerProvider, 123);
        matrixStack.pop();
        super.render(projectile, yaw, tickDelta, matrixStack, consumerProvider,light);
    }

    @Override
    public Identifier getTexture(ModProjectile entity) {
        return null;
    }

    {
    }
}