package grauly.hudcompass.waypoints;


import net.minecraft.util.math.Vec3d;

public class Waypoint {
    private Vec3d waypoint;
    private String name;
    private String dimensionID;

    public Waypoint(Vec3d waypoint, String dimensionID, String name) {
        this.waypoint = waypoint;
        this.name = name;
        this.dimensionID = dimensionID;
    }

    public Waypoint(float x, float y, float z, String dimensionID, String name) {
        this.waypoint = new Vec3d(x,y,z);
        this.name = name;
        this.dimensionID = dimensionID;
    }

    public Vec3d getWaypoint() {
        return waypoint;
    }

    public String getName() {
        return name;
    }

    public String getDimensionID() {
        return dimensionID;
    }
}
