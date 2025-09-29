package grauly.hudcompass.waypoint

import net.minecraft.util.Identifier

interface WaypointProvider {
    fun getWaypoints(worldId: Identifier): Waypoint
}