package com.armaninyow.smoothsail;

import net.fabricmc.api.ClientModInitializer;

public class SmoothSailClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Rendering is handled by GuiMixin injecting after renderHotbarAndDecorations
		// so that the bar appears above the XP bar in survival mode.
		SmoothSail.LOGGER.info("SmoothSail client initialised.");
	}
}