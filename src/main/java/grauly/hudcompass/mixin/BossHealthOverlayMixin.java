package grauly.hudcompass.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void renderMoveDown(GuiGraphics context, CallbackInfo ci) {
        context.pose().translate(0, 20);
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void renderResetMove(GuiGraphics context, CallbackInfo ci) {
        context.pose().translate(0, -20);
    }

}
