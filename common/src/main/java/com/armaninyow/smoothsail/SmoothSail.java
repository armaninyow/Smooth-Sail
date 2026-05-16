package com.armaninyow.smoothsail;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmoothSail implements ModInitializer {
	public static final String MOD_ID = "smoothsail";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/** Shared ramp state — read by the mixin, written by the HUD renderer. */
	public static final SteeringInputHandler steeringHandler = new SteeringInputHandler();

	/** Shared HUD renderer instance — used by GuiMixin in branches that need post-XP-bar rendering. */
	public static final SteeringHudRenderer hudRenderer = new SteeringHudRenderer();

	/** Last scaled net steering value; used only for debug/future purposes. */
	public static float lastNetSteering = 0f;

	@Override
	public void onInitialize() {
		// Register payload type on both sides.
		PayloadTypeRegistry.playS2C().register(
			SmoothSailHandshakePayload.TYPE,
			SmoothSailHandshakePayload.CODEC
		);

		// Send handshake to client on join — works in both singleplayer and multiplayer.
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			sender.sendPacket(new SmoothSailHandshakePayload());
			LOGGER.info("SmoothSail: sent server opt-in handshake to {}", handler.player.getName().getString());
		});

		LOGGER.info("SmoothSail loaded — smooth boat steering active.");
	}
}