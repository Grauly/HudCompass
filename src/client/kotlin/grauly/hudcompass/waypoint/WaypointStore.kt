package grauly.hudcompass.waypoint

import grauly.hudcompass.HudCompassClient
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.Identifier
import net.minecraft.util.WorldSavePath
import java.util.*

object WaypointStore {
    private val waypointProviders: MutableMap<Identifier, WaypointProvider> = mutableMapOf()
    private val enabledProviders: MutableList<Identifier> = mutableListOf()
    private var dimensionId: Identifier? = null
    private var worldId: String? = null;

    init {
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register { client, world -> updateWorldAndDimensionId(client, world) }
    }

    fun getRenderWaypoints(): Collection<WaypointRenderState> {
        val localDimensionId = dimensionId ?: return Collections.emptySet()
        return waypointProviders
            .filterKeys { enabledProviders.contains(it) }
            .values
            .map { it.getWaypoints(worldId, localDimensionId).toMutableList() }
            .reduce { acc, waypoints -> acc.addAll(waypoints); acc }
            .filter { it.getDimensionId() == localDimensionId }
            .map { it.toRenderState() }
    }

    fun registerProvider(identifier: Identifier, provider: WaypointProvider, enabled: Boolean = true) {
        if (waypointProviders.contains(identifier)) throw IllegalArgumentException("Provider with ID: $identifier is already registered.")
        waypointProviders[identifier] = provider
        if (enabled) {
            enabledProviders.add(identifier)
        }
    }

    fun setEnabled(identifier: Identifier, enabled: Boolean) {
        if (enabled) {
            enabledProviders.add(identifier)
        } else {
            enabledProviders.remove(identifier)
        }
    }

    private fun updateWorldAndDimensionId(client: MinecraftClient, newWorld: ClientWorld) {
        dimensionId = newWorld.registryKey.value
        worldId = client.currentServerEntry?.address ?: client.server?.getSavePath(WorldSavePath.ROOT)?.parent?.fileName.toString()
        HudCompassClient.logger.info("changing dimensionId to: $dimensionId and worldId to $worldId")
    }
}