package net.nameplate.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import net.nameplate.Nameplate;
import net.nameplate.access.MobEntityAccess;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
public class NameplateRender {

    public static void renderNameplate(EntityRenderer entityRenderer, MobEntity mobEntity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, EntityRenderDispatcher dispatcher,
            TextRenderer textRenderer, boolean isVisible, int i) {
        if (MinecraftClient.isHudEnabled() && Nameplate.CONFIG.showLevel && dispatcher.getSquaredDistanceToCamera(mobEntity) <= Nameplate.CONFIG.squaredDistance && !mobEntity.hasPassengers())
            if (isVisible && ((MobEntityAccess) mobEntity).showMobRpgLabel()) {
                matrices.push();
                matrices.translate(0.0D, (double) mobEntity.getHeight() + 0.5F, 0.0D);
                matrices.multiply(dispatcher.getRotation());
                matrices.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                float o = dispatcher.gameOptions.getTextBackgroundOpacity(Nameplate.CONFIG.backgroundOpacity);
                int j = (int) (o * 255.0F) << 24;
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
}
