package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.nameplate.Nameplate;
import net.nameplate.access.MobEntityAccess;
import net.rpgdifficulty.api.MobStrengthener;

@Mixin(MobStrengthener.class)
public class MobStrengthenerMixin {

    @Inject(method = "changeAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void changeAttributesMixin(MobEntity mobEntity, ServerWorld world, CallbackInfo info, double mobHealthFactor) {
        setRpgStuff(mobEntity, mobHealthFactor);
    }

    @Inject(method = "changeOnlyHealthAttribute", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void changeOnlyHealthAttributeMixin(MobEntity mobEntity, ServerWorld world, CallbackInfo info, double mobHealthFactor) {
        setRpgStuff(mobEntity, mobHealthFactor);
    }

    @Inject(method = "changeBossAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void changeBossAttributesMixin(MobEntity mobEntity, ServerWorld world, CallbackInfo info, double mobHealthFactor) {
        setRpgStuff(mobEntity, mobHealthFactor);
    }

    @Inject(method = "changeEnderDragonAttribute", at = @At("TAIL"))
    private static void changeEnderDragonAttributeMixin(MobEntity mobEntity, ServerWorld world, CallbackInfo info) {
        ((MobEntityAccess) mobEntity).setMobRpgLabel(false);
    }

    private static void setRpgStuff(MobEntity mobEntity, double factor) {
        ((MobEntityAccess) mobEntity).setMobRpgLevel((int) Math.round(factor * 10.0F) - 9);
        if (Nameplate.CONFIG.excluded_entities.contains(mobEntity.getType().toString().replace("entity.", "")))
            ((MobEntityAccess) mobEntity).setMobRpgLabel(false);

    }

}
