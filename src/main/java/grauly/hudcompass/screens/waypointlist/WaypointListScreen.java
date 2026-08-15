package grauly.hudcompass.screens.waypointlist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class WaypointListScreen extends Screen {

    public static final int BOTTOM_TEXT_HEIGHT = 53;
    private static final Minecraft mc = Minecraft.getInstance();
    private final Screen parent;
    private WaypointListWidget waypointList;

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
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        waypointList.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.centeredText(mc.font, Component.translatable("screen.hudcompass.waypointlist"), this.width / 2, 6, -1);
    }
}
