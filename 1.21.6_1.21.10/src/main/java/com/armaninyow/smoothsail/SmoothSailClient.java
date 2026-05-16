package com.armaninyow.smoothsail;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

// 1.21.6_1.21.10
public class SmoothSailClient implements ClientModInitializer {

	/** True when the current server has SmoothSail installed. */
	public static boolean serverHasMod = false;

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new SteeringHudRenderer());

		// Receive handshake from server — enable smooth steering.
		ClientPlayNetworking.registerGlobalReceiver(SmoothSailHandshakePayload.TYPE, (payload, context) -> {
			serverHasMod = true;
			SmoothSail.LOGGER.info("SmoothSail: server opt-in confirmed — smooth steering enabled.");
		});

		// Reset flag on disconnect so it doesn't carry over to the next server.
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			serverHasMod = false;
		});

		SmoothSail.LOGGER.info("SmoothSail client initialised — HUD renderer registered.");
	}
}