package com.armaninyow.smoothsail.mixin;

import net.minecraft.world.entity.vehicle.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// 1.21.2_1.21.5
@Mixin(AbstractBoat.class)
public interface BoatAccessor {

	@Accessor(value = "deltaRotation", remap = false)
	float getDeltaRotation();

	@Accessor(value = "deltaRotation", remap = false)
	void setDeltaRotation(float value);

	@Accessor(value = "inputLeft", remap = false)
	boolean getInputLeft();

	@Accessor(value = "inputRight", remap = false)
	boolean getInputRight();
}