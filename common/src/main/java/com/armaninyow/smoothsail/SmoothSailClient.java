package com.armaninyow.smoothsail;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class SmoothSailClient implements ClientModInitializer {

	public static boolean serverHasMod = false;

	@Override
	public void onInitializeClient() {
		HudElementRegistry.attachElementBefore(
			VanillaHudElements.CHAT,
			Identifier.fromNamespaceAndPath(SmoothSail.MOD_ID, "steering_hud"),
			new SteeringHudRenderer()
		);

		ClientPlayNetworking.registerGlobalReceiver(SmoothSailHandshakePayload.TYPE, (payload, context) -> {
			serverHasMod = true;
			SmoothSail.LOGGER.info("SmoothSail: server opt-in confirmed — smooth steering enabled.");
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			serverHasMod = false;
		});

		SmoothSail.LOGGER.info("SmoothSail client initialised — HUD renderer registered.");
	}
}