package grauly.hudcompass.waypoint

import net.minecraft.util.Identifier

interface WaypointProvider {
    fun getWaypoints(worldId: String?, dimensionId: Identifier?): Collection<Waypoint>
}