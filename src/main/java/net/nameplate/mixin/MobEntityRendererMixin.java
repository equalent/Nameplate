package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import net.nameplate.Nameplate;
import net.nameplate.access.MobEntityAccess;

@Environment(EnvType.CLIENT)
@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {

    public MobEntityRendererMixin(Context ctx, M model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    // Could inject into ServerBossBar and set level there
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", shift = Shift.AFTER))
    private void renderMixin(T mobEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i, CallbackInfo info) {
        if (MinecraftClient.isHudEnabled() && Nameplate.CONFIG.showLevel && this.dispatcher.getSquaredDistanceToCamera(mobEntity) <= Nameplate.CONFIG.squaredDistance)
            if (this.isVisible(mobEntity) && ((MobEntityAccess) mobEntity).showMobRpgLabel()) {
                matrices.push();
                matrices.translate(0.0D, (double) mobEntity.getHeight() + 0.5F, 0.0D);
                matrices.multiply(this.dispatcher.getRotation());
                matrices.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                float o = this.dispatcher.gameOptions.getTextBackgroundOpacity(Nameplate.CONFIG.backgroundOpacity);
                int j = (int) (o * 255.0F) << 24;
                TextRenderer textRenderer = this.getTextRenderer();
                String string = mobEntity.hasCustomName() ? mobEntity.getCustomName().getString() : mobEntity.getName().getString();
                if (Nameplate.CONFIG.showHealth) {
                    string = string + " " + Text.translatable("text.nameplate.health", Math.round(mobEntity.getHealth()), Math.round(mobEntity.getMaxHealth())).getString();
                }
                // string = new TranslatableText("text.nameplate.level", Math.round(mobEntity.getMaxHealth() / Nameplate.CONFIG.levelDivider)).getString() + string;
                string = Text.translatable("text.nameplate.level", ((MobEntityAccess) mobEntity).getMobRpgLevel()).getString() + string;

                Text text = Text.of(string);
                float h = (float) (-textRenderer.getWidth(text) / 2);
                textRenderer.draw(text, h, 0.0F, Nameplate.CONFIG.nameColor, false, matrix4f, vertexConsumers, true, j, i);
                textRenderer.draw(text, h, 0.0F, Nameplate.CONFIG.backgroundColor, false, matrix4f, vertexConsumers, false, 0, i);
                matrices.pop();
            }
    }

    @Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
    protected void hasLabelMixin(T mobEntity, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }
}
