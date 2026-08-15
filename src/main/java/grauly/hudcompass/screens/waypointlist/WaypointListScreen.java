package grauly.hudcompass.screens.waypointlist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class WaypointListScreen extends Screen {

    private static final Minecraft mc = Minecraft.getInstance();
    private final Screen parent;
    private WaypointListWidget waypointList;

    public static final int BOTTOM_TEXT_HEIGHT = 53;

    public WaypointListScreen(Screen parent) {
        super(Component.translatable("screen.hudcompass.waypointlist"));
        this.parent = parent;
    }


    @Override
    protected void init() {
        waypointList = new WaypointListWidget(this, mc, this.width, this.height - BOTTOM_TEXT_HEIGHT);
        this.addWidget(waypointList);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button -> {
                            mc.setScreen(parent);
                        }))
                        //.dimensions(width / 2 + 150, height / 2 - 10, 50, 20)
                        .width(50)
                        .pos(this.width - 55, this.height - 26)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        waypointList.render(context,mouseX,mouseY,delta);
        context.drawCenteredString(mc.font, Component.translatable("screen.hudcompass.waypointlist"), this.width / 2, 6, -1);
    }
}
