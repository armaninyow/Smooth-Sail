package com.armaninyow.smoothsail.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

	@Accessor(value = "yRotO", remap = false)
	float getYRotO();

	@Accessor(value = "yRotO", remap = false)
	void setYRotO(float value);
}