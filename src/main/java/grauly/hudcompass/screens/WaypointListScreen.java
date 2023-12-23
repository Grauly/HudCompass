package grauly.hudcompass.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
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
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button -> {
                            mc.setScreen(parent);
                        }))
                        .dimensions(width / 2 + 150, height / 2 - 10, 50, 20)
                        .build()
        );
        waypointList = new WaypointListWidget(this, mc);
        this.addSelectableChild(waypointList);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        var width = mc.getWindow().getScaledWidth();
        super.render(context, mouseX, mouseY, delta);
        this.waypointList.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(mc.textRenderer, Text.translatable("screen.hudcompass.waypointlist"), width / 2, 6, -1);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackgroundTexture(context);
    }
}
