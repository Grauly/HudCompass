package grauly.hudcompass.waypoints;

import net.minecraft.util.math.Vec3d;

public record WaypointLocation(double x, double y, double z) {

    public Vec3d toVec3d() {
        return new Vec3d(x, y, z);
    }

    public static WaypointLocation fromVec3d(Vec3d vec3d) {
        return new WaypointLocation(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }
}
