package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.nameplate.util.NameplateRender;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

@Environment(EnvType.CLIENT)
@SuppressWarnings("rawtypes")
@Mixin(GeoReplacedEntityRenderer.class)
public abstract class GeoReplacedEntityRendererMixin extends EntityRenderer {

    public GeoReplacedEntityRendererMixin(Context ctx) {
        super(ctx);
    }

    @Inject(method = "Lsoftware/bernie/geckolib3/renderers/geo/GeoReplacedEntityRenderer;render(Lnet/minecraft/entity/Entity;Lsoftware/bernie/geckolib3/core/IAnimatable;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/mob/MobEntity;getHoldingEntity()Lnet/minecraft/entity/Entity;"), remap = false)
    private void renderMixin(Entity entity, IAnimatable animatable, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn, CallbackInfo info) {
        NameplateRender.renderNameplate(this, (MobEntity) entity, stack, bufferIn, dispatcher, this.getTextRenderer(), isVisible((MobEntity) entity), packedLightIn);
    }

    @Inject(method = "hasLabel", at = @At(value = "RETURN", ordinal = 1), remap = false, cancellable = true)
    protected void hasLabelMixin(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof MobEntity)
            info.setReturnValue(false);
    }

    @Shadow(remap = false)
    protected boolean isVisible(LivingEntity livingEntityIn) {
        return false;
    }
}
