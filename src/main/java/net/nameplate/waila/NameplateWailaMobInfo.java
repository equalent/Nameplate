package net.nameplate.waila;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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
    public void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(MOB_LEVEL_INFO) && ((MobEntityAccess) accessor.getEntity()).hasMobRpgLabel()) {
            tooltip.add(new TranslatableText("text.nameplate.level", ((MobEntityAccess) accessor.getEntity()).getMobRpgLevel()).formatted(Formatting.YELLOW));
        }
    }

}
