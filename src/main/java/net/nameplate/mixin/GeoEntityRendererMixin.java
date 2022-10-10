package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.nameplate.util.NameplateRender;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GeoEntityRenderer.class)
public abstract class GeoEntityRendererMixin<T extends LivingEntity> extends EntityRenderer<T> {

    public GeoEntityRendererMixin(Context ctx) {
        super(ctx);
    }

    @Inject(method = "render", at = @At("HEAD"), remap = false)
    private void renderMixin(T entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn, CallbackInfo info) {
        if (entity instanceof MobEntity)
            NameplateRender.renderNameplate(this, (MobEntity) entity, stack, bufferIn, dispatcher, this.getTextRenderer(), isVisible(entity), packedLightIn);
    }

    @Override
    protected boolean hasLabel(T entity) {
        if (entity instanceof MobEntity)
            return false;
        return super.hasLabel(entity);
    }

    @Shadow(remap = false)
    protected boolean isVisible(T livingEntityIn) {
        return false;
    }
}
