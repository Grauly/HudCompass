package grauly.hudcompass.rendering

import net.minecraft.client.gui.DrawContext

interface WaypointIconRenderer {
    fun render(
        drawContext: DrawContext,
        centerX: Int,
        centerY: Int,
    )
}