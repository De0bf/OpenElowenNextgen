package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.modules.Module;
import com.elowen.utils.SmoothAnimationTimer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

class ClickGUI$5 extends HashMap {
   final ClickGUI g;

   ClickGUI$5(ClickGUI var1) {
      Objects.requireNonNull(var1);
      this.g = var1;
      super();
      String var10000 = ClickGUI.I();
      Iterator var3 = Elowen.S$Elowen().q$ModuleManager().i().iterator();
      String var2 = var10000;

      while (var3.hasNext()) {
         Module var4 = (Module)var3.next();
         this.put(var4, new SmoothAnimationTimer(0.0F));
         if (var2 != null) {
            break;
         }
      }
   }
}
