package grauly.hudcompass

import grauly.hudcompass.screens.ConfigureWaypointScreen
import grauly.hudcompass.screens.waypointlist.WaypointListScreen
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

object KeyBindings {
    val MAIN: KeyBinding.Category = KeyBinding.Category.create(Identifier.of(HudCompass.MODID, "main"))
    val NEW_WAYPOINT: KeyBinding = KeyBinding(
        "key.hudcompass.newpoint",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_B,
        MAIN
    )
    val WAYPOINT_LIST: KeyBinding = KeyBinding(
        "key.hudcompass.waypointlist",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_V,
        MAIN
    )

    private fun tick(client: MinecraftClient) {
/*
        if (NEW_WAYPOINT.wasPressed()) {
            client.setScreen(ConfigureWaypointScreen(client.currentScreen))
        }
        if (WAYPOINT_LIST.wasPressed()) {
            client.setScreen(WaypointListScreen(client.currentScreen))
        }
*/
    }

    fun init() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { this::tick })
    }
}