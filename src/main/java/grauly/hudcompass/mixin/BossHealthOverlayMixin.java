package grauly.hudcompass.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {

    @Inject(at = @At("HEAD"), method = "extractRenderState")
    public void renderMoveDown(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        graphics.pose().translate(0, 20);
    }

    @Inject(at = @At("RETURN"), method = "extractRenderState")
    public void renderResetMove(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        graphics.pose().translate(0, -20);
    }

}
