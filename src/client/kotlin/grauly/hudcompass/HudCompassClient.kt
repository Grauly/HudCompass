package grauly.hudcompass

import grauly.hudcompass.waypoint.WaypointStore
import grauly.hudcompass.waypoint.provider.HudCompassWaypointProvider
import net.fabricmc.api.ClientModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HudCompassClient : ClientModInitializer {
    const val MODID = "hudcompass"
    val logger: Logger = LoggerFactory.getLogger(MODID)
	val hudCompassWaypoints: HudCompassWaypointProvider = HudCompassWaypointProvider()

    override fun onInitializeClient() {
        KeyBindings.init()
        WaypointStore.registerProvider(HudCompassWaypointProvider.ID, hudCompassWaypoints)
    }
}