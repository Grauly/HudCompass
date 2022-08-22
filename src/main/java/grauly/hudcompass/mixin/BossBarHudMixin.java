package grauly.hudcompass.mixin;

import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void renderMoveDown(MatrixStack matrices, CallbackInfo ci) {
        matrices.translate(0,20,0);
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void renderResetMove(MatrixStack matrices, CallbackInfo ci) {
        matrices.translate(0,-20,0);
    }
}
