package grauly.hudcompass.waypoint

import grauly.hudcompass.rendering.WaypointIconRenderer

data class WaypointRenderState(
    val location: WaypointLocation,
    val iconRenderState: WaypointIconRenderer
)