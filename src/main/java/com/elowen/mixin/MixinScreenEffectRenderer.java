package com.elowen.mixin;

import com.elowen.modules.impl.render.LowFire;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ScreenEffectRenderer.class)
public class MixinScreenEffectRenderer {
   @ModifyArg(method = "lambda$submitFire$0", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;translate(FFF)Lorg/joml/Matrix4f;"), index = 1)
   private static float elowen$modifyFireY(float var0) {
      return var0 + LowFire.h$F();
   }
}
