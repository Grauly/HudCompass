package grauly.hudcompass

import folk.sisby.kaleido.api.WrappedConfig
import grauly.hudcompass.config.HudCompassConfig
import grauly.hudcompass.waypoint.WaypointStore
import grauly.hudcompass.waypoint.provider.HudCompassWaypointProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HudCompassClient : ClientModInitializer {
    const val MODID = "hudcompass"
    val logger: Logger = LoggerFactory.getLogger(MODID)
	val hudCompassWaypoints: HudCompassWaypointProvider = HudCompassWaypointProvider()
    val config = WrappedConfig.createToml(
    FabricLoader.getInstance().configDir,
        MODID,
        "main",
        HudCompassConfig::class.java
    )

    override fun onInitializeClient() {
        KeyBindings.init()
        WaypointStore.registerProvider(HudCompassWaypointProvider.ID, hudCompassWaypoints)
    }
}