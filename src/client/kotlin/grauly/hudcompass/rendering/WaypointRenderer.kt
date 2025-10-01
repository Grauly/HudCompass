package grauly.hudcompass.rendering

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

interface WaypointRenderer {
    fun render(
        drawContext: DrawContext,
        renderState: WaypointRenderState,
        client: MinecraftClient,
        distance: Double,
        angle: Double,
        angleOffset: Double,
        centerX: Int,
        centerY: Int,
    )
}