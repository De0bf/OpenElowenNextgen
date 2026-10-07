package com.elowen.mixin;

import com.elowen.Elowen;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.DeltaTracker$Timer")
public class MixinTimer {
   @Shadow
   private float deltaTicks;
   @Shadow
   private float deltaTickResidual;
   @Shadow
   private long lastMs;
   @Shadow
   @Final
   private float msPerTick;
   @Shadow
   @Final
   private FloatUnaryOperator targetMsptProvider;

   @Inject(method = "advanceGameTime", at = @At("HEAD"), cancellable = true)
   private void onAdvanceGameTime(long var1, CallbackInfoReturnable var3) {
      if (Elowen.i != 1.0F) {
         float var4 = this.targetMsptProvider.apply(this.msPerTick);
         this.deltaTicks = (float)(var1 - this.lastMs) / var4 * Elowen.i;
         this.lastMs = var1;
         this.deltaTickResidual = this.deltaTickResidual + this.deltaTicks;
         int var5 = (int)this.deltaTickResidual;
         this.deltaTickResidual -= var5;
         var3.setReturnValue(var5);
      }
   }
}
