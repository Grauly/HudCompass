package grauly.hudcompass.rendering.parts

import grauly.hudcompass.rendering.WaypointIconRenderer
import grauly.hudcompass.rendering.WaypointRenderState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

object DistanceOnlyWaypointIconRenderer: WaypointIconRenderer {
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
        val color = iconRenderState.getData(WaypointRenderState.DISTANCE_COLOR) ?: -1
        drawContext.drawCenteredTextWithShadow(
            client.textRenderer,
            Text.literal(distance.toInt().toString()),
            centerX,
            centerY + client.textRenderer.fontHeight / 2,
            color
        )
    }
}