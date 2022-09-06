package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.nameplate.util.NameplateRender;

@Environment(EnvType.CLIENT)
@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {

    public MobEntityRendererMixin(Context ctx, M model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderMixin(T mobEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i, CallbackInfo info) {
        NameplateRender.renderNameplate(this, mobEntity, matrices, vertexConsumers, dispatcher, this.getTextRenderer(), this.isVisible(mobEntity), i);
    }

    @Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
    protected void hasLabelMixin(T mobEntity, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }
}
