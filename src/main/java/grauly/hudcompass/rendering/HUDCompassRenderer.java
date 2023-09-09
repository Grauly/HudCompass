package grauly.hudcompass.rendering;

import grauly.hudcompass.HudCompass;
import grauly.hudcompass.HudCompassClient;
import grauly.hudcompass.mixin.PlayerListHudAccessor;
import grauly.hudcompass.util.MathHelper;
import grauly.hudcompass.util.RendererHelper;
import grauly.hudcompass.waypoints.Waypoint;
import grauly.hudcompass.waypoints.WaypointManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Collection;

public class HUDCompassRenderer {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Color gray = new Color(0.9f, 0.9f, 0.9f, 0.7f);
    private static final Identifier background = new Identifier(HudCompass.MODID, "textures/ui/compass_background.png");

    @Environment(EnvType.CLIENT)
    public static void onRender(DrawContext context, float tickDelta) {
        if (((PlayerListHudAccessor) mc.inGameHud.getPlayerListHud()).isVisible()) {
            return;
        }
        var width = mc.getWindow().getScaledWidth();
        var textRenderer = mc.textRenderer;
        var player = mc.player;
        var angle = MathHelper.determineCompassAngle(player);

        /*drawBoundingLine(2, 2, matrices, width);
        drawBoundingLine(13, 2, matrices, width);*/

        //RenderSystem.setShaderTexture(0,background);
        context.drawTexture(background, width / 2 - 185, 1, 0, 0, 370, 15, 370, 15);

        //DrawableHelper.drawCenteredText(matrices, textRenderer, String.valueOf(angle), width / 2, 22, -1);
        //DrawableHelper.drawCenteredText(matrices, textRenderer, "^", width / 2, 17, -1);
        //DrawableHelper.drawCenteredText(matrices, textRenderer, "|", width / 2, 5, -1);
        context.drawCenteredTextWithShadow(textRenderer, "N", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 180)), 5, -1);
        context.drawCenteredTextWithShadow(textRenderer, "E", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 90)), 5, -1);
        context.drawCenteredTextWithShadow(textRenderer, "S", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 0)), 5, -1);
        context.drawCenteredTextWithShadow(textRenderer, "W", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 270)), 5, -1);
        drawWaypoints(context, textRenderer, width, angle, HudCompassClient.waypointManager.getWaypoints(), WaypointManager.getDimensionID());
    }

    private static void drawWaypoints(DrawContext context, TextRenderer textRenderer, int width, int playerAngle, Collection<Waypoint> waypoints, String currentDimension) {
        var playerPos = mc.player.getPos();
        waypoints.forEach(w -> {
            if (!(w.isHidden()) && w.getDimensionID().equals(currentDimension)) {
                var angle = (int) MathHelper.determineWaypointAngleRelative(playerPos, w);
                var pos = MathHelper.determineXPosOnCompass(playerAngle, angle);
                RendererHelper.drawWaypointIcon(context, (width / 2) + pos, 12, w.getIconID());
                context.getMatrices().push();
                context.getMatrices().scale(0.5f, 0.5f, 0);
                context.drawCenteredTextWithShadow(textRenderer, w.getName(), ((width / 2) + pos) * 2, (13 - mc.textRenderer.fontHeight / 2) * 2, -1);
                context.getMatrices().pop();
            }
        });
    }

    /*private static void drawBoundingLine(int height, int lineThickness, MatrixStack matrices, int screenWidth) {
        DrawContext.fill(matrices, screenWidth / 2 - 185, height + lineThickness, screenWidth / 2 + 185, height, gray.getRGB());
    }*/
}
