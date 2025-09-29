package grauly.hudcompass.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void renderMoveDown(DrawContext context, CallbackInfo ci) {
        context.getMatrices().translate(0, 20);
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void renderResetMove(DrawContext context, CallbackInfo ci) {
        context.getMatrices().translate(0, -20);
    }

}
