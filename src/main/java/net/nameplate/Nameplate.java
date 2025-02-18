package net.nameplate;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.nameplate.config.NameplateConfig;
import net.nameplate.network.MobLevelPacket;

public class Nameplate implements ClientModInitializer {

    public static NameplateConfig CONFIG = new NameplateConfig();

    @Override
    public void onInitializeClient() {
        AutoConfig.register(NameplateConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(NameplateConfig.class).getConfig();
        MobLevelPacket.init();
    }

}
