package grauly.hudcompass;

import grauly.hudcompass.rendering.HUDCompassRenderer;
import grauly.hudcompass.screens.ConfigureWaypointScreen;
import grauly.hudcompass.screens.WaypointListScreen;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class HudCompassClient implements ClientModInitializer {

    public static final WaypointManager waypointManager = new WaypointManager();
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Identifier COMPASS_LAYER = Identifier.of(HudCompass.MODID, "compass");

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.SUBTITLES, COMPASS_LAYER, HUDCompassRenderer::onRender);
        var newWaypointKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hudcompass.newpoint",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.hudcompass.main"
        ));
        var waypointListKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hudcompass.waypointlist",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.hudcompass.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (newWaypointKeyBind.wasPressed()) {
                mc.setScreen(new ConfigureWaypointScreen(mc.currentScreen));
            }
            if (waypointListKeyBind.wasPressed()) {
                mc.setScreen(new WaypointListScreen(mc.currentScreen));
            }
        });
    }
}
