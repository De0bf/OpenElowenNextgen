package com.elowen.modules.impl.fun;

import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventSwing;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;

@ModuleInfo(R = "NoSwing", M = Category.FUN, a = "You can't swing your hand")
public class NoSwing extends Module {
   @EventTarget
   public void e(EventSwing var1) {
      var1.c(true);
   }
}
