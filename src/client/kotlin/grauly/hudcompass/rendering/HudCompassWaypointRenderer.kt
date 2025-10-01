package grauly.hudcompass.rendering

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class HudCompassWaypointRenderer: WaypointRenderer {
    override fun render(
        drawContext: DrawContext,
        renderState: WaypointRenderState,
        client: MinecraftClient,
        distance: Double,
        angle: Double,
        angleOffset: Double,
        centerX: Int,
        centerY: Int
    ) {
        renderCenteredIcon(drawContext, renderState, distance, angle, angleOffset, centerX, centerY)
        
    }

    companion object {
        fun renderCenteredDistance(
            drawContext: DrawContext,
            renderState: WaypointRenderState,
            client: MinecraftClient,
            distance: Double,
            centerX: Int,
            centerY: Int
        ) {
            val color = renderState.getData(WaypointRenderState.DISTANCE_COLOR) ?: -1
            drawContext.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal(distance.toInt().toString()),
                centerX,
                centerY + client.textRenderer.fontHeight / 2,
                color
            )
        }

        fun renderCenteredIcon(
            drawContext: DrawContext,
            renderState: WaypointRenderState,
            distance: Double,
            angle: Double,
            angleOffset: Double,
            centerX: Int,
            centerY: Int
        ) {
            drawContext.drawGuiTexture(
                RenderPipelines.GUI_TEXTURED,
                renderState.iconProvider.getIcon(distance, angle, angleOffset),
                centerX - 4, centerY + 4,
                8, 8
            )
        }

        fun renderCenteredName(
            drawContext: DrawContext,
            renderState: WaypointRenderState,
            client: MinecraftClient,
            centerX: Int,
            centerY: Int
        ) {
            val name = renderState.getData(WaypointRenderState.Companion.NAME)
            if (name == null) return
            val color = renderState.getData(WaypointRenderState.Companion.NAME_COLOR) ?: -1
            drawContext.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal(name),
                centerX,
                centerY + client.textRenderer.fontHeight / 2,
                color
            )
        }
    }
}