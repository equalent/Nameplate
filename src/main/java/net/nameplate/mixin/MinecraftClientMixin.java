package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.nameplate.access.ClientAccess;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements ClientAccess {

    private boolean showMobNameplate = true;

    @Override
    public void toggleMobLevelRenderer() {
        if (showMobNameplate)
            showMobNameplate = false;
        else
            showMobNameplate = true;
    }

    @Override
    public boolean showMobNameplate() {
        return this.showMobNameplate;
    }

}
