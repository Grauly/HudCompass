package grauly.hudcompass.rendering.waypoint

import net.minecraft.util.Identifier

data class StaticWaypointIconProvider(
    private val icon: Identifier
): WaypointIconProvider {
    override fun getIcon(
        distance: Double,
        angle: Double,
        centerAngleOffset: Double
    ): Identifier = icon
}