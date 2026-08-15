package grauly.hudcompass.waypoints;

import net.minecraft.world.phys.Vec3;

public record WaypointLocation(double x, double y, double z) {

    public Vec3 toVec3d() {
        return new Vec3(x, y, z);
    }

    public static WaypointLocation fromVec3d(Vec3 vec3d) {
        return new WaypointLocation(vec3d.x(), vec3d.y(), vec3d.z());
    }
}
