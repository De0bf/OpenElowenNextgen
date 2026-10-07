package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ClickGUI$2 extends HashMap {
   final ClickGUI j;

   ClickGUI$2(ClickGUI var1) {
      String var10000 = ClickGUI.I();
      Objects.requireNonNull(var1);
      this.j = var1;
      String var2 = var10000;
      super();

      for (Value var4 : (List<Value>)Elowen.S$Elowen().E$C().A$List()) {
         if (var2 == null && var4.J$H() == ValueType.STRING) {
            this.put(var4.N(), new SmoothAnimationTimer(0.0F));
         }

         if (var2 != null) {
            break;
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
