package grauly.hudcompass.waypoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public void hideWaypoint(Waypoint waypoint, boolean hidden) {
        worldWaypoints.getWaypoints().stream()
                .filter(w -> w.getWaypointID().equals(waypoint.getWaypointID()))
                .forEach(w -> w.setHidden(hidden));
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
        } catch (NumberFormatException e) {
            HudCompass.LOGGER.info("Failed to parse waypoint data for {}, assuming file is corrupt. Backing up old file and starting over", worldID);
            e.printStackTrace();
            try {
                var content = Files.readString(path);
                var newPath = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME, worldID + ".backup");
                if(!Files.exists(newPath)) {
                    Files.createFile(newPath);
                }
                Files.writeString(newPath,content);
                HudCompass.LOGGER.info("Successfully saved old data as {}.backup",worldID);
            } catch (IOException e2) {
                HudCompass.LOGGER.info("Failed to save old waypoint data for {}, sorry.", worldID);
            }
        }
    }

    private void saveDataForWorld(String worldID) {
        var directoryPath = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME);
        var path = Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), DIRECTORY_NAME, worldID + ".json");
        try {
            if(worldWaypoints.getWaypoints().isEmpty()) {
                if(Files.exists(path)) {
                    Files.delete(path);
                }
            }
        } catch (IOException e) {
            HudCompass.LOGGER.info("Failed to delete waypoint file for {}",worldID);
            e.printStackTrace();
        }
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var json = gson.toJson(worldWaypoints);
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
