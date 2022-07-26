package grauly.hudcompass.rendering;

import I;
import com.mojang.blaze3d.systems.RenderSystem;
import grauly.hudcompass.util.MathHelper;
import grauly.hudcompass.waypoints.Waypoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.Vec3d;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class HUDCompassRenderer {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Color gray = new Color(0.9f, 0.9f, 0.9f, 0.7f);
    private static final Waypoint[] points = {new Waypoint(0, 0, 0, "0,0,0")};
    private static final Identifier MAP_ICONS = new Identifier("minecraft","textures/map/map_icons.png");

    public static void onRender(MatrixStack matrices, float tickDelta) {
        var width = mc.getWindow().getScaledWidth();
        var textRenderer = mc.textRenderer;
        var player = mc.player;
        var angle = MathHelper.determineCompassAngle(player);

        drawBoundingLine(2,2,matrices,width);
        drawBoundingLine(13,2,matrices,width);

        DrawableHelper.drawCenteredText(matrices, textRenderer, String.valueOf(angle), width / 2, 22, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "^", width / 2, 17, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "|", width / 2, 5, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "N", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 180)), 5, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "E", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 90)), 5, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "S", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 0)), 5, -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, "W", (int) ((width / 2f) + MathHelper.determineXPosOnCompass(angle, 270)), 5, -1);
        drawWaypoints(matrices, textRenderer, width, angle, Arrays.stream(points).toList());
        DrawableHelper.drawCenteredText(matrices, textRenderer, mc.world.getRegistryKey().getValue().toString(), width / 2 , 45 , -1);
        DrawableHelper.drawCenteredText(matrices, textRenderer, getWorldID(), width / 2 , 55 , -1);
    }

    private static String getWorldID() {
        if(mc.getGame().getCurrentSession().isRemoteServer()) {
            return mc.getCurrentServerEntry().address;
        } else {
            return mc.getServer().getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
        }
    }

    private static void drawWaypoints(MatrixStack matrices, TextRenderer textRenderer, int width, int playerAngle, Collection<Waypoint> waypoints) {
        var playerPos = mc.player.getPos();
        waypoints.forEach(w -> {
            var angle = (int) MathHelper.determineWaypointAngleRelative(playerPos, w);
            var pos = MathHelper.determineXPosOnCompass(playerAngle, angle);
            drawCenteredMarker((width / 2) + pos,12,matrices);
            matrices.push();
            matrices.scale(0.5f,0.5f,0);
            DrawableHelper.drawCenteredText(matrices, textRenderer, w.getName(), ((width / 2) + pos)*2, (13-mc.textRenderer.fontHeight/2)*2, -1);
            matrices.pop();
        });
    }

    private static void drawBoundingLine(int height, int lineThickness, MatrixStack matrices, int screenWidth) {
        DrawableHelper.fill(matrices, screenWidth / 2 - 185, height + lineThickness, screenWidth / 2 + 185, height, gray.getRGB());
    }

    private static void drawCenteredMarker(int centerX, int floorY, MatrixStack matrices) {
        RenderSystem.setShaderTexture(0, MAP_ICONS);
        DrawableHelper.drawTexture(matrices,centerX-3,floorY-8,0,89,0,6,8,128,128);
    }
}
