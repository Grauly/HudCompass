package grauly.hudcompass.waypoint

import grauly.hudcompass.rendering.WaypointIconRenderer
import grauly.hudcompass.waypoint.location.WaypointLocation
import grauly.hudcompass.rendering.WaypointRenderState
import net.minecraft.util.Identifier

interface Waypoint {
    fun getLocation(): WaypointLocation
    fun shouldShow(dimensionId: Identifier): Boolean
    fun extractRenderState(): WaypointRenderState
}