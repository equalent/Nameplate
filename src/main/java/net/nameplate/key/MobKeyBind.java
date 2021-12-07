package net.nameplate.key;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.nameplate.access.ClientAccess;

public class MobKeyBind {

    public static KeyBinding showLevelKeyBind;

    public static void init() {
        showLevelKeyBind = new KeyBinding("key.nameplate.shownameplate", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_ADD, "category.nameplate.keybind");
        KeyBindingHelper.registerKeyBinding(showLevelKeyBind);
        // Callback
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (showLevelKeyBind.wasPressed()) {
                ((ClientAccess) client).toggleMobLevelRenderer();
            }
        });
    }

}
