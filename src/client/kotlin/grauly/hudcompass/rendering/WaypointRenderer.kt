package grauly.hudcompass.rendering

import grauly.hudcompass.waypoint.location.WaypointLocation
import net.minecraft.client.gui.DrawContext

interface WaypointRenderer {
    fun render(
        drawContext: DrawContext,
        waypointIconRenderer: WaypointIconRenderer,
        waypointLocation: WaypointLocation,
        centerX: Int,
        centerY: Int,
    )
}