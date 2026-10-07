package com.elowen.modules.impl.fun;

import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventAttack;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;

@ModuleInfo(R = "AutoEmptyAttack", a = "Cannot deal damage.", M = Category.FUN)
public class AutoEmptyAttack extends Module {
   @EventTarget
   public void E(EventAttack var1) {
      var1.c(true);
   }
}
