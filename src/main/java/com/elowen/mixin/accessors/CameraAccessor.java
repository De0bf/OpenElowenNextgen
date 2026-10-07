package com.elowen.mixin.accessors;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
   @Invoker("setRotation")
   void invokeSetRotation(float var1, float var2);
}
