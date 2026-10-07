package com.elowen.mixin;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.test.NoInterpolation;
import net.minecraft.client.Minecraft;
import net.minecraft.core.PositionAndRotation;
import net.minecraft.world.entity.AbstractInterpolationHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.PositionPath;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractInterpolationHandler.class)
public abstract class MixinInterpolationHandler {
   @Shadow
   protected Entity entity;

   @Inject(method = "interpolateTo(Lnet/minecraft/world/entity/PositionPath;FFZ)Z", at = @At("HEAD"), cancellable = true)
   private void onInterpolateTo(PositionPath var1, float var2, float var3, boolean var4, CallbackInfoReturnable var5) {
      if (NoInterpolation.D$h()) {
         Entity var6 = this.entity;
         if (var6 instanceof Player && var6 != Minecraft.getInstance().player) {
            Vec3 var7 = var1 != null ? var1.endPosition() : var6.position();
            var6.snapTo(var7, var4 ? var2 : var6.getYRot(), var4 ? var3 : var6.getXRot());
            ((InterpolationHandler)this).cancel();
            var5.setReturnValue(false);
            var5.cancel();
         }
      }
   }

   @Inject(method = "interpolate", at = @At("HEAD"), cancellable = true)
   private void onInterpolate(CallbackInfo var1) {
      if (NoInterpolation.D$h()) {
         Entity var2 = this.entity;
         if (var2 instanceof Player && var2 != Minecraft.getInstance().player) {
            PositionAndRotation var3 = ((InterpolationHandler)this).target();
            if (var3 != null) {
               var2.snapTo(var3.position(), var3.yRot(), var3.xRot());
            }

            ((InterpolationHandler)this).cancel();
            var1.cancel();
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
