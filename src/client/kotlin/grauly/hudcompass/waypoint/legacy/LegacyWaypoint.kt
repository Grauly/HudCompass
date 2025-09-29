package grauly.hudcompass.waypoint.legacy


import grauly.hudcompass.waypoint.StaticWaypointLocation
import java.util.*


data class LegacyWaypoint(
    var waypoint: StaticWaypointLocation,
    var name: String,
    val dimensionID: String,
    var isHidden: Boolean = false,
    var iconID: Int = 0,
    val waypointID: UUID = UUID.randomUUID(),
)
