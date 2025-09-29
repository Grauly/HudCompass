package grauly.hudcompass.waypoint

import grauly.hudcompass.rendering.WaypointIconRenderer
import net.minecraft.util.Identifier

interface Waypoint {
    fun getLocation(): WaypointLocation
    fun getDimensionId(): Identifier
    fun getIconRenderer(): WaypointIconRenderer

    fun toRenderState(): WaypointRenderState = WaypointRenderState(getLocation(), getIconRenderer())
}