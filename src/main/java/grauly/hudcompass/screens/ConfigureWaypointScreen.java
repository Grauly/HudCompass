package grauly.hudcompass.screens;

import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.util.RendererHelper;
import grauly.hudcompass.waypoints.Waypoint;
import grauly.hudcompass.waypoints.WaypointLocation;
import grauly.hudcompass.waypoints.WaypointManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.awt.*;

public class ConfigureWaypointScreen extends Screen {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final int MAX_ICON_ID = RendererHelper.MAP_ICONS.size() - 1;
    private static final Identifier recipeBookTexture = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/recipe_book.png");
    private static final WidgetSprites rightButtonTextures = new WidgetSprites(Identifier.parse("recipe_book/page_forward"), Identifier.parse("recipe_book/page_forward_highlighted"));
    private static final WidgetSprites leftButtonTextures = new WidgetSprites(Identifier.parse("recipe_book/page_backward"), Identifier.parse("recipe_book/page_backward_highlighted"));
    private final Screen parent;
    private final Waypoint editWaypoint;
    private EditBox xCoord;
    private EditBox yCoord;
    private EditBox zCoord;
    private EditBox waypointName;
    private ImageButton iconLeftButton;
    private ImageButton iconRightButton;
    private int iconID = 11;


    public ConfigureWaypointScreen(Screen parent) {
        super(Component.translatable("screen.hudcompass.newwaypoint"));
        this.parent = parent;
        this.editWaypoint = null;
    }

    public ConfigureWaypointScreen(Screen parent, Waypoint editPoint) {
        super(Component.translatable("screen.hudcompass.editwaypoint"));
        this.parent = parent;
        this.editWaypoint = editPoint;
    }

    @Override
    protected void init() {
        var width = mc.getWindow().getGuiScaledWidth();
        var height = mc.getWindow().getGuiScaledHeight();

        xCoord = new EditBox(mc.font, width / 2 - 55 - 70 - 5, height / 2 - 9, 70, 18, Component.translatable("screen.hudcompass.waypoint.x"));
        yCoord = new EditBox(mc.font, width / 2 - 35, height / 2 - 9, 70, 18, Component.translatable("screen.hudcompass.waypoint.y"));
        zCoord = new EditBox(mc.font, width / 2 + 55 + 5, height / 2 - 9, 70, 18, Component.translatable("screen.hudcompass.waypoint.z"));

        xCoord.setResponder(s -> doNumberInputCheckFor(xCoord));
        yCoord.setResponder(s -> doNumberInputCheckFor(yCoord));
        zCoord.setResponder(s -> doNumberInputCheckFor(zCoord));

        waypointName = new EditBox(mc.font, xCoord.getX(), height / 2 - 48, 160, 18, Component.translatable("screen.hudcompass.newwaypoint.name"));
        if (editWaypoint != null) {
            waypointName.setValue(editWaypoint.getName());
            xCoord.setValue(String.valueOf(editWaypoint.getWaypoint().x()));
            yCoord.setValue(String.valueOf(editWaypoint.getWaypoint().y()));
            zCoord.setValue(String.valueOf(editWaypoint.getWaypoint().z()));
            iconID = editWaypoint.getIconID();
        } else {
            var pos = mc.player.blockPosition();
            xCoord.setValue(String.valueOf(pos.getX()));
            yCoord.setValue(String.valueOf(pos.getY()));
            zCoord.setValue(String.valueOf(pos.getZ()));
            waypointName.setValue(pos.toShortString());
        }

        this.addRenderableWidget(xCoord);
        this.addRenderableWidget(yCoord);
        this.addRenderableWidget(zCoord);
        this.addRenderableWidget(waypointName);

        this.addRenderableWidget(Button
                .builder(CommonComponents.GUI_CANCEL, (button -> {
                    mc.setScreen(parent);
                }))
                .bounds(width / 2 - 98 - 2, height / 2 + 30, 98, 20)
                .build());
        this.addRenderableWidget(Button
                .builder(CommonComponents.GUI_PROCEED, (button) -> {
                    try {
                        Vec3 location = new Vec3(
                                Double.parseDouble(xCoord.getValue()),
                                Double.parseDouble(yCoord.getValue()),
                                Double.parseDouble(zCoord.getValue()));
                        if (editWaypoint != null) {
                            editWaypoint.setWaypoint(WaypointLocation.fromVec3d(location));
                            editWaypoint.setName(waypointName.getValue());
                            editWaypoint.setIconID(iconID);
                            HudCompassClient.waypointManager.editWaypoint(editWaypoint);
                        } else {
                            HudCompassClient.waypointManager.addWaypoint(
                                    new Waypoint(
                                            location,
                                            WaypointManager.getDimensionID(),
                                            waypointName.getValue(),
                                            iconID));
                        }
                    } catch (NumberFormatException e) {
                        //not needed as this is just a cancel
                    }
                    mc.setScreen(parent);
                })
                .bounds(width / 2 + 2, height / 2 + 30, 98, 20)
                .build());

        var centerX = zCoord.getX() + zCoord.getWidth() / 2;

        iconLeftButton = new ImageButton(centerX - 12 - 3 - 12, waypointName.getY(), 12, 17, leftButtonTextures, (widget) -> {
            iconID--;
            if (iconID < 0) {
                iconID = MAX_ICON_ID;
            }
        });
        iconRightButton = new ImageButton(centerX + 12 + 3, waypointName.getY(), 12, 17, rightButtonTextures, (widget) -> {
            iconID++;
            if (iconID > MAX_ICON_ID) {
                iconID = 0;
            }
        });

        this.addRenderableWidget(iconLeftButton);
        this.addRenderableWidget(iconRightButton);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        var width = mc.getWindow().getGuiScaledWidth();
        var height = mc.getWindow().getGuiScaledHeight();
        super.extractRenderState(context, mouseX, mouseY, delta);

        context.centeredText(mc.font, "x:", width / 2 - 55 - 70 - 5 - 10, height / 2 - font.wordWrapHeight(Component.translatable("screen.hudcompass.waypoint.x").append(":"), 100) / 2, -1);
        context.centeredText(mc.font, "y:", width / 2 - 35 - 10, height / 2 - font.wordWrapHeight(Component.translatable("screen.hudcompass.waypoint.y").append(":"), 100) / 2, -1);
        context.centeredText(mc.font, "z:", width / 2 + 55 + 5 - 10, height / 2 - font.wordWrapHeight(Component.translatable("screen.hudcompass.waypoint.z").append(":"), 100) / 2, -1);
        context.centeredText(mc.font, Component.translatable("screen.hudcompass.newwaypoint"), width / 2, height / 2 - 66, -1);

        var centerX = zCoord.getX() + zCoord.getWidth() / 2;
        var floorY = waypointName.getY() + waypointName.getHeight();
        RendererHelper.drawCenteredTexture(context, centerX, floorY + 2, 29, 206, 24, 24, 256, 256, recipeBookTexture);
        RendererHelper.drawScaledWaypointIcon(context, centerX, floorY - 2, iconID, 2);
    }

    private void doNumberInputCheckFor(EditBox widget) {
        try {
            Float.parseFloat(widget.getValue());
            widget.setTextColor(Color.white.getRGB());
        } catch (NumberFormatException e) {
            widget.setTextColor(Color.red.getRGB());
        }
    }
}
