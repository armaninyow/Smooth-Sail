package com.armaninyow.smoothsail.mixin;

import com.armaninyow.smoothsail.SmoothSail;
import com.armaninyow.smoothsail.SteeringHudRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

	@Inject(at = @At("TAIL"), method = "renderHotbarAndDecorations")
	private void smoothsail$renderAfterXpBar(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		SmoothSail.hudRenderer.onHudRender(graphics, deltaTracker);
	}
}