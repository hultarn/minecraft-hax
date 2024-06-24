package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    String btnFish = "AutoFishing: ";
    String btnInventory = "AutoInv: ";
    String btnXray = "xRay: ";

    protected GameMenuScreenMixin(Text text) {
        super(text);
    }

    @Inject(at = @At("HEAD"), method="initWidgets")
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(20, 20, 64, 20, Text.literal(btnFish + " " + autoFishingText()), (button) -> {
            ExampleMod.autoFishingEnabled = !ExampleMod.autoFishingEnabled;
            ExampleMod.LOGGER.info("AutoFishing toggled to: " + ExampleMod.autoFishingEnabled);
            button.setMessage(Text.literal(btnFish + " " + autoFishingText()));
        }));

        this.addDrawableChild(new ButtonWidget(20, 20 * 2, 64, 20, Text.literal(btnInventory + " " + InventoryText()),(button) -> {
            ExampleMod.autoInvEnabled = !ExampleMod.autoInvEnabled;
            ExampleMod.LOGGER.info("AutoInv toggled to: " + ExampleMod.autoInvEnabled);
            button.setMessage(Text.literal(btnInventory + " " + InventoryText()));
        }));

        this.addDrawableChild(new ButtonWidget(20, 20 * 3, 64, 20, Text.literal(btnXray + " " + XrayText()),(button) -> {
            ExampleMod.xRayEnabled = !ExampleMod.xRayEnabled;
            ExampleMod.LOGGER.info("Xray toggled to: " + ExampleMod.xRayEnabled);
            button.setMessage(Text.literal(btnXray + " " + XrayText()));
        }));
    }

    private String autoFishingText() {
        if(ExampleMod.autoFishingEnabled)
            return "ON";
        else
            return "OFF";
    }

    private String InventoryText() {
        if(ExampleMod.autoInvEnabled)
            return "ON";
        else
            return "OFF";
    }

    private String XrayText() {
        if(ExampleMod.xRayEnabled)
            return "ON";
        else
            return "OFF";
    }
}
