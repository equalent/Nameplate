package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.nameplate.access.MobEntityAccess;

@Mixin(MobEntity.class)
public class MobEntityMixin implements MobEntityAccess {

    private int mobRpgLevel = 1;
    private boolean hasMobRpgLabel = true;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("MobRpgLevel", this.mobRpgLevel);
        nbt.putBoolean("HasMobRpgLabel", this.hasMobRpgLabel);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo info) {
        this.mobRpgLevel = nbt.getInt("MobRpgLevel");
        this.hasMobRpgLabel = nbt.getBoolean("HasMobRpgLabel");
    }

    @Override
    public void setMobRpgLevel(int level) {
        this.mobRpgLevel = level;
    }

    @Override
    public int getMobRpgLevel() {
        return this.mobRpgLevel;
    }

    @Override
    public boolean hasMobRpgLabel() {
        return this.hasMobRpgLabel;
    }

    @Override
    public void setMobRpgLabel(boolean setLabel) {
        this.hasMobRpgLabel = setLabel;
    }
}
