package grauly.hudcompass;

import grauly.hudcompass.rendering.HUDCompassRenderer;
import grauly.hudcompass.resources.IconStore;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.resources.Identifier;

public class HudCompassClient implements ClientModInitializer {

    public static final WaypointManager waypointManager = new WaypointManager();
    public static final Minecraft mc = Minecraft.getInstance();
    public static final IconStore ICON_STORE = new IconStore();
    public static final Identifier COMPASS_LAYER = Identifier.fromNamespaceAndPath(HudCompass.MODID, "compass");

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.SUBTITLES, COMPASS_LAYER, HUDCompassRenderer::onRender);
        KeyBindings.init();
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(
                Identifier.fromNamespaceAndPath(HudCompass.MODID, "icons"),
                ICON_STORE
        );
    }
}
