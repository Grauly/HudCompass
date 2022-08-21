package grauly.hudcompass.util;

import F;
import I;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RendererHelper {
    private static final Identifier MAP_ICONS = new Identifier("minecraft", "textures/map/map_icons.png");

    public static void drawWaypointIcon(MatrixStack matrices, int centerX, int floorY, int id) {
        RenderSystem.setShaderTexture(0, MAP_ICONS);
        var yOffset = 0;
        while (id >= 16) {
            id -= 16;
            yOffset += 8;
        }
        DrawableHelper.drawTexture(matrices, centerX - 4, floorY - 8, 0, id * 8, yOffset, 8, 8, 128, 128);
    }

    public static void drawScaledWaypointIcon(MatrixStack matrices, int centerX, int floorY, int id, int scale) {
        matrices.push();
        matrices.scale(scale, scale, 1);
        var offsetX = (centerX / (float) scale);
        offsetX = offsetX - (int) offsetX;
        var offsetY = (floorY / (float) scale);
        offsetY = offsetY - (int) offsetY;
        matrices.translate(-offsetX,-offsetY,0);
        drawWaypointIcon(matrices, Math.round(centerX / (float) scale), Math.round(floorY / (float) scale), id);
        matrices.pop();
    }

    public static void drawCenteredTexture(MatrixStack matrices, int centerX, int floorY, int u, int v, int width, int height, int imageWidth, int imageHeight, Identifier texture) {
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, centerX - width / 2, floorY - height, 0, u, v, width, height, imageWidth, imageHeight);
    }
}
