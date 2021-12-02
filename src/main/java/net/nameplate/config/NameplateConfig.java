package net.nameplate.config;

import java.util.Arrays;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "nameplate")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class NameplateConfig implements ConfigData {

    @Comment("0xAARRGGBB Format")
    public int nameColor = 553648127;
    public int backgroundColor = -1;
    public float backgroundOpacity = 0.4F;
    public boolean showHealth = false;
    public boolean showLevel = true;
    @Comment("Example: minecraft.zombie or adventurez.brown_fungus")
    public List<String> excluded_entities = Arrays.asList("minecraft.wither", "adventurez.stone_golem", "adventurez.void_shadow", "adventurez.the_eye");
}