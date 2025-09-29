package grauly.hudcompass.rendering

import grauly.hudcompass.waypoint.location.WaypointLocation

data class WaypointRenderState(
    val location: WaypointLocation,
    val iconRenderState: WaypointIconRenderer
)