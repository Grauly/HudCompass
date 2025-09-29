package grauly.hudcompass;

import grauly.hudcompass.screens.ConfigureWaypointScreen;
import grauly.hudcompass.screens.waypointlist.WaypointListScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final KeyBinding.Category MAIN = KeyBinding.Category.create(Identifier.of(HudCompass.MODID, "main"));
    private static final KeyBinding NEW_WAYPOINT = new KeyBinding(
            "key.hudcompass.newpoint",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            MAIN
    );
    private static final KeyBinding WAYPOINT_LIST = new KeyBinding(
            "key.hudcompass.waypointlist",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            MAIN
    );

    private static void tick(MinecraftClient client) {
        if (NEW_WAYPOINT.wasPressed()) {
            client.setScreen(new ConfigureWaypointScreen(client.currentScreen));
        }
        if (WAYPOINT_LIST.wasPressed()) {
            client.setScreen(new WaypointListScreen(client.currentScreen));
        }
    }

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(KeyBindings::tick);
    }
}
