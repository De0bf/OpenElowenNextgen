package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.render.NoRender;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class MixinHud {
   @Inject(method = "extractEffects", at = @At("HEAD"), cancellable = true)
   private void onExtractEffects(GuiGraphicsExtractor var1, DeltaTracker var2, CallbackInfo var3) {
      Elowen var4 = Elowen.S$Elowen();
      if (var4 != null) {
         ModuleManager var5 = var4.q$ModuleManager();
         if (var5 != null) {
            NoRender var6 = (NoRender)var5.A(NoRender.class);
            if (var6 != null && var6.w() && var6.i.w()) {
               var3.cancel();
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
