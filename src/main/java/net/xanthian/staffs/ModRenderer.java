package net.xanthian.staffs;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class ModRenderer extends EntityRenderer<ModProjectile> {
    protected ModRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    private static final Identifier TEXTURE_LOCATION = new Identifier("staffs:textures/item/golden_fireball.png");
    private static final RenderLayer RENDER_TYPE = RenderLayer.getEntityCutoutNoCull(TEXTURE_LOCATION);

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int packedLight, float x, int y, int u, int v) {
        vertexConsumer.vertex(matrix4f, x - 0.5F, (float) y - 1.0F, 0.0F).color(255, 255, 255, 255).texture((float) u, (float) v).overlay(OverlayTexture.DEFAULT_UV).light(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).next();
    }

    @Override
    public void render(ModProjectile projectile, float x, float partialTick, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int packedLight) {
        matrixStack.push();
        matrixStack.scale(1F, 1F, 1F);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        //  matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(projectile.age));
        MatrixStack.Entry posestack$pose = matrixStack.peek();
        Matrix4f matrix4f = posestack$pose.getPositionMatrix();
        Matrix3f matrix3f = posestack$pose.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
        matrixStack.pop();
        super.render(projectile, x, partialTick, matrixStack, vertexConsumerProvider, packedLight);
    }

    @Override
    public Identifier getTexture(ModProjectile entity) {
        {
            return TEXTURE_LOCATION;
        }
    }
    private ModStaffItem staff;
    public void setStaff(ModStaffItem staff) {
        this.staff = staff;
        System.out.println(this.staff);
    }
}


