package com.elowen.ui;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.values.HasValue;
import java.util.HashMap;
import java.util.Objects;

class ClickGUI$6 extends HashMap {
   final ClickGUI V;

   ClickGUI$6(ClickGUI var1) {
      String var10000 = ClickGUI.I();
      Objects.requireNonNull(var1);
      this.V = var1;
      String var2 = var10000;
      super();

      for (Category var6 : Category.values()) {
         this.put(var6, Elowen.S$Elowen().q$ModuleManager().O(var6));
         if (var2 != null) {
            break;
         }
      }

      if (!HasValue.x()) {
         ClickGUI.z("gTKXhc");
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
