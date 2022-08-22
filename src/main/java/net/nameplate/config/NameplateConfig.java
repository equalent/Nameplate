package net.nameplate.config;

import java.util.ArrayList;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "nameplate")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class NameplateConfig implements ConfigData {

    @Comment("Calculates Level: Real HP / Old HP * multiplier - multiplier + 1")
    public int levelMultiplier = 15;
    @Comment("0xAARRGGBB Format")
    public int nameColor = 553648127;
    public int backgroundColor = -1;
    public float backgroundOpacity = 0.4F;
    public boolean showHealth = false;
    @Comment("Setting if for example WTHIT is installed")
    public boolean showLevel = true;
    public boolean showHostileOnly = false;
    public double squaredDistance = 128.0D;
    @Comment("Example: minecraft:zombie or adventurez:brown_fungus")
    public ArrayList<String> excluded_entities = new ArrayList<>() {
        {
            add("minecraft:ender_dragon");
            add("minecraft:wither");
            add("adventurez:stone_golem");
            add("adventurez:void_shadow");
            add("adventurez:the_eye");
        }
    };
}