package com.elowen.values;

import com.elowen.exceptions.NoSuchModuleException;

public abstract class HasValue {
   private String O;
   private static boolean L;

   public String i() {
      return this.O;
   }

   public void j(String var1) {
      this.O = var1;
   }

   public static void d(boolean var0) {
      L = var0;
   }

   public static boolean x() {
      return L;
   }

   public static boolean X$Z() {
      return !x();
   }

   private static NoSuchModuleException b(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (X$Z()) {
         d(true);
      }
   }
}
