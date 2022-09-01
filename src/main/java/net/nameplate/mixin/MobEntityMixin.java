package net.nameplate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.nameplate.Nameplate;
import net.nameplate.access.MobEntityAccess;

@Mixin(MobEntity.class)
public class MobEntityMixin implements MobEntityAccess {

    private int mobRpgLevel = 1;
    private boolean showMobRpgLabel = true;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initMixin(EntityType<? extends MobEntity> entityType, World world, CallbackInfo info) {
        if (Nameplate.CONFIG.excludedEntities.contains(((MobEntity) (Object) this).getType().toString().replace("entity.", "").replace(".", ":")))
            this.showMobRpgLabel = false;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("MobRpgLevel", this.mobRpgLevel);
        nbt.putBoolean("HasMobRpgLabel", this.showMobRpgLabel);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo info) {
        this.mobRpgLevel = nbt.getInt("MobRpgLevel");
        this.showMobRpgLabel = nbt.getBoolean("HasMobRpgLabel");
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
    public boolean showMobRpgLabel() {
        return this.showMobRpgLabel;
    }

    @Override
    public void setShowMobRpgLabel(boolean setLabel) {
        this.showMobRpgLabel = setLabel;
    }
}
