package grauly.hudcompass;

import grauly.hudcompass.rendering.HUDCompassRenderer;
import grauly.hudcompass.screens.NewWaypointScreen;
import grauly.hudcompass.screens.WaypointListScreen;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HudCompassClient implements ClientModInitializer {

    public static final WaypointManager waypointManager = new WaypointManager();
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(HUDCompassRenderer::onRender);
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
                mc.setScreen(new NewWaypointScreen(mc.currentScreen));
            }
            if (waypointListKeyBind.wasPressed()) {
                mc.setScreen(new WaypointListScreen(mc.currentScreen));
            }
        });
    }
}
