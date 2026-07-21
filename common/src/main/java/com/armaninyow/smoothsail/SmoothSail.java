package com.armaninyow.smoothsail;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmoothSail implements ModInitializer {
	public static final String MOD_ID = "smoothsail";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SteeringInputHandler steeringHandler = new SteeringInputHandler();

	public static float lastNetSteering = 0f;

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.clientboundPlay().register(
			SmoothSailHandshakePayload.TYPE,
			SmoothSailHandshakePayload.CODEC
		);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			sender.sendPacket(new SmoothSailHandshakePayload());
			LOGGER.info("SmoothSail: sent server opt-in handshake to {}", handler.player.getName().getString());
		});

		LOGGER.info("SmoothSail loaded — smooth boat steering active.");
	}
}