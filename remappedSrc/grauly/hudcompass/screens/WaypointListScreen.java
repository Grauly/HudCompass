package grauly.hudcompass.screens;

import I;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class WaypointListScreen extends Screen {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Screen parent;
    private WaypointListWidget waypointList;

    public WaypointListScreen(Screen parent) {
        super(Text.translatable("screen.hudcompass.waypointlist"));
        this.parent = parent;
    }


    @Override
    protected void init() {
        var width = mc.getWindow().getScaledWidth();
        var height = mc.getWindow().getScaledWidth();
        this.addDrawableChild(new ButtonWidget(width/2 + 150,height/2 - 10,50,20, ScreenTexts.DONE,c -> {
            mc.setScreen(parent);
        }));
        waypointList = new WaypointListWidget(this, mc);
        this.addSelectableChild(waypointList);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        var width = mc.getWindow().getScaledWidth();
        var height = mc.getWindow().getScaledWidth();
        this.waypointList.render(matrices,mouseX,mouseY,delta);
        DrawableHelper.drawCenteredText(matrices,mc.textRenderer,Text.translatable("screen.hudcompass.waypointlist"),width/2, 6, -1);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
