package com.elowen.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TickTimeHelper {
   private static final List a = new ArrayList();
   private int s = 0;

   public static void S$V() {
      String var10000 = Vector2f.e();
      Iterator var1 = a.iterator();
      String var0 = var10000;

      while (var1.hasNext()) {
         TickTimeHelper var2 = (TickTimeHelper)var1.next();
         var2.s++;
         if (var0 != null) {
            break;
         }
      }
   }

   public TickTimeHelper() {
      a.add(this);
   }

   public boolean m(int var1) {
      String var2 = Vector2f.e();
      return this.s >= var1;
   }

   public boolean M(float var1) {
      String var2 = Vector2f.e();
      return this.s >= var1;
   }

   public void e() {
      this.s = 0;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
