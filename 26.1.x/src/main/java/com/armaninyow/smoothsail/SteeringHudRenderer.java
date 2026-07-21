package com.armaninyow.smoothsail;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class SteeringHudRenderer implements HudElement {

	private static final Identifier BAR_BG =
		Identifier.withDefaultNamespace("hud/jump_bar_background");
	private static final Identifier BAR_PROGRESS =
		Identifier.withDefaultNamespace("hud/jump_bar_progress");

	private static final int BAR_W = 182;
	private static final int BAR_H = 5;
	private static final int TEX_W = 182;
	private static final int TEX_H = 5;

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
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
			int fillW = Math.max(1, Math.round(leftProgress * BAR_W));
			blitMirrored(graphics, pipeline, BAR_BG, barX, barY, 0, BAR_W);
			blitMirrored(graphics, pipeline, BAR_PROGRESS, barX, barY, 0, fillW);

		} else if (rightProgress > 0f) {
			graphics.blitSprite(pipeline, BAR_BG, barX, barY, BAR_W, BAR_H);

			int fillW = Math.max(1, Math.round(rightProgress * BAR_W));
			graphics.blitSprite(pipeline, BAR_PROGRESS, TEX_W, TEX_H, 0, 0, barX, barY, fillW, BAR_H);
		}
	}

	private void blitMirrored(GuiGraphicsExtractor graphics, RenderPipeline pipeline, Identifier sprite,
	                           int barX, int barY, int srcXStart, int srcXEnd) {
		for (int col = srcXStart; col < srcXEnd; col++) {
			int destX = barX + (BAR_W - 1 - col);
			graphics.blitSprite(pipeline, sprite, TEX_W, TEX_H, col, 0, destX, barY, 1, BAR_H);
		}
	}
}