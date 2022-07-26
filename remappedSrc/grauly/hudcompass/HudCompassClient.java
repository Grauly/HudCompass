package grauly.hudcompass;

import grauly.hudcompass.events.WordIdentifierListener;
import grauly.hudcompass.rendering.HUDCompassRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class HudCompassClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(HUDCompassRenderer::onRender);
    }
}
