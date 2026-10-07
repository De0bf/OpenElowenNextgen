package com.elowen.ui;

import com.elowen.modules.Category;
import com.elowen.utils.SmoothAnimationTimer;
import java.util.HashMap;
import java.util.Objects;

class ClickGUI$3 extends HashMap {
   final ClickGUI j;

   ClickGUI$3(ClickGUI var1) {
      Objects.requireNonNull(var1);
      this.j = var1;
      String var10000 = ClickGUI.I();
      super();
      Category[] var3 = Category.values();
      String var2 = var10000;

      for (Category var6 : var3) {
         this.put(var6, new SmoothAnimationTimer(0.0F));
         if (var2 != null) {
            break;
         }
      }
   }
}
