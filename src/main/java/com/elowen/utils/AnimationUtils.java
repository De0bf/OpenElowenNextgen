package com.elowen.utils;

public class AnimationUtils {
   public static int D;

   public static float R(float var0, float var1, float var2) {
      String var10000 = Vector2f.e();
      float var4 = D * (var2 / 1000.0F);
      String var3 = var10000;
      float var8;
      int var5 = (var8 = var0 - var1) == 0.0F ? 0 : (var8 < 0.0F ? -1 : 1);
      if (var3 == null) {
         if (var5 < 0) {
            float var6 = var0 + var4;
            if (var3 == null) {
               if (var6 < var1) {
                  var0 += var4;
                  if (var3 == null) {
                     return var0;
                  }
               }

               var6 = var1;
            }

            var0 = var6;
            if (var3 == null) {
               return var0;
            }
         }

         float var7 = var0 - var4;
         if (var3 != null) {
            return var7;
         }

         float var9;
         var5 = (var9 = var7 - var1) == 0.0F ? 0 : (var9 < 0.0F ? -1 : 1);
      }

      if (var5 > 0) {
         var0 -= var4;
         if (var3 == null) {
            return var0;
         }
      }

      return var1;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
