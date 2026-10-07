package com.elowen.mixin.accessors;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
   @Accessor
   void setNoJumpDelay(int var1);

   @Invoker("jumpFromGround")
   void invokeJumpFromGround();
}
