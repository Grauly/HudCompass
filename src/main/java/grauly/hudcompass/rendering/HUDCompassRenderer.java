package grauly.hudcompass.rendering;

import grauly.hudcompass.HudCompass;
import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.mixin.PlayerTabOverlayAccessor;
import grauly.hudcompass.util.MathHelper;
import grauly.hudcompass.util.RendererHelper;
import grauly.hudcompass.waypoints.Waypoint;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.Identifier;

import java.util.Collection;

public class HUDCompassRenderer {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final Identifier background = Identifier.fromNamespaceAndPath(HudCompass.MODID, "textures/ui/compass_background.png");

    @Environment(EnvType.CLIENT)
    public static void onRender(GuiGraphics context, DeltaTracker renderTickCounter) {
        if (mc.options.hideGui) return;
        if (((PlayerTabOverlayAccessor) mc.gui.getTabList()).isVisible()) return;
        var width = mc.getWindow().getGuiScaledWidth();
        var textRenderer = mc.font;
        var player = mc.player;
        var angle = MathHelper.determineCompassAngle(player);

        context.blit(RenderPipelines.GUI_TEXTURED, background, width / 2 - 185, 1, 0, 0, 370, 15, 370, 15);

        context.drawCenteredString(textRenderer, "N", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 180)), 5, -1);
        context.drawCenteredString(textRenderer, "E", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 90)), 5, -1);
        context.drawCenteredString(textRenderer, "S", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 0)), 5, -1);
        context.drawCenteredString(textRenderer, "W", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 270)), 5, -1);
        drawWaypoints(context, textRenderer, width, angle, HudCompassClient.waypointManager.getWaypoints(), WaypointManager.getDimensionID());
    }

    private static void drawWaypoints(GuiGraphics context, Font textRenderer, int width, int playerAngle, Collection<Waypoint> waypoints, String currentDimension) {
        var playerPos = mc.player.position();
        waypoints.forEach(w -> {
            if (!(w.isHidden()) && w.getDimensionID().equals(currentDimension)) {
                var angle = (int) MathHelper.determineWaypointAngleRelative(playerPos, w);
                var pos = MathHelper.determineXPosOnCompass(playerAngle, angle);
                RendererHelper.drawWaypointIcon(context, (width / 2) + pos, 12, w.getIconID());
                context.pose().pushMatrix();
                context.pose().scale(0.5f, 0.5f);
                context.drawCenteredString(textRenderer, w.getName(), ((width / 2) + pos) * 2, (13 - mc.font.lineHeight / 2) * 2, -1);
                context.pose().popMatrix();
            }
        });
    }
}
