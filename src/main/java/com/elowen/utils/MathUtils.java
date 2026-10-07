package com.elowen.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils {
   public static final double u = Math.PI;
   static final double x = Math.PI * 2;
   static final float b = (float) Math.PI;
   static final float A = (float) (Math.PI * 2);
   static final double k = Math.PI / 2;
   static final float N = (float) (Math.PI / 2);
   static final double P = Math.PI / 4;
   static final double v = 0.3183098861837907;
   public static final Random o;
   private static final String a;

   public static float l(float var0) {
      String var1 = Vector2f.e();
      if (var0 > 90.0F) {
         return 90.0F;
      } else {
         return var0 < -90.0F ? -90.0F : var0;
      }
   }

   public static float O(float var0, float var1, float var2, float var3, float var4) {
      return (var0 - var1) / (var2 - var1) * (var4 - var3) + var3;
   }

   public static double Z(double var0, double var2, double var4) {
      String var6 = Vector2f.e();
      return var0 < var2 ? var2 : Math.min(var0, var4);
   }

   public static int I(int var0, int var1, int var2) {
      String var3 = Vector2f.e();
      return var0 < var1 ? var1 : Math.min(var0, var2);
   }

   public static Number M(Number var0, Number var1, Number var2) {
      String var3 = Vector2f.e();
      if (var0 instanceof Integer) {
         if (var0.intValue() > var2.intValue()) {
            var0 = var2;
         }

         if (var0.intValue() >= var1.intValue()) {
            return var0;
         }

         var0 = var1;
      }

      if (var0 instanceof Float) {
         if (var0.floatValue() > var2.floatValue()) {
            var0 = var2;
         }

         if (!(var0.floatValue() < var1.floatValue())) {
            return var0;
         }

         var0 = var1;
      }

      if (var0 instanceof Double) {
         if (var0.doubleValue() > var2.doubleValue()) {
            var0 = var2;
         }

         if (!(var0.doubleValue() < var1.doubleValue())) {
            return var0;
         }

         var0 = var1;
      }

      if (var0 instanceof Long) {
         if (var0.longValue() > var2.longValue()) {
            var0 = var2;
         }

         if (var0.longValue() >= var1.longValue()) {
            return var0;
         }

         var0 = var1;
      }

      if (var0 instanceof Short) {
         if (var0.shortValue() > var2.shortValue()) {
            var0 = var2;
         }

         if (var0.shortValue() >= var1.shortValue()) {
            return var0;
         }

         var0 = var1;
      }

      if (var0 instanceof Byte) {
         if (var0.byteValue() > var2.byteValue()) {
            var0 = var2;
         }

         if (var0.byteValue() < var1.byteValue()) {
            var0 = var1;
         }
      }

      return var0;
   }

   public static double K(double var0, double var2) {
      String var4 = Vector2f.e();
      return var0 >= var2 ? var0 : o.nextDouble() * (var2 - var0) + var0;
   }

   public static int x(int var0, int var1, Random var2) {
      String var3 = Vector2f.e();
      return var1 - var0 <= 0 ? var0 : var0 + var2.nextInt(var1 - var0);
   }

   public static int M(int var0, int var1) {
      String var2 = Vector2f.e();
      return var1 - var0 <= 0 ? var0 : var0 + new Random().nextInt(var1 - var0);
   }

   public static float m(float var0) {
      String var10000 = Vector2f.e();
      float var2 = var0 % 360.0F;
      String var1 = var10000;
      float var6;
      int var3 = (var6 = var2 - -180.0F) == 0.0F ? 0 : (var6 < 0.0F ? -1 : 1);
      if (var1 == null) {
         if (var3 < 0) {
            return var2 + 360.0F;
         }

         if (var1 != null) {
            return var2 - 180.0F;
         }

         float var7;
         var3 = (var7 = var2 - 180.0F) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
      }

      return var3 <= 0 ? var2 : var2 - 360.0F;
   }

   public static float t(float var0, float var1, float var2) {
      return var1 + var0 * (var2 - var1);
   }

   public static double s(double var0, double var2, float var4) {
      return var2 + var0 * (var4 - var2);
   }

   public static double X(float var0, double var1, double var3) {
      return var1 + var0 * (var3 - var1);
   }

   public static float M(float var0, float var1, float var2) {
      return var1 + var0 * m(var2 - var1);
   }

   public static double K(double var0, int var2) {
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         return new BigDecimal(var0).setScale(var2, RoundingMode.HALF_UP).doubleValue();
      }
   }

   public static int Z(Number var0) {
      String var1 = Vector2f.e();
      if (!(var0 instanceof Integer) && !(var0 instanceof Long)) {
         String[] var2 = var0.toString().split(a);
         if (var2.length == 2) {
            if (var2[1].endsWith("0")) {
               var2[1] = var2[1].substring(0, var2[1].length() - 1);
            }

            return var2[1].length();
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public static float s(float var0, float var1, float var2) {
      String var3 = Vector2f.e();
      return var0 < var1 ? var1 : Math.min(var0, var2);
   }

   public static int f(int var0, int var1, int var2) {
      String var3 = Vector2f.e();
      return var0 < var1 ? var1 : Math.min(var0, var2);
   }

   public static int e(int var0) {
      int var1 = var0 - 1;
      var1 |= var1 >> 1;
      var1 |= var1 >> 2;
      var1 |= var1 >> 4;
      var1 |= var1 >> 8;
      var1 |= var1 >> 16;
      return var1 + 1;
   }

   public static float r(double var0, double var2) {
      return (float)(Math.log(var2) / Math.log(var0));
   }

   public static double c(double var0, int var2) {
      if (var2 < 0) {
         throw new IllegalArgumentException();
      }

      BigDecimal var3 = new BigDecimal(var0);
      var3 = var3.setScale(var2, RoundingMode.HALF_UP);
      return var3.doubleValue();
   }

   public static boolean e(float var0, float var1, float var2, float var3, float var4, float var5) {
      String var6 = Vector2f.e();
      return var0 > var2 && var0 < var4 && var1 > var3 && var1 < var5;
   }

   public static float B(float var0, float var1) {
      double var2 = 3.141592653;
      double var4 = 1.0 / Math.sqrt(2.0 * var2 * (var1 * var1));
      return (float)(var4 * Math.exp(-(var0 * var0) / (2.0 * (var1 * var1))));
   }

   public static double M(double var0, double var2) {
      String var10000 = Vector2f.e();
      double var5 = b(1.0 - var0 * var0);
      String var4 = var10000;
      double var7 = var2 + (Math.PI / 2);
      double var9 = var7 - (int)(var7 / (Math.PI * 2)) * (Math.PI * 2);
      double var13;
      int var11 = (var13 = var9 - 0.0) == 0.0 ? 0 : (var13 < 0.0 ? -1 : 1);
      if (var4 == null) {
         if (var11 < 0) {
            var9 += Math.PI * 2;
         }

         if (var4 != null) {
            return var9;
         }

         double var14;
         var11 = (var14 = var9 - Math.PI) == 0.0 ? 0 : (var14 < 0.0 ? -1 : 1);
      }

      return var11 >= 0 ? -var5 : var5;
   }

   public static double b(double var0) {
      return Math.sqrt(var0);
   }

   static {
      a = "\\.";
      o = new Random();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
