package grauly.hudcompass.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class RendererHelper {
    public static final ArrayList<Identifier> MAP_ICONS = new ArrayList<>();

    private static void addIcon(String id) {
        MAP_ICONS.add(Identifier.ofVanilla("textures/map/decorations/" + id + ".png"));
    }

    static {
        addIcon("player");
        addIcon("frame");
        addIcon("red_marker");
        addIcon("blue_marker");
        addIcon("target_x");
        addIcon("target_point");
        addIcon("player_off_map");
        addIcon("player_off_limits");
        addIcon("woodland_mansion");
        addIcon("ocean_monument");
        addIcon("white_banner");
        addIcon("orange_banner");
        addIcon("magenta_banner");
        addIcon("light_blue_banner");
        addIcon("yellow_banner");
        addIcon("lime_banner");
        addIcon("pink_banner");
        addIcon("gray_banner");
        addIcon("light_gray_banner");
        addIcon("cyan_banner");
        addIcon("purple_banner");
        addIcon("blue_banner");
        addIcon("brown_banner");
        addIcon("green_banner");
        addIcon("red_banner");
        addIcon("black_banner");
        addIcon("red_x");
        addIcon("desert_village");
        addIcon("plains_village");
        addIcon("savanna_village");
        addIcon("snowy_village");
        addIcon("taiga_village");
        addIcon("jungle_temple");
        addIcon("swamp_hut");
        addIcon("trial_chambers");
    }

    public static void drawWaypointIcon(DrawContext context, int centerX, int floorY, int id) {
        Identifier drawTarget = id > MAP_ICONS.size() ? Identifier.of("void") : MAP_ICONS.get(id);
        context.drawTexture(drawTarget, centerX - 4, floorY - 8, 0, 0, 0, 8, 8, 8, 8);
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
