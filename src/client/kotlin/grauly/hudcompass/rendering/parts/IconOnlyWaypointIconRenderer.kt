package grauly.hudcompass.rendering.parts

import grauly.hudcompass.rendering.WaypointIconRenderer
import grauly.hudcompass.rendering.WaypointRenderState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext

object IconOnlyWaypointIconRenderer : WaypointIconRenderer {
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
        drawContext.drawGuiTexture(
            RenderPipelines.GUI_TEXTURED,
            iconRenderState.iconProvider.getIcon(distance, angle, angleOffset),
            centerX - 4, centerY + 4,
            8, 8
        )
    }
}