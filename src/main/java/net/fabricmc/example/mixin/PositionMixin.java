package net.fabricmc.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class PositionMixin {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshair(MatrixStack matrices, CallbackInfo info) {
        MinecraftClient c = MinecraftClient.getInstance();

        if(c.player != null) {
            c.inGameHud.getTextRenderer().drawWithShadow(matrices, "X: " + String.format("%.02f", c.player.getX()), 0, 0, 0xff0000);
            c.inGameHud.getTextRenderer().drawWithShadow(matrices, "Y: " + String.format("%.02f", c.player.getY()), 0, 10, 0xff0000);
            c.inGameHud.getTextRenderer().drawWithShadow(matrices, "Z: " + String.format("%.02f", c.player.getZ()), 0, 20, 0xff0000);
        }
    }
}