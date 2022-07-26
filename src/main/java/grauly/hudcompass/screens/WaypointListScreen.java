package grauly.hudcompass.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class WaypointListScreen extends Screen {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Screen parent;

    public WaypointListScreen(Screen parent) {
        super(Text.translatable("screen.hudcompass.waypointlist"));
        this.parent = parent;
    }


    @Override
    protected void init() {
        var list = new WaypointListWidget(this, mc);
        var width = mc.getWindow().getScaledWidth();
        var height = mc.getWindow().getScaledWidth();
        this.addSelectableChild(list);
        this.addDrawableChild(new ButtonWidget(width/2 + 150,height/2 + 50,50,20, ScreenTexts.DONE,c -> {
            mc.setScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
