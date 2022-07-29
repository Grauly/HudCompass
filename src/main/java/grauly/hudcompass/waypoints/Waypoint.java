package grauly.hudcompass.waypoints;


import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class Waypoint {
    private Vec3d waypoint;
    private String name;
    private String dimensionID;
    private boolean isHidden;
    private int iconID;
    private UUID waypointID;

    public Waypoint(Vec3d waypoint, String dimensionID, String name) {
        this.waypoint = waypoint;
        this.name = name;
        this.dimensionID = dimensionID;
        this.iconID = 11;
        waypointID = UUID.randomUUID();
    }

    public Waypoint(Vec3d waypoint, String name, String dimensionID, boolean isHidden, int iconID, UUID waypointID) {
        this.waypoint = waypoint;
        this.name = name;
        this.dimensionID = dimensionID;
        this.isHidden = isHidden;
        this.iconID = iconID;
        this.waypointID = waypointID;
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

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getIconID() {
        return iconID;
    }

    public UUID getWaypointID() {
        return waypointID;
    }
}
