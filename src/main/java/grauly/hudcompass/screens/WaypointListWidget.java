package grauly.hudcompass.screens;

import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.util.RendererHelper;
import grauly.hudcompass.waypoints.Waypoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.List;

@Environment(EnvType.CLIENT)
public class WaypointListWidget extends ElementListWidget<WaypointListWidget.Entry> {

    private static final Color gray = new Color(0.9f, 0.9f, 0.9f, 0.7f);
    private static final Identifier visibleTextureIdentifier = new Identifier("minecraft", "textures/mob_effect/night_vision.png");
    private static final Identifier hiddenTextureIdentifier = new Identifier("minecraft", "textures/mob_effect/blindness.png");
    private final MinecraftClient mc;
    private final Screen parent;

    public WaypointListWidget(Screen parent, MinecraftClient client, int width, int height) {
        //client, background width, background height, top margin pos, bottom margin pos, entry height
        //super(client, parent.width, parent.height, 20, parent.height - 35, 25);
        //client, background width, background height, top margin pos, entry height
        //super(client, parent.width, parent.height - WaypointListScreen.BOTTOM_TEXT_HEIGHT, 20, 35);
        super(client,width,height,20,35);
        this.mc = client;
        this.parent = parent;
        reloadList();
    }

    @Override
    public int getRowWidth() {
        //standard is 220
        return 320;
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 35;
    }

    public void reloadList() {
        this.clearEntries();
        for (Waypoint w : HudCompassClient.waypointManager.getWaypoints()) {
            this.addEntry(new WaypointEntry(w));
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
        //can be empty according to any of the vanilla classes
    }

    @Environment(EnvType.CLIENT)
    public class WaypointEntry extends Entry {

        Waypoint waypoint;
        boolean hidden;

        ButtonWidget deleteWaypointButton = ButtonWidget
                .builder(Text.translatable("screen.hudcompass.waypointlist.delete"), button -> {
                    HudCompassClient.waypointManager.removeWaypoint(waypoint);
                    reloadList();
                })
                .dimensions(0, 0, 48, 20)
                .build();
        ButtonWidget hideWaypointButton = ButtonWidget
                .builder(Text.empty(), button -> {
                    HudCompassClient.waypointManager.hideWaypoint(waypoint, !hidden);
                    reloadList();
                }).dimensions(0, 0, 20, 20)
                .build();
        ButtonWidget editWaypointButton = ButtonWidget
                .builder(Text.translatable("screen.hudcompass.waypointlist.edit"), button -> {
                    mc.setScreen(new ConfigureWaypointScreen(parent, waypoint));
                }).dimensions(0, 0, 35, 20)
                .build();

        public WaypointEntry(Waypoint waypoint) {
            this.waypoint = waypoint;
            this.hidden = waypoint.isHidden();
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of(deleteWaypointButton, hideWaypointButton, editWaypointButton);
        }

        @Override
        public List<? extends Element> children() {
            return List.of(deleteWaypointButton, hideWaypointButton, editWaypointButton);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            var textColor = waypoint.isHidden() ? gray.getRGB() : -1;
            RendererHelper.drawScaledWaypointIcon(context, x + 4, y + (entryHeight / 2) + 6, waypoint.getIconID(), 2);
            context.drawCenteredTextWithShadow(mc.textRenderer, waypoint.getName(), x + mc.textRenderer.getWidth(waypoint.getName()) / 2 + 16 + 3, y + (entryHeight / 2) - mc.textRenderer.fontHeight / 2, textColor);
            deleteWaypointButton.setX(x + entryWidth - 5 - deleteWaypointButton.getWidth());
            deleteWaypointButton.setY(y + entryHeight / 2 - deleteWaypointButton.getHeight() / 2);
            deleteWaypointButton.render(context, mouseX, mouseY, tickDelta);
            hideWaypointButton.setX(x + entryWidth - 10 - hideWaypointButton.getWidth() - deleteWaypointButton.getWidth());
            hideWaypointButton.setY(y + entryHeight / 2 - hideWaypointButton.getHeight() / 2);
            hideWaypointButton.render(context, mouseX, mouseY, tickDelta);
            editWaypointButton.setX(x + entryWidth - 15 - hideWaypointButton.getWidth() - deleteWaypointButton.getWidth() - editWaypointButton.getWidth());
            editWaypointButton.setY(y + entryHeight / 2 - editWaypointButton.getHeight() / 2);
            editWaypointButton.render(context, mouseX, mouseY, tickDelta);
            //RenderSystem.setShaderTexture(0, waypoint.isHidden() ? hiddenTextureIdentifier : visibleTextureIdentifier);
            context.drawTexture(waypoint.isHidden() ? hiddenTextureIdentifier : visibleTextureIdentifier, hideWaypointButton.getX() + 1, hideWaypointButton.getY() + 1, 0, 0, 0, 18, 18, 18, 18);
        }
    }
}
