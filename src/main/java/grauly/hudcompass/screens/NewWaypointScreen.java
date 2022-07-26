package grauly.hudcompass.screens;

import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.waypoints.Waypoint;
import grauly.hudcompass.waypoints.WaypointManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.awt.geom.Arc2D;

public class NewWaypointScreen extends Screen {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Screen parent;
    private TextFieldWidget xCoord;
    private TextFieldWidget yCoord;
    private TextFieldWidget zCoord;
    private TextFieldWidget waypointName;

    public NewWaypointScreen(Screen parent) {
        super(Text.translatable("screen.hudcompass.newwaypoint"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        var width = mc.getWindow().getScaledWidth();
        var height = mc.getWindow().getScaledHeight();
        xCoord = new TextFieldWidget(mc.textRenderer, width / 2 - 55 - 70 - 5, height / 2 - 9, 70, 18, Text.translatable("screen.hudcompass.waypoint.x"));
        yCoord = new TextFieldWidget(mc.textRenderer, width / 2 - 35, height / 2 - 9, 70, 18, Text.translatable("screen.hudcompass.waypoint.y"));
        zCoord = new TextFieldWidget(mc.textRenderer, width / 2 + 55 + 5, height / 2 - 9, 70, 18, Text.translatable("screen.hudcompass.waypoint.z"));
        waypointName = new TextFieldWidget(mc.textRenderer, width / 2 - 80, height / 2 - 48, 160, 18, Text.translatable("screen.hudcompass.newwaypoint.name"));
        var pos = mc.player.getBlockPos();
        xCoord.setText(String.valueOf(pos.getX()));
        yCoord.setText(String.valueOf(pos.getY()));
        zCoord.setText(String.valueOf(pos.getZ()));
        waypointName.setText(pos.toShortString());
        this.addDrawableChild(xCoord);
        this.addDrawableChild(yCoord);
        this.addDrawableChild(zCoord);
        this.addDrawableChild(waypointName);
        this.addDrawableChild(new ButtonWidget(width / 2 - 98 - 2, height / 2 + 30, 98, 20, ScreenTexts.CANCEL, p -> {
            mc.setScreen(parent);
        }));
        this.addDrawableChild(new ButtonWidget(width / 2 + 2, height / 2 + 30, 98, 20, ScreenTexts.PROCEED, p -> {
            try {
                HudCompassClient.waypointManager.addWaypoint(
                        new Waypoint(
                                new Vec3d(
                                        Double.parseDouble(xCoord.getText()),
                                        Double.parseDouble(yCoord.getText()),
                                        Double.parseDouble(zCoord.getText())),
                                WaypointManager.getDimensionID(),
                                waypointName.getText()));
            } catch (NumberFormatException e) {
                //not needed as this is just a cancel
            }
            mc.setScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        var width = mc.getWindow().getScaledWidth();
        var height = mc.getWindow().getScaledHeight();
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, mc.textRenderer, "x:", width / 2 - 55 - 70 - 5 - 10, height / 2 - textRenderer.getWrappedLinesHeight(Text.translatable("screen.hudcompass.waypoint.x").getString() + ":", 100) / 2, -1);
        DrawableHelper.drawCenteredText(matrices, mc.textRenderer, "y:", width / 2 - 35 - 10, height / 2 - textRenderer.getWrappedLinesHeight(Text.translatable("screen.hudcompass.waypoint.y").getString() + ":", 100) / 2, -1);
        DrawableHelper.drawCenteredText(matrices, mc.textRenderer, "z:", width / 2 + 55 + 5 - 10, height / 2 - textRenderer.getWrappedLinesHeight(Text.translatable("screen.hudcompass.waypoint.z").getString() + ":", 100) / 2, -1);
        DrawableHelper.drawCenteredText(matrices, mc.textRenderer, Text.translatable("screen.hudcompass.newwaypoint"), width / 2, height / 2 - 63, -1);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        doNumberInputCheckFor(xCoord);
        doNumberInputCheckFor(yCoord);
        doNumberInputCheckFor(zCoord);
        super.tick();
    }

    private void doNumberInputCheckFor(TextFieldWidget widget) {
        try {
            Float.parseFloat(widget.getText());
            widget.setEditableColor(Color.white.getRGB());
        } catch (NumberFormatException e) {
            widget.setEditableColor(Color.red.getRGB());
        }
    }
}
