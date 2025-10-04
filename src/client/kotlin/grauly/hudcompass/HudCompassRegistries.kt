package grauly.hudcompass

import com.mojang.serialization.Lifecycle
import grauly.hudcompass.rendering.waypoint.WaypointRenderer
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier

object HudCompassRegistries {
    val waypointRendererRegistryKey: RegistryKey<Registry<WaypointRenderer>> = RegistryKey.ofRegistry(Identifier.of(HudCompassClient.MODID, "waypoint_renderer"))
    val waypointRendererRegistry: SimpleRegistry<WaypointRenderer> = SimpleRegistry(waypointRendererRegistryKey, Lifecycle.stable())
}