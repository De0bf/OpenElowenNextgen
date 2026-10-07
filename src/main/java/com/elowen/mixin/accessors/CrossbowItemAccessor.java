package com.elowen.mixin.accessors;

import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CrossbowItem.class)
public interface CrossbowItemAccessor {
   @Invoker("getShootingPower")
   static float getShootingPower(ChargedProjectiles var0) {
      throw new AssertionError("Mixin invoker - should not be called directly");
   }
}
