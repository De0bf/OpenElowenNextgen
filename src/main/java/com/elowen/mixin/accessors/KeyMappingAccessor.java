package com.elowen.mixin.accessors;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
   @Accessor("key")
   Key elowen$getKey();

   @Accessor("isDown")
   void elowen$setIsDown(boolean var1);
}
