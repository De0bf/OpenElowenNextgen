package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;

@ModuleInfo(R = "Sprint", a = "Automatically sprints", M = Category.MOVEMENT)
public class Sprint extends Module {
   @EventTarget(0)
   public void m(EventMotion var1) {
      boolean var2 = Scaffold.k();
      if (!var2) {
         if (var1.Q() != EventType.PRE) {
            return;
         }

         G.options.keySprint.setDown(true);
      }

      G.options.toggleSprint().set(false);
   }

   @Override
   public void q$V() {
      G.options.keySprint.setDown(false);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
