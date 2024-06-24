package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxScreen.class)
public class HacksMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void renderScreen(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(ExampleMod.autoInvEnabled) {
            MinecraftClient c = MinecraftClient.getInstance();
            ShulkerBoxScreen gui = (ShulkerBoxScreen) c.currentScreen;

            ExampleMod.LOGGER.info("" + gui.getScreenHandler().getSlot(0).id + " " + gui.getScreenHandler().syncId);
            ExampleMod.LOGGER.info("" + c.player.playerScreenHandler.slots.get(9).getStack());

            var chestSlot = (Slot)c.player.playerScreenHandler.slots.get(9);
            var playerItems = c.player.getInventory().getStack(0);
            var chestItems = chestSlot.getStack();

            ExampleMod.LOGGER.info("" + chestSlot.inventory + " " + playerItems + " " + chestItems);
            c.player.getInventory().setStack(9, chestItems);

            chestSlot.setStack(playerItems);
            chestSlot.onTakeItem(c.player, chestItems);
        }
    }
}
