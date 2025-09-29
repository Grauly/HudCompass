package grauly.hudcompass.waypoint

import net.minecraft.util.math.Vec3d

interface WaypointLocation {
    fun getVec3d(): Vec3d
}