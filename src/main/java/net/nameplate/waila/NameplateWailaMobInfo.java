package net.nameplate.waila;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaConstants;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nameplate.access.MobEntityAccess;

public class NameplateWailaMobInfo extends NameplateFeature implements IEntityComponentProvider {

    public static Identifier MOB_LEVEL_INFO = new Identifier("nameplate", "mob_level_info");

    @Override
    public void initialize(IRegistrar registrar) {
        registrar.addConfig(MOB_LEVEL_INFO, true);
        registrar.addComponent(this, TooltipPosition.BODY, MobEntity.class);
    }

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        IEntityComponentProvider.super.appendBody(tooltip, accessor, config);
        if (config.getBoolean(MOB_LEVEL_INFO) && ((MobEntityAccess) accessor.getEntity()).showMobRpgLabel()) {
            tooltip.setLine(WailaConstants.OBJECT_NAME_TAG,
                    Text.translatable("text.nameplate.level", String.valueOf("§e" + ((MobEntityAccess) accessor.getEntity()).getMobRpgLevel()), accessor.getEntity().getName()));
        }
    }

}
