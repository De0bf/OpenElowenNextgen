package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.Version;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventShutdown;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventClick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.fun.AutoCloseMC;
import com.elowen.utils.AnimationUtils;
import java.io.File;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MixinMinecraft {
   @Unique
   private long elowen$lastFrame;

   @Inject(method = "close", at = @At("HEAD"), remap = false)
   private void shutdown(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventShutdown());
      }
   }

   @Redirect(
      method = "emergencySaveAndCrash",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/Minecraft;saveReportAndShutdownSoundManager(Lnet/minecraft/client/Minecraft;Ljava/io/File;Lnet/minecraft/CrashReport;I)I"
      )
   )
   private static int elowen$autoCloseExitCode(Minecraft var0, File var1, CrashReport var2, int var3) {
      boolean var4 = AutoCloseMC.Z;
      AutoCloseMC.Z = false;
      return Minecraft.saveReportAndShutdownSoundManager(var0, var1, var2, var4 ? 54188 : var3);
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void tickPre(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventTick(EventType.PRE));
      }
   }

   @Inject(method = "tick", at = @At("TAIL"))
   private void tickPost(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventTick(EventType.POST));
      }
   }

   @Inject(method = "runTick", at = @At("HEAD"))
   private void runTick(boolean var1, CallbackInfo var2) {
      long var3 = System.nanoTime() / 1000000L;
      int var5 = (int)(var3 - this.elowen$lastFrame);
      this.elowen$lastFrame = var3;
      AnimationUtils.D = var5;
   }

   @Inject(
      method = "handleKeybinds",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal = 0, shift = Shift.BEFORE),
      cancellable = true
   )
   private void clickEvent(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         EventClick var2 = new EventClick();
         Elowen.S$Elowen().e().B(var2);
         if (var2.c$Z()) {
            var1.cancel();
         }
      }
   }

   @Inject(method = "createTitle", at = @At("HEAD"), cancellable = true)
   private void hookCreateTitle(CallbackInfoReturnable var1) {
      var1.setReturnValue("Elowen-NextGen " + Version.Y());
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
