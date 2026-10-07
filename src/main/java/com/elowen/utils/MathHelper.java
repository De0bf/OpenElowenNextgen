package com.elowen.utils;

public class MathHelper {
   public static float N(float var0) {
      String var10000 = Vector2f.e();
      var0 %= 360.0F;
      String var1 = var10000;
      float var5;
      int var3 = (var5 = var0 - 180.0F) == 0.0F ? 0 : (var5 < 0.0F ? -1 : 1);
      if (var1 == null) {
         if (var3 >= 0) {
            var0 -= 360.0F;
         }

         if (var1 != null) {
            return var0;
         }

         float var6;
         var3 = (var6 = var0 - -180.0F) == 0.0F ? 0 : (var6 < 0.0F ? -1 : 1);
      }

      if (var3 < 0) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static double b(double var0) {
      String var10000 = Vector2f.e();
      var0 %= 360.0;
      String var2 = var10000;
      double var6;
      int var4 = (var6 = var0 - 180.0) == 0.0 ? 0 : (var6 < 0.0 ? -1 : 1);
      if (var2 == null) {
         if (var4 >= 0) {
            var0 -= 360.0;
         }

         if (var2 != null) {
            return var0;
         }

         double var7;
         var4 = (var7 = var0 - -180.0) == 0.0 ? 0 : (var7 < 0.0 ? -1 : 1);
      }

      if (var4 < 0) {
         var0 += 360.0;
      }

      return var0;
   }

   public static int y(int var0) {
      String var10000 = Vector2f.e();
      var0 %= 360;
      String var1 = var10000;
      int var3 = var0;
      short var10001 = 180;
      if (var1 == null) {
         if (var0 >= 180) {
            var0 -= 360;
         }

         var3 = var0;
         if (var1 != null) {
            return var0;
         }

         var10001 = -180;
      }

      if (var3 < var10001) {
         var0 += 360;
      }

      return var0;
   }

   public static int q(int var0, int var1, int var2) {
      String var3 = Vector2f.e();
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static float K(float var0, float var1, float var2) {
      String var3 = Vector2f.e();
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static double P(double var0, double var2, double var4) {
      String var6 = Vector2f.e();
      if (var0 < var2) {
         return var2;
      } else {
         return var0 > var4 ? var4 : var0;
      }
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
