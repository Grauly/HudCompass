package grauly.hudcompass.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class WaypointListScreen extends Screen {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Screen parent;
    private WaypointListWidget waypointList;

    public static final int BOTTOM_TEXT_HEIGHT = 53;

    public WaypointListScreen(Screen parent) {
        super(Text.translatable("screen.hudcompass.waypointlist"));
        this.parent = parent;
    }


    @Override
    protected void init() {
        waypointList = new WaypointListWidget(this, mc, this.width, this.height - BOTTOM_TEXT_HEIGHT);
        this.addSelectableChild(waypointList);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button -> {
                            mc.setScreen(parent);
                        }))
                        //.dimensions(width / 2 + 150, height / 2 - 10, 50, 20)
                        .width(50)
                        .position(this.width - 55, this.height - 26)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        waypointList.render(context,mouseX,mouseY,delta);
        context.drawCenteredTextWithShadow(mc.textRenderer, Text.translatable("screen.hudcompass.waypointlist"), this.width / 2, 6, -1);
    }
}
