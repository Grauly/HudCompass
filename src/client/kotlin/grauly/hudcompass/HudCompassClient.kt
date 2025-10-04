package grauly.hudcompass

import folk.sisby.kaleido.api.WrappedConfig
import grauly.hudcompass.config.HudCompassConfig
import grauly.hudcompass.rendering.waypoint.HudCompassWaypointRenderer
import grauly.hudcompass.waypoint.WaypointStore
import grauly.hudcompass.waypoint.provider.HudCompassWaypointProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
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

        Registry.register(
            HudCompassRegistries.waypointRendererRegistry,
            Identifier.of(MODID, "default"),
            HudCompassWaypointRenderer(showName = true, showDistance = false)
        )
        Registry.register(
            HudCompassRegistries.waypointRendererRegistry,
            Identifier.of(MODID, "only_icon"),
            HudCompassWaypointRenderer(showName = false, showDistance = false)
        )
        Registry.register(
            HudCompassRegistries.waypointRendererRegistry,
            Identifier.of(MODID, "only_distance"),
            HudCompassWaypointRenderer(showName = false, showDistance = true)
        )
        Registry.register(
            HudCompassRegistries.waypointRendererRegistry,
            Identifier.of(MODID, "distance_and_name"),
            HudCompassWaypointRenderer(showName = true, showDistance = true)
        )
    }
}