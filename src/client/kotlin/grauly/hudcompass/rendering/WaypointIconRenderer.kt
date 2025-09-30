package grauly.hudcompass.rendering

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

@FunctionalInterface
interface WaypointIconRenderer {
    fun render(
        drawContext: DrawContext,
        iconRenderState: WaypointRenderState,
        client: MinecraftClient,
        distance: Double,
        angle: Double,
        angleOffset: Double,
        centerX: Int,
        centerY: Int,
    )
}