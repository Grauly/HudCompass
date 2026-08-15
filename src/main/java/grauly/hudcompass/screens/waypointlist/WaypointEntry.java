package grauly.hudcompass.screens.waypointlist;

import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.screens.ConfigureWaypointScreen;
import grauly.hudcompass.util.RendererHelper;
import grauly.hudcompass.waypoints.Waypoint;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@Environment(EnvType.CLIENT)
public class WaypointEntry extends WaypointListWidget.Entry {

    private final WaypointListWidget waypointListWidget;
    private final Minecraft client;
    Waypoint waypoint;
    boolean hidden;

    private final Button deleteWaypointButton;
    private final Button hideWaypointButton;
    private final Button editWaypointButton;
    private final Button teleportWaypointButton;

    public WaypointEntry(WaypointListWidget waypointListWidget, Waypoint waypoint) {
        this.waypointListWidget = waypointListWidget;
        this.client = Minecraft.getInstance();
        this.waypoint = waypoint;
        this.hidden = waypoint.isHidden();
        deleteWaypointButton = Button
                .builder(Component.translatable("screen.hudcompass.waypointlist.delete"), button -> {
                    HudCompassClient.waypointManager.removeWaypoint(waypoint);
                    waypointListWidget.reloadList();
                })
                .bounds(0, 0, 48, 20)
                .build();
        hideWaypointButton = Button
                .builder(Component.empty(), button -> {
                    HudCompassClient.waypointManager.hideWaypoint(waypoint, !hidden);
                    waypointListWidget.reloadList();
                }).bounds(0, 0, 20, 20)
                .build();
        editWaypointButton = Button
                .builder(Component.translatable("screen.hudcompass.waypointlist.edit"), button -> {
                    client.setScreen(new ConfigureWaypointScreen(waypointListWidget.parent, waypoint));
                }).bounds(0, 0, 35, 20)
                .build();
        teleportWaypointButton = Button
                .builder(Component.translatable("screen.hudcompass.waypointlist.teleport"), button -> {
                    Vec3 point = waypoint.getWaypoint();
                    var isSameDimension = waypoint.getDimensionID().equals(WaypointManager.getDimensionID());
                    String command;
                    if (isSameDimension) {
                        command = "tp " + point.x + " " + point.y + " " + point.z;
                    } else {
                        command = "execute in " + waypoint.getDimensionID() + " run tp " + point.x + " " + point.y + " " + point.z;
                    }
                    client.getConnection().sendCommand(command);
                }).bounds(0, 0, 56, 20)
                .build();
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of(deleteWaypointButton, hideWaypointButton, editWaypointButton, teleportWaypointButton);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(deleteWaypointButton, hideWaypointButton, editWaypointButton, teleportWaypointButton);
    }

    @Override
    public void renderContent(GuiGraphics context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int x = getContentX();
        int y = getContentY();
        int entryHeight = getContentHeight();
        int entryWidth = getContentWidth();
        var textColor = waypoint.isHidden() ? WaypointListWidget.gray.getRGB() : -1;
        var isSameDimension = waypoint.getDimensionID().equals(WaypointManager.getDimensionID());
        if (!isSameDimension) {
            RendererHelper.drawCenteredTexture(context, x - 10, y + (entryHeight / 2) + 6, 0, 0, 16, 16, 16, 16, WaypointListWidget.otherDimensionIdentifier);
        }
        RendererHelper.drawScaledWaypointIcon(context, x + 4, y + (entryHeight / 2) + 6, waypoint.getIconID(), 2);
        context.drawCenteredString(client.font, waypoint.getName(), x + client.font.width(waypoint.getName()) / 2 + 16 + 3, y + (entryHeight / 2) - client.font.lineHeight / 2, textColor);
        deleteWaypointButton.setX(x + entryWidth - 5 - deleteWaypointButton.getWidth());
        deleteWaypointButton.setY(y + entryHeight / 2 - deleteWaypointButton.getHeight() / 2);
        deleteWaypointButton.render(context, mouseX, mouseY, tickDelta);
        hideWaypointButton.setX(x + entryWidth - 10 - hideWaypointButton.getWidth() - deleteWaypointButton.getWidth());
        hideWaypointButton.setY(y + entryHeight / 2 - hideWaypointButton.getHeight() / 2);
        hideWaypointButton.render(context, mouseX, mouseY, tickDelta);
        editWaypointButton.setX(x + entryWidth - 15 - hideWaypointButton.getWidth() - deleteWaypointButton.getWidth() - editWaypointButton.getWidth());
        editWaypointButton.setY(y + entryHeight / 2 - editWaypointButton.getHeight() / 2);
        editWaypointButton.render(context, mouseX, mouseY, tickDelta);
        teleportWaypointButton.setX(x + entryWidth - 25 - hideWaypointButton.getWidth() - deleteWaypointButton.getWidth() - editWaypointButton.getWidth() - teleportWaypointButton.getWidth());
        teleportWaypointButton.setY(y + entryHeight / 2 - editWaypointButton.getHeight() / 2);
        teleportWaypointButton.render(context, mouseX, mouseY, tickDelta);
        Identifier renderTexture = waypoint.isHidden() ? WaypointListWidget.hiddenTextureIdentifier : WaypointListWidget.visibleTextureIdentifier;
        context.blit(RenderPipelines.GUI_TEXTURED, renderTexture, hideWaypointButton.getX() + 1, hideWaypointButton.getY() + 1, 0, 0, 18, 18, 18, 18);

    }
}
