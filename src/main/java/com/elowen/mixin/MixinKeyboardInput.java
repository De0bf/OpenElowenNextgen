package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.mixin.accessors.ClientInputAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class MixinKeyboardInput {
   @Unique
   private static float calculateImpulse(boolean var0, boolean var1) {
      if (var0 == var1) {
         return 0.0F;
      } else {
         return var0 ? 1.0F : -1.0F;
      }
   }

   @Inject(method = "tick", at = @At("TAIL"))
   private void onMoveInputTail(CallbackInfo var1) {
      Elowen var2 = Elowen.S$Elowen();
      if (var2 != null && var2.e() != null) {
         ClientInputAccessor var3 = (ClientInputAccessor)this;
         Input var4 = var3.keyPresses();
         float var5 = calculateImpulse(var4.forward(), var4.backward());
         float var6 = calculateImpulse(var4.left(), var4.right());
         boolean var7 = Minecraft.getInstance().player != null && Minecraft.getInstance().player.isSprinting();
         EventMoveInput var8 = new EventMoveInput(var5, var6, var4.jump(), var4.shift(), var7, 0.3);
         var2.e().B(var8);
         float var9 = var8.I();
         float var10 = var8.g$F();
         var3.setMoveVector(new Vec2(var10, var9).normalized());
         var3.setKeyPresses(new Input(var9 > 0.0F, var9 < 0.0F, var10 > 0.0F, var10 < 0.0F, var8.D(), var8.q$Z(), var4.sprint() && var9 > 0.0F));
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
