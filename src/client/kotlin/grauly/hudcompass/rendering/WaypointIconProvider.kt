package grauly.hudcompass.rendering

import net.minecraft.util.Identifier

interface WaypointIconProvider {
    fun getIcon(distance: Double, angle: Double, centerAngleOffset: Double): Identifier
}