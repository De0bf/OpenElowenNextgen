package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.render.ViewClip;
import com.elowen.utils.rotation.RotationManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class MixinCamera {
   @Inject(at = @At("HEAD"), method = "getMaxZoom", cancellable = true)
   private void getMaxZoom(float var1, CallbackInfoReturnable var2) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().q$ModuleManager() != null) {
         ViewClip var3 = (ViewClip)Elowen.S$Elowen().q$ModuleManager().A(ViewClip.class);
         if (var3.w()) {
            float var4 = (float)((double)var1 * var3.z.o$F() * var3.S.l / 100.0);
            var2.setReturnValue(var4);
         }
      }
   }

   @Redirect(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"))
   private float redirectAlignViewYRot(Entity var1, float var2) {
      return RotationManager.L && RotationManager.X != null && var1 == Minecraft.getInstance().player ? RotationManager.X.H : var1.getViewYRot(var2);
   }

   @Redirect(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"))
   private float redirectAlignViewXRot(Entity var1, float var2) {
      return RotationManager.L && RotationManager.X != null && var1 == Minecraft.getInstance().player ? RotationManager.X.E : var1.getViewXRot(var2);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
