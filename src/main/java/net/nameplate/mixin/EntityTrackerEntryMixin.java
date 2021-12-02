package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nameplate.access.MobEntityAccess;
import net.nameplate.network.MobLevelPacket;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    @Shadow
    private final Entity entity;

    public EntityTrackerEntryMixin(Entity entity) {
        this.entity = entity;
    }

    @Inject(method = "startTracking", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayerEntity serverPlayer, CallbackInfo info) {
        if (entity instanceof MobEntity) {
            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeVarInt(((MobEntityAccess) entity).getMobRpgLevel());
            data.writeVarInt(entity.getId());
            data.writeBoolean(((MobEntityAccess) entity).hasMobRpgLabel());
            ServerPlayNetworking.send(serverPlayer, MobLevelPacket.SET_MOB_LEVEL, new PacketByteBuf(data));
        }
    }

}
