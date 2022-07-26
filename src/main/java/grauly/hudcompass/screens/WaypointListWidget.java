package grauly.hudcompass.screens;

import com.google.common.collect.ImmutableList;
import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.waypoints.Waypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

@Environment(EnvType.CLIENT)
public class WaypointListWidget extends ElementListWidget<WaypointListWidget.Entry> {

    private MinecraftClient mc;
    private Screen parent;
    private Screen doneButton;

    public WaypointListWidget(Screen parent, MinecraftClient client) {
        //client, background width, background height, top margin pos, bottom margin pos, entry height
        super(client, parent.width, parent.height, 20, parent.height - 35, 25);
        mc = client;
        reloadList();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void reloadList() {
        this.clearEntries();
        for(Waypoint w : HudCompassClient.waypointManager.getWaypoints()) {
            this.addEntry(new WaypointEntry(w));
        }
    }

    @Environment(EnvType.CLIENT)
    public class WaypointEntry extends Entry {

        Waypoint waypoint;

        ButtonWidget deleteWaypointButton = new ButtonWidget(0, 0, 48, 20, Text.translatable("screen.hudcompass.waypointlist.delete"), c -> {
            HudCompassClient.waypointManager.removeWaypoint(waypoint);
            reloadList();
        });

        public WaypointEntry(Waypoint waypoint) {
            this.waypoint = waypoint;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of(deleteWaypointButton);
        }

        @Override
        public List<? extends Element> children() {
            return List.of(deleteWaypointButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            DrawableHelper.drawCenteredText(matrices, mc.textRenderer, waypoint.getName(), x + mc.textRenderer.getWidth(waypoint.getName())/2, y + (entryHeight / 2) - mc.textRenderer.fontHeight/2, -1);
            deleteWaypointButton.x = x + entryWidth - 5 - deleteWaypointButton.getWidth();
            deleteWaypointButton.y = y + entryHeight/2 - deleteWaypointButton.getHeight()/2;
            deleteWaypointButton.render(matrices,mouseX,mouseY,tickDelta);
        }


    }
    @Environment(EnvType.CLIENT)
    public static abstract class Entry extends ElementListWidget.Entry<Entry> {
        //can be empty according to any of the vanilla classes
    }
}
