package com.elowen.ui;

import com.elowen.modules.Category;
import com.elowen.utils.SmoothAnimationTimer;
import java.util.HashMap;
import java.util.Objects;

class ClickGUI$7 extends HashMap {
   final ClickGUI X;

   ClickGUI$7(ClickGUI var1) {
      String var10000 = ClickGUI.I();
      Objects.requireNonNull(var1);
      this.X = var1;
      super();
      Category[] var3 = Category.values();
      int var4 = var3.length;
      String var2 = var10000;
      int var5 = 0;

      while (var5 < var4) {
         Category var6 = var3[var5];
         this.put(var6, new SmoothAnimationTimer(0.0F));
         var5++;
         if (var2 != null) {
            break;
         }
      }
   }
}
