package com.armaninyow.smoothsail;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SmoothSailClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new SteeringHudRenderer());
		SmoothSail.LOGGER.info("SmoothSail client initialised — HUD renderer registered.");
	}
}