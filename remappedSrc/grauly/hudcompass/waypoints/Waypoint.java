package grauly.hudcompass.waypoints;


import net.minecraft.util.math.Vec3d;

public class Waypoint {
    private Vec3d waypoint;
    private String name;

    public Waypoint(Vec3d waypoint, String name) {
        this.waypoint = waypoint;
        this.name = name;
    }

    public Waypoint(float x, float y, float z, String name) {
        this.waypoint = new Vec3d(x,y,z);
        this.name = name;
    }

    public Vec3d getWaypoint() {
        return waypoint;
    }

    public String getName() {
        return name;
    }
}
