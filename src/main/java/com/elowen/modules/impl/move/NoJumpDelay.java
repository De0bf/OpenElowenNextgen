package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUpdate;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.mixin.accessors.LivingEntityAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import net.minecraft.client.player.LocalPlayer;

@ModuleInfo(R = "NoJumpDelay", a = "Removes the delay when jumping", M = Category.MOVEMENT)
public class NoJumpDelay extends Module {
   @EventTarget
   public void L(EventUpdate var1) {
      boolean var2 = Scaffold.k();
      if (var1.a$f() == EventType.PRE) {
         LocalPlayer var10000 = G.player;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var10000 = G.player;
         }

         ((LivingEntityAccessor)var10000).setNoJumpDelay(0);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
