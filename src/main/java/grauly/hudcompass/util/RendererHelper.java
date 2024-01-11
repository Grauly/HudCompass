package grauly.hudcompass.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class RendererHelper {
    private static final Identifier MAP_ICONS = new Identifier("minecraft", "textures/map/map_icons.png");

    public static void drawWaypointIcon(DrawContext context, int centerX, int floorY, int id) {
        var yOffset = 0;
        while (id >= 16) {
            id -= 16;
            yOffset += 8;
        }
        context.drawTexture(MAP_ICONS, centerX - 4, floorY - 8, 0, id * 8, yOffset, 8, 8, 128, 128);
    }

    public static void drawScaledWaypointIcon(DrawContext context, int centerX, int floorY, int id, int scale) {
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1);
        var offsetX = (centerX / (float) scale);
        offsetX = offsetX - (int) offsetX;
        var offsetY = (floorY / (float) scale);
        offsetY = offsetY - (int) offsetY;
        context.getMatrices().translate(-offsetX, -offsetY, 0);
        drawWaypointIcon(context, Math.round(centerX / (float) scale), Math.round(floorY / (float) scale), id);
        context.getMatrices().pop();
    }

    public static void drawCenteredTexture(DrawContext context, int centerX, int floorY, int u, int v, int width, int height, int imageWidth, int imageHeight, Identifier texture) {
        context.drawTexture(texture, centerX - width / 2, floorY - height, 0, u, v, width, height, imageWidth, imageHeight);
    }
}
