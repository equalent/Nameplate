package net.nameplate.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.nameplate.access.MobEntityAccess;

public class MobLevelPacket {

    public static final Identifier SET_MOB_LEVEL = new Identifier("nameplate", "set_mob_level");

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(SET_MOB_LEVEL, (client, handler, buffer, responseSender) -> {
            int mobLevel = buffer.readVarInt();
            int mobId = buffer.readVarInt();
            boolean hasRpgLabel = buffer.readBoolean();
            client.execute(() -> {
                if (client.world.getEntityById(mobId) != null) {
                    ((MobEntityAccess) (MobEntity) client.world.getEntityById(mobId)).setMobRpgLevel(mobLevel);
                    if (!hasRpgLabel)
                        ((MobEntityAccess) (MobEntity) client.world.getEntityById(mobId)).setMobRpgLabel(hasRpgLabel);
                }
            });
        });

    }

}
