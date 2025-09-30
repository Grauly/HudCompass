package grauly.hudcompass.waypoint.impl

import grauly.hudcompass.rendering.StaticWaypointIconProvider
import grauly.hudcompass.rendering.WaypointRenderState
import grauly.hudcompass.waypoint.Waypoint
import grauly.hudcompass.waypoint.location.StaticWaypointLocation
import grauly.hudcompass.waypoint.location.WaypointLocation
import net.minecraft.util.Identifier
import java.util.UUID

data class HudCompassWaypoint(
    var location: StaticWaypointLocation,
    var name: String,
    var hidden: Boolean,
    val dimensionId: Identifier,
    var iconId: Identifier,
    val uuid: UUID = UUID.randomUUID()
): Waypoint {

    override fun getLocation(): WaypointLocation = location

    override fun shouldShow(dimensionId: Identifier): Boolean {
        return dimensionId == this.dimensionId && !hidden
    }

    override fun extractRenderState(): WaypointRenderState {
        val state = WaypointRenderState(
            getLocation(),
            StaticWaypointIconProvider(iconId)
        )
        state.setData(WaypointRenderState.NAME, name)
        return state
    }
}