package net.nameplate.waila;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

public class NameplateWailaPlugin implements IWailaPlugin {

    private static final List<NameplateFeature> features = new ArrayList<>();

    @Override
    public void register(IRegistrar registrar) {
        features.add(new NameplateWailaMobInfo());
        features.forEach(feature -> feature.initialize(registrar));
    }

}
