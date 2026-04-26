package com.armaninyow.smoothsail;

public class SteeringInputHandler {
	// How long (in seconds) to ramp from 1% to 100%
	private static final float RAMP_DURATION = 1.0f;
	private static final float MIN_POWER = 0.01f;

	private float leftHoldTime = 0f;
	private float rightHoldTime = 0f;

	private boolean leftWasPressed = false;
	private boolean rightWasPressed = false;

	public void tick(boolean leftPressed, boolean rightPressed, float deltaTime) {
		if (leftPressed) {
			if (!leftWasPressed) {
				// Fresh press — reset to zero so the ramp starts from MIN_POWER
				leftHoldTime = 0f;
			}
			leftHoldTime = Math.min(leftHoldTime + deltaTime, RAMP_DURATION);
		} else {
			leftHoldTime = 0f;
		}

		if (rightPressed) {
			if (!rightWasPressed) {
				rightHoldTime = 0f;
			}
			rightHoldTime = Math.min(rightHoldTime + deltaTime, RAMP_DURATION);
		} else {
			rightHoldTime = 0f;
		}

		leftWasPressed = leftPressed;
		rightWasPressed = rightPressed;
	}

	/**
	 * Returns a multiplier in [MIN_POWER, 1.0] for the left key,
	 * or 0 if the key is not held.
	 */
	public float getLeftPower() {
		if (leftHoldTime <= 0f) return 0f;
		float t = leftHoldTime / RAMP_DURATION;          // 0..1
		return MIN_POWER + (1f - MIN_POWER) * t;         // MIN_POWER..1.0
	}

	/**
	 * Returns a multiplier in [MIN_POWER, 1.0] for the right key,
	 * or 0 if the key is not held.
	 */
	public float getRightPower() {
		if (rightHoldTime <= 0f) return 0f;
		float t = rightHoldTime / RAMP_DURATION;
		return MIN_POWER + (1f - MIN_POWER) * t;
	}

	/** Normalised 0..1 progress for the HUD bar (left). */
	public float getLeftProgress() {
		return leftHoldTime / RAMP_DURATION;
	}

	/** Normalised 0..1 progress for the HUD bar (right). */
	public float getRightProgress() {
		return rightHoldTime / RAMP_DURATION;
	}
}