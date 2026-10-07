package com.elowen.utils.renderer;

import java.awt.Color;

public class RenderUtils {
   public static int O(int var0, int var1, int var2, int var3) {
      return var3 << 24 | var0 << 16 | var1 << 8 | var2;
   }

   public static int f(int var0, float var1) {
      int var2 = (int)(var1 * 255.0F);
      return var0 & 16777215 | var2 << 24;
   }

   public static int h(Color var0) {
      return var0.getAlpha() << 24 | var0.getRed() << 16 | var0.getGreen() << 8 | var0.getBlue();
   }

   public static int A(int var0, float var1, float var2, float var3) {
      return u(System.currentTimeMillis(), var0, var1, var2, var3);
   }

   public static int u(long var0, int var2, float var3, float var4, float var5) {
      float var6 = (float)((var0 + var2) % (long)var5) / var5;
      return Color.HSBtoRGB(var6 % 1.0F, var3, var4) | 0xFF000000;
   }
}
