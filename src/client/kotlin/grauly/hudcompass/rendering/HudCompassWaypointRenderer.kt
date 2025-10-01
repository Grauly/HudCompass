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
        TODO("Not yet implemented")
    }

    companion object {
        fun renderCenteredDistance(
            drawContext: DrawContext,
            iconRenderState: WaypointRenderState,
            client: MinecraftClient,
            distance: Double,
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

        fun renderCenteredIcon(
            drawContext: DrawContext,
            iconRenderState: WaypointRenderState,
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

        fun renderCenteredName(
            drawContext: DrawContext,
            iconRenderState: WaypointRenderState,
            client: MinecraftClient,
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
}