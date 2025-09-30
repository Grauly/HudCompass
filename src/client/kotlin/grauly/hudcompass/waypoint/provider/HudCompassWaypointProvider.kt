package grauly.hudcompass.waypoint.provider

import grauly.hudcompass.HudCompassClient
import grauly.hudcompass.waypoint.Waypoint
import grauly.hudcompass.waypoint.impl.HudCompassWaypoint
import net.minecraft.util.Identifier
import java.util.Collections
import java.util.UUID

class HudCompassWaypointProvider: WaypointProvider {
    private var cachedWorldWaypoints: Map<UUID, HudCompassWaypoint> = mutableMapOf()

    override fun getWaypoints(
        worldId: String?,
        dimensionId: Identifier?
    ): Collection<Waypoint> {
        worldId ?: return Collections.emptySet()
        dimensionId ?: return Collections.emptySet()

        return cachedWorldWaypoints.values
            .filter { it.dimensionId == dimensionId }
    }

    companion object {
        val ID: Identifier = Identifier.of(HudCompassClient.MODID, "default")
    }
}