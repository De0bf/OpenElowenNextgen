package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientLevel.class)
public class MixinClientLevel {
   @Redirect(method = "tickNonPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
   public void hookSkipTicks(Entity var1) {
      if (!Elowen.o.isEmpty() && var1 == Minecraft.getInstance().player) {
         Runnable var2 = (Runnable)Elowen.o.poll();
         if (var2 != null) {
            var2.run();
         }
      }

      if (Elowen.U > 0 && var1 == Minecraft.getInstance().player) {
         Elowen.U--;
      } else {
         var1.tick();
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
