package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.EventManager;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.rotation.RotationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {
   @Unique
   private static EventManager safeEventManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.e() : null;
   }

   @Inject(method = "onButton", at = @At("HEAD"))
   private void onMouseButton(long var1, MouseButtonInfo var3, int var4, CallbackInfo var5) {
      EventManager var6 = safeEventManager();
      if (var6 != null) {
         if (var4 != 2) {
            boolean var7 = var4 == 1;
            var6.B(new com.elowen.events.impl.EventMouseClick(toLegacyButton(var3.button()), var7));
         }
      }
   }

   @Unique
   private static int toLegacyButton(int var0) {
      return switch (var0) {
         case 1 -> 0;
         case 2 -> 2;
         case 3 -> 1;
         default -> var0 - 1;
      };
   }

   @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
   private void onTurnPlayer(LocalPlayer var1, double var2, double var4) {
      if (RotationManager.L && var1 == Minecraft.getInstance().player) {
         RotationManager.X(var2, var4);
      } else {
         var1.turn(var2, var4);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
