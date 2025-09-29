package grauly.hudcompass.waypoint

import net.minecraft.util.math.Vec3d

data class StaticWaypointLocation(
    val x: Double,
    val y: Double,
    val z: Double
) : WaypointLocation {
    constructor(vector: Vec3d) : this(vector.x, vector.y, vector.z)

    override fun getVec3d(): Vec3d {
        return Vec3d(x, y, z)
    }
}