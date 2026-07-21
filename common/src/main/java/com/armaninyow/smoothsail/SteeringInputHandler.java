package com.armaninyow.smoothsail;

public class SteeringInputHandler {
	private static final float RAMP_DURATION = 1.0f;
	private static final float MIN_POWER = 0.01f;

	private float leftHoldTime = 0f;
	private float rightHoldTime = 0f;

	private boolean leftWasPressed = false;
	private boolean rightWasPressed = false;

	public void tick(boolean leftPressed, boolean rightPressed, float deltaTime) {
		if (leftPressed) {
			if (!leftWasPressed) {
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

	public float getLeftPower() {
		if (leftHoldTime <= 0f) return 0f;
		float t = leftHoldTime / RAMP_DURATION;
		return MIN_POWER + (1f - MIN_POWER) * t;
	}

	public float getRightPower() {
		if (rightHoldTime <= 0f) return 0f;
		float t = rightHoldTime / RAMP_DURATION;
		return MIN_POWER + (1f - MIN_POWER) * t;
	}

	public float getLeftProgress() {
		return leftHoldTime / RAMP_DURATION;
	}

	public float getRightProgress() {
		return rightHoldTime / RAMP_DURATION;
	}
}