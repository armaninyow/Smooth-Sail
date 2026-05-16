package com.armaninyow.smoothsail;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractBoat;

// 1.21.6_1.21.10
public class SteeringHudRenderer implements HudRenderCallback {

	private static final ResourceLocation LEFT_BG =
		ResourceLocation.fromNamespaceAndPath("smoothsail", "textures/hud/left_steering_bar_background.png");
	private static final ResourceLocation LEFT_PROGRESS =
		ResourceLocation.fromNamespaceAndPath("smoothsail", "textures/hud/left_steering_bar_progress.png");
	private static final ResourceLocation RIGHT_BG =
		ResourceLocation.fromNamespaceAndPath("smoothsail", "textures/hud/right_steering_bar_background.png");
	private static final ResourceLocation RIGHT_PROGRESS =
		ResourceLocation.fromNamespaceAndPath("smoothsail", "textures/hud/right_steering_bar_progress.png");

	private static final int BAR_W = 182;
	private static final int BAR_H = 5;
	private static final int TEX_W = 182;
	private static final int TEX_H = 5;

	@Override
	public void onHudRender(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player == null || !(mc.player.getVehicle() instanceof AbstractBoat)) return;
		if (mc.screen != null) return;

		SteeringInputHandler handler = SmoothSail.steeringHandler;
		float leftProgress  = handler.getLeftProgress();
		float rightProgress = handler.getRightProgress();

		if (leftProgress <= 0f && rightProgress <= 0f) return;

		int screenW = mc.getWindow().getGuiScaledWidth();
		int screenH = mc.getWindow().getGuiScaledHeight();

		int barX = (screenW - BAR_W) / 2;
		int barY = screenH - 32 + 3;

		RenderPipeline pipeline = RenderPipelines.GUI_TEXTURED;

		if (leftProgress > 0f && rightProgress <= 0f) {
			graphics.blit(pipeline, LEFT_BG, barX, barY, 0, 0, BAR_W, BAR_H, TEX_W, TEX_H);

			int fillW   = Math.max(1, Math.round(leftProgress * BAR_W));
			int fillX   = barX + (BAR_W - fillW);
			int uOffset = BAR_W - fillW;
			graphics.blit(pipeline, LEFT_PROGRESS, fillX, barY, uOffset, 0, fillW, BAR_H, TEX_W, TEX_H);

		} else if (rightProgress > 0f) {
			graphics.blit(pipeline, RIGHT_BG, barX, barY, 0, 0, BAR_W, BAR_H, TEX_W, TEX_H);

			int fillW = Math.max(1, Math.round(rightProgress * BAR_W));
			graphics.blit(pipeline, RIGHT_PROGRESS, barX, barY, 0, 0, fillW, BAR_H, TEX_W, TEX_H);
		}
	}
}