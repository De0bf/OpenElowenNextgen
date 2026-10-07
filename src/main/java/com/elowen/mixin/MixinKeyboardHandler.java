package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventKey;
import com.elowen.exceptions.NoSuchModuleException;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class MixinKeyboardHandler {
   @Inject(at = @At("HEAD"), method = "keyPress")
   private void onKeyPress(long var1, int var3, KeyEvent var4, CallbackInfo var5) {
      if (var4.key() != InputConstants.UNKNOWN.getValue() && Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventKey(var4.key(), var3 != 0));
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
