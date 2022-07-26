package grauly.hudcompass.waypoints;

import com.google.gson.Gson;
import grauly.hudcompass.HudCompass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.WorldSavePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class WaypointManager {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final String DIRECTORY_NAME = "hudCompassWaypoints";
    private WorldWaypoints worldWaypoints;
    private String currentlyLoadedWorld = null;

    public static String getDimensionID() {
        return mc.world.getRegistryKey().getValue().toString();
    }

    public static String getWorldID() {
        if (mc.getGame().getCurrentSession().isRemoteServer()) {
            return mc.getCurrentServerEntry().address;
        } else {
            return mc.getServer().getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
        }
    }

    public ArrayList<Waypoint> getWaypoints() {
        var newWorldID = getWorldID();
        if (!Objects.equals(currentlyLoadedWorld, newWorldID)) {
            if (currentlyLoadedWorld != null) {
                saveDataForWorld(currentlyLoadedWorld);
            }
            loadDataForWorld(newWorldID);
            currentlyLoadedWorld = newWorldID;
        }
        return worldWaypoints.getWaypoints();
    }

    public void addWaypoint(Waypoint waypoint) {
        worldWaypoints.getWaypoints().add(waypoint);
        saveDataForWorld(currentlyLoadedWorld);
    }

    public void removeWaypoint(Waypoint waypoint) {
        worldWaypoints.getWaypoints().remove(waypoint);
        saveDataForWorld(currentlyLoadedWorld);
    }

    private void loadDataForWorld(String worldID) {
        var path = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME, worldID + ".json");
        try {
            if (Files.exists(path)) {
                var content = Files.readString(path);
                worldWaypoints = new Gson().fromJson(content, WorldWaypoints.class);
            } else {
                worldWaypoints = new WorldWaypoints(getWorldID());
            }
        } catch (IOException e) {
            HudCompass.LOGGER.info("Failed to load waypoint data for {}, assuming none existent", worldID);
            e.printStackTrace();
            worldWaypoints = new WorldWaypoints(getWorldID());
        }
    }

    private void saveDataForWorld(String worldID) {
        var json = new Gson().toJson(worldWaypoints);
        var directoryPath = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME);
        var path = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME, worldID + ".json");
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.writeString(path, json);
        } catch (IOException e) {
            HudCompass.LOGGER.info("Failed to save waypoint data for {}, all data discarded", worldID);
            e.printStackTrace();
        }
    }
}
