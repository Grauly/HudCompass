package grauly.hudcompass.screens.waypointlist;

import grauly.hudcompass.HudCompass;
import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.waypoints.Waypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.resources.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class WaypointListWidget extends ContainerObjectSelectionList<WaypointListWidget.Entry> {

    public static final Color gray = new Color(0.9f, 0.9f, 0.9f, 0.7f);
    public static final Identifier visibleTextureIdentifier = Identifier.withDefaultNamespace("textures/mob_effect/night_vision.png");
    public static final Identifier hiddenTextureIdentifier = Identifier.withDefaultNamespace("textures/mob_effect/blindness.png");
    public static final Identifier otherDimensionIdentifier = Identifier.fromNamespaceAndPath(HudCompass.MODID, "textures/ui/other_dimension_indicator.png");
    final Screen parent;

    public WaypointListWidget(Screen parent, Minecraft client, int width, int height) {
        //client, background width, background height, top margin pos, bottom margin pos, entry height
        //super(client, parent.width, parent.height, 20, parent.height - 35, 25);
        //client, background width, background height, top margin pos, entry height
        //super(client, parent.width, parent.height - WaypointListScreen.BOTTOM_TEXT_HEIGHT, 20, 35);
        super(client, width, height, 20, 25);
        this.parent = parent;
        reloadList();
    }

    @Override
    public int getRowWidth() {
        //standard is 220
        return 320;
    }

    public void reloadList() {
        this.clearEntries();
        for (Waypoint w : HudCompassClient.waypointManager.getWaypoints()) {
            this.addEntry(new WaypointEntry(this, w));
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends net.minecraft.client.gui.components.ContainerObjectSelectionList.Entry<Entry> {
        //can be empty according to any of the vanilla classes
    }

}
