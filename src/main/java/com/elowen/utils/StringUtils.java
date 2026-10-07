package com.elowen.utils;

public class StringUtils {
   public static boolean n(String var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty()) {
         char[] var2 = var0.toCharArray();
         int var3 = var2.length;
         int var4 = 0;
         while (var4 < var3) {
            char var5 = var2[var4];
            if (var5 > 19968) {
               return true;
            }

            var4++;
         }

         return false;
      } else {
         return false;
      }
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
