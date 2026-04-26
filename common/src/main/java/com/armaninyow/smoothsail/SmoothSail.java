package com.armaninyow.smoothsail;

import net.fabricmc.api.ModInitializer;

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
		LOGGER.info("SmoothSail loaded — smooth boat steering active.");
	}
}