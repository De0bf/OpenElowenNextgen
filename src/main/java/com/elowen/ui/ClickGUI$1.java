package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.modules.Module;
import com.elowen.utils.SmoothAnimationTimer;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ClickGUI$1 extends HashMap {
   final ClickGUI V;

   ClickGUI$1(ClickGUI var1) {
      String var10000 = ClickGUI.I();
      Objects.requireNonNull(var1);
      this.V = var1;
      String var2 = var10000;
      super();

      for (Module var4 : (List<Module>)Elowen.S$Elowen().q$ModuleManager().i()) {
         this.put(var4, new SmoothAnimationTimer(0.0F, 255.0F));
         if (var2 != null) {
            break;
         }
      }
   }
}
