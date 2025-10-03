package grauly.hudcompass.waypoint.provider

import grauly.hudcompass.waypoint.Waypoint

interface WritebackWaypointProvider<T: Waypoint>: WaypointProvider<T> {
    fun updateWaypoint(waypoint: T)
}