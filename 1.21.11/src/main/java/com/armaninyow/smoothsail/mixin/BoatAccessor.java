package com.armaninyow.smoothsail.mixin;

import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

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