package grauly.hudcompass.rendering.parts

import grauly.hudcompass.rendering.WaypointIconRenderer
import grauly.hudcompass.rendering.WaypointRenderState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

object NameOnlyWaypointIconRenderer: WaypointIconRenderer {
    override fun render(
        drawContext: DrawContext,
        iconRenderState: WaypointRenderState,
        client: MinecraftClient,
        distance: Double,
        angle: Double,
        angleOffset: Double,
        centerX: Int,
        centerY: Int
    ) {
        val name = iconRenderState.getData(WaypointRenderState.Companion.NAME)
        if (name == null) return
        val color = iconRenderState.getData(WaypointRenderState.Companion.NAME_COLOR) ?: -1
        drawContext.drawCenteredTextWithShadow(
            client.textRenderer,
            Text.literal(name),
            centerX,
            centerY + client.textRenderer.fontHeight / 2,
            color
        )
    }
}