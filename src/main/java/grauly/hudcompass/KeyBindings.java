package grauly.hudcompass;

import grauly.hudcompass.screens.ConfigureWaypointScreen;
import grauly.hudcompass.screens.waypointlist.WaypointListScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final KeyMapping.Category MAIN = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(HudCompass.MODID, "main"));
    private static final KeyMapping NEW_WAYPOINT = new KeyMapping(
            "key.hudcompass.newpoint",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            MAIN
    );
    private static final KeyMapping WAYPOINT_LIST = new KeyMapping(
            "key.hudcompass.waypointlist",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            MAIN
    );

    private static void tick(Minecraft client) {
        if (NEW_WAYPOINT.consumeClick()) {
            client.gui.setScreen(new ConfigureWaypointScreen(client.gui.screen()));
        }
        if (WAYPOINT_LIST.consumeClick()) {
            client.gui.setScreen(new WaypointListScreen(client.gui.screen()));
        }
    }

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(KeyBindings::tick);
    }
}
