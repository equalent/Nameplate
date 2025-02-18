package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nameplate.Nameplate;
import net.nameplate.access.MobEntityAccess;
import net.nameplate.network.MobLevelPacket;

@Mixin(value = EntityTrackerEntry.class, priority = 1001)
public class EntityTrackerEntryMixin {

    @Shadow
    private final Entity entity;

    public EntityTrackerEntryMixin(Entity entity) {
        this.entity = entity;
    }

    @Inject(method = "startTracking", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayerEntity serverPlayer, CallbackInfo info) {
        if (entity instanceof MobEntity) {
            // Send packet if entity should show
            if (((MobEntityAccess) entity).showMobRpgLabel()) {
                int level = 1;
                if (DefaultAttributeRegistryAccessor.getRegistry().get(((MobEntity) entity).getType()) != null) {
                    // Calculate mob level
                    level = (int) (Nameplate.CONFIG.levelMultiplier * ((MobEntity) entity).getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)
                            / Math.abs(DefaultAttributeRegistryAccessor.getRegistry().get(((MobEntity) entity).getType()).getBaseValue(EntityAttributes.GENERIC_MAX_HEALTH)))
                            - Nameplate.CONFIG.levelMultiplier + 1;
                    if (level < 1)
                        level = 1;
                    ((MobEntityAccess) entity).setMobRpgLevel(level);
                }

                PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
                data.writeVarInt(level);
                data.writeVarInt(entity.getId());
                data.writeBoolean(((MobEntityAccess) entity).showMobRpgLabel());
                ServerPlayNetworking.send(serverPlayer, MobLevelPacket.SET_MOB_LEVEL, new PacketByteBuf(data));
            }
        }
    }

}
