package grauly.hudcompass.waypoints;

import java.util.ArrayList;

public class WorldWaypoints {

    private String worldID;
    private ArrayList<Waypoint> waypoints;

    public WorldWaypoints(String worldID) {
        this.worldID = worldID;
        this.waypoints = new ArrayList<>();
    }

    public String getWorldID() {
        return worldID;
    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }
}
