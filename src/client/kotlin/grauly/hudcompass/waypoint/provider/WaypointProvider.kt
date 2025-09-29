package grauly.hudcompass.waypoint.provider

import grauly.hudcompass.waypoint.Waypoint
import net.minecraft.util.Identifier

interface WaypointProvider {
    fun getWaypoints(worldId: String?, dimensionId: Identifier?): Collection<Waypoint>
}