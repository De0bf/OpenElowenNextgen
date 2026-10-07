package com.elowen.modules.impl.combat.critical;

import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventAttack;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Critical;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class Test implements CriticalMode {
   private Critical M;
   private static final String[] a = new String[]{"[Critical] Test mode triggered on attack", "Test"};
   @Override
   public void O(Critical var1) {
      this.M = var1;
   }

   @Override
   public void f(EventTick var1) {
      this.M.X("Test");
   }

   @Override
   public void T(EventAttack var1) {
      KeyMapping var5 = null;
      boolean var2 = Vanilla.K();
      Critical var10000 = this.M;
      if (var2) {
         if (this.M == null) {
            return;
         }

         var10000 = this.M;
      }

      Minecraft var3 = var10000.d$Minecraft();
      if (var2) {
         if (var3.player == null) {
            return;
         }

         var3 = this.M.d$Minecraft();
      }

      if (var2) {
         if (var3.level == null) {
            return;
         }

         var3 = this.M.d$Minecraft();
      }

      LocalPlayer var4 = var3.player;
      if (var2) {
         label44: {
            if (!var3.player.isSprinting()) {
               var5 = this.M.d$Minecraft().options.keySprint;
               if (!var2) {
                  break label44;
               }

               if (!var5.isDown()) {
                  return;
               }
            }

            this.M.L("[Critical] Test mode triggered on attack");
            var5 = this.M.d$Minecraft().options.keySprint;
         }

         var5.setDown(false);
         var4 = this.M.d$Minecraft().player;
      }

      var4.setSprinting(false);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
