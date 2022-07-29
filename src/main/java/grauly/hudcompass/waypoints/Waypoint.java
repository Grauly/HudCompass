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

    public Waypoint(Vec3d waypoint, String dimensionID, String name, int iconID) {
        this.waypoint = waypoint;
        this.name = name;
        this.dimensionID = dimensionID;
        this.iconID = iconID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waypoint waypoint1 = (Waypoint) o;

        if (isHidden != waypoint1.isHidden) return false;
        if (iconID != waypoint1.iconID) return false;
        if (!waypoint.equals(waypoint1.waypoint)) return false;
        if (!name.equals(waypoint1.name)) return false;
        if (!dimensionID.equals(waypoint1.dimensionID)) return false;
        return waypointID.equals(waypoint1.waypointID);
    }

    @Override
    public int hashCode() {
        return waypointID.hashCode();
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
