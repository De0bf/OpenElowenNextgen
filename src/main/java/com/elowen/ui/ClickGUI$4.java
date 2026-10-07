package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.values.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ClickGUI$4 extends HashMap {
   final ClickGUI C;

   ClickGUI$4(ClickGUI var1) {
      String var10000 = ClickGUI.I();
      Objects.requireNonNull(var1);
      this.C = var1;
      String var2 = var10000;
      super();

      for (Value var4 : (List<Value>)Elowen.S$Elowen().E$C().A$List()) {
         this.put(var4, new SmoothAnimationTimer(0.0F));
         if (var2 != null) {
            break;
         }
      }
   }
}
