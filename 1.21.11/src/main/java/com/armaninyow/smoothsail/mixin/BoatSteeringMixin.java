package com.armaninyow.smoothsail.mixin;

import com.armaninyow.smoothsail.SmoothSail;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public class BoatSteeringMixin {

	@Inject(at = @At("HEAD"), method = "tick", remap = false)
	private void smoothsail$tickHead(CallbackInfo ci) {
		AbstractBoat self = (AbstractBoat) (Object) this;
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.player.getVehicle() != self) return;

		boolean leftDown  = mc.options.keyLeft.isDown();
		boolean rightDown = mc.options.keyRight.isDown();
		SmoothSail.steeringHandler.tick(leftDown, rightDown, 1f / 20f);
	}

	@Inject(at = @At("HEAD"), method = "clampRotation", remap = false, cancellable = true)
	private void smoothsail$clampRotation(Entity passenger, CallbackInfo ci) {
		AbstractBoat self = (AbstractBoat) (Object) this;
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || passenger != mc.player) return;

		boolean leftDown  = mc.options.keyLeft.isDown();
		boolean rightDown = mc.options.keyRight.isDown();

		float leftPower  = SmoothSail.steeringHandler.getLeftPower();
		float rightPower = SmoothSail.steeringHandler.getRightPower();

		float power;
		if (leftDown && !rightDown)       power = leftPower;
		else if (rightDown && !leftDown)  power = rightPower;
		else if (leftDown)                power = Math.max(leftPower, rightPower);
		else                              power = 1f;

		if (power >= 1f) return;

		float boatYRot = self.getYRot();

		passenger.setYBodyRot(boatYRot);

		float wrapped = net.minecraft.util.Mth.wrapDegrees(passenger.getYRot() - boatYRot);
		float clamped = net.minecraft.util.Mth.clamp(wrapped, -105.0F, 105.0F);
		float adjustment = clamped - wrapped;

		EntityAccessor passengerAccessor = (EntityAccessor) passenger;
		passengerAccessor.setYRotO(passengerAccessor.getYRotO() + adjustment * power);
		passenger.setYRot(passenger.getYRot() + adjustment * power);

		float wrappedHead = net.minecraft.util.Mth.wrapDegrees(passenger.getYHeadRot() - boatYRot);
		float clampedHead = net.minecraft.util.Mth.clamp(wrappedHead, -105.0F, 105.0F);
		passenger.setYHeadRot(passenger.getYHeadRot() + (clampedHead - wrappedHead) * power);

		ci.cancel();
	}

	@Inject(at = @At("TAIL"), method = "tick", remap = false)
	private void smoothsail$tickTail(CallbackInfo ci) {
		AbstractBoat self = (AbstractBoat) (Object) this;
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.player.getVehicle() != self) return;

		BoatAccessor boatAccessor   = (BoatAccessor) self;
		EntityAccessor boatAsEntity = (EntityAccessor) self;

		boolean leftDown  = mc.options.keyLeft.isDown();
		boolean rightDown = mc.options.keyRight.isDown();

		float leftPower  = SmoothSail.steeringHandler.getLeftPower();
		float rightPower = SmoothSail.steeringHandler.getRightPower();

		float rawDelta = boatAccessor.getDeltaRotation();

		float scaledDelta;
		if (leftDown && !rightDown)      scaledDelta = rawDelta * leftPower;
		else if (rightDown && !leftDown) scaledDelta = rawDelta * rightPower;
		else if (leftDown && rightDown)  scaledDelta = (-leftPower + rightPower) * Math.abs(rawDelta);
		else                             scaledDelta = rawDelta;

		float correction = scaledDelta - rawDelta;

		self.setYRot(self.getYRot() + correction);
		boatAsEntity.setYRotO(boatAsEntity.getYRotO() + correction);

		boatAccessor.setDeltaRotation(scaledDelta);
		SmoothSail.lastNetSteering = scaledDelta;
	}
}