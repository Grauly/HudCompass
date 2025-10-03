package grauly.hudcompass.waypoint.provider

import grauly.hudcompass.waypoint.Waypoint
import net.minecraft.util.Identifier

interface WaypointProvider<T: Waypoint> {
    fun getWaypoints(worldId: String?, dimensionId: Identifier?): Collection<T>
}